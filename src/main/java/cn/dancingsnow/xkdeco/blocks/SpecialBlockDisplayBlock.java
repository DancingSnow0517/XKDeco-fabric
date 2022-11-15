package cn.dancingsnow.xkdeco.blocks;

import cn.dancingsnow.xkdeco.blockentities.BlockDisplayBlockEntity;
import cn.dancingsnow.xkdeco.utils.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class SpecialBlockDisplayBlock extends BlockWithEntity implements XKDecoBlock.Special {
    private static final VoxelShape TOP = Block.createCuboidShape(0, 11, 0, 16, 16, 16);
    private static final VoxelShape NECK = Block.createCuboidShape(2, 8, 2, 14, 11, 14);
    private static final VoxelShape BOTTOM = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 4, 2, 4),
            Block.createCuboidShape(12, 0, 0, 16, 2, 4),
            Block.createCuboidShape(0, 0, 12, 4, 2, 16),
            Block.createCuboidShape(12, 0, 12, 16, 2, 16),
            Block.createCuboidShape(0, 2, 0, 16, 8, 16));
    private static final VoxelShape SHAPE = VoxelShapes.union(TOP, NECK, BOTTOM);

    public SpecialBlockDisplayBlock(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            this.dropContent(world, pos);
            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }

    private void dropContent(World world, BlockPos pos) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            // TODO: blockentity
            if (blockEntity instanceof BlockDisplayBlockEntity blockDisplayBlockEntity) {
                ItemStack itemStack = blockDisplayBlockEntity.getItem();
                if (!itemStack.isEmpty()) {
                    world.syncWorldEvent(1010, pos, 0);
                    blockDisplayBlockEntity.clear();
                    ItemEntity itemEntity = new ItemEntity(world,
                            pos.getX() + world.random.nextFloat() * 0.7F + 0.15F,
                            pos.getY() + world.random.nextFloat() * 0.7F + 0.060000002F + 0.6D,
                            pos.getZ() + world.random.nextFloat() * 0.7F + 0.15F,
                            itemStack.copy());
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                }
            }
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        NbtCompound nbt = BlockItem.getBlockEntityNbt(itemStack);
        if (nbt != null && nbt.contains(BlockDisplayBlockEntity.ITEMSTACK_NBT_KEY)) {
            world.setBlockState(pos, state, 2);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BlockDisplayBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var be = world.getBlockEntity(pos);
        if (!(be instanceof BlockDisplayBlockEntity blockEntity)) return ActionResult.PASS;

        var swapItem = hit.getSide() == Direction.UP
                && MathUtil.containsInclusive(TOP.getBoundingBox(), hit.getPos().subtract(Vec3d.of(pos)));
        if (swapItem) {
            var handItem = player.getStackInHand(hand);
            if (!(handItem.getItem() instanceof BlockItem) && !handItem.isEmpty()) {
                return ActionResult.CONSUME_PARTIAL;
            }
        }

        if (world.isClient) return ActionResult.SUCCESS;

        if (swapItem) {
            var temp = player.getStackInHand(hand).copy();
            player.setStackInHand(hand, blockEntity.getItem().copy());
            blockEntity.setItem(temp);
        } else if (blockEntity.getSelectedProperty().isEmpty()) {
            sendMessage(player, Text.translatable("item.minecraft.debug_stick.empty",
                    Objects.requireNonNull(blockEntity.getCachedState().getBlock().getName().toString())));
        } else if (hit.getPos().subtract(Vec3d.of(pos)).y > 0.5) {
            var property = blockEntity.getSelectedProperty().get();
            blockEntity.setStoredBlockState(cycle(blockEntity.getCachedState(), property, false));
            sendMessage(player, Text.translatable("\"%s\" to %s",
                    property.getName(), getValueString(blockEntity.getCachedState(), property)));
        } else {
            var blockState = blockEntity.getCachedState();
            var newProperty = cycle(blockState.getProperties(), blockEntity.getSelectedProperty().get(), false);
            blockEntity.setSelectedProperty(newProperty);
            sendMessage(player, Text.translatable("selected \"%s\" (%s)",
                    newProperty.getName(), getValueString(blockState, newProperty)));
        }
        return ActionResult.SUCCESS;
    }

    // borrowed from DebugStickItem#cycle
    private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) {
        return state.with(property, cycle(property.getValues(), state.get(property), inverse));
    }

    // borrowed from DebugStickItem#cycle
    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) {
        return inverse ? Util.previous(elements, current) : Util.next(elements, current);
    }

    // borrowed from DebugStickItem#sendMessage
    private static void sendMessage(PlayerEntity player, Text message) {
        ((ServerPlayerEntity)player).sendMessageToClient(message, true);
    }

    // borrowed from DebugStickItem#getValueString
    private static <T extends Comparable<T>> String getValueString(BlockState state, Property<T> property) {
        return property.name(state.get(property));
    }
}
