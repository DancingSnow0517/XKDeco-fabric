package cn.dancingsnow.xkdeco.blocks;

import cn.dancingsnow.xkdeco.blockentities.ItemDisplayBlockEntity;
import cn.dancingsnow.xkdeco.utils.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static cn.dancingsnow.xkdeco.utils.MathUtil.TAU;

public final class SpecialItemDisplayBlock extends BlockWithEntity implements XKDecoBlock.Special {
    private static final VoxelShape TOP = Block.createCuboidShape(0, 13, 0, 16, 16, 16);
    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 0, 3, 13, 4, 13),
            Block.createCuboidShape(5, 4, 5, 11, 11, 11),
            Block.createCuboidShape(1, 11, 1, 15, 13, 15),
            TOP
    );
    public static final BooleanProperty POWERED = Properties.POWERED;

    public SpecialItemDisplayBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            this.dropContent(world, pos);
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    /**
     * Borrowed from JukeboxBlock#dropRecording
     */
    @SuppressWarnings("DuplicatedCode")
    private void dropContent(World world, BlockPos pPos) {
        if (!world.isClient) {
            BlockEntity blockentity = world.getBlockEntity(pPos);
            if (blockentity instanceof ItemDisplayBlockEntity itemDisplayBlockEntity) {
                ItemStack itemstack = itemDisplayBlockEntity.getItem();
                if (!itemstack.isEmpty()) {
                    world.syncWorldEvent(1010, pPos, 0);
                    itemDisplayBlockEntity.clear();
                    ItemEntity itementity = new ItemEntity(world,
                            pPos.getX() + world.random.nextFloat() * 0.7F + 0.15F,
                            pPos.getY() + world.random.nextFloat() * 0.7F + 0.060000002F + 0.6D,
                            pPos.getZ() + world.random.nextFloat() * 0.7F + 0.15F,
                            itemstack.copy());
                    itementity.setToDefaultPickupDelay();
                    world.spawnEntity(itementity);
                }
            }
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        NbtCompound nbt = BlockItem.getBlockEntityNbt(itemStack);
        if (nbt != null && nbt.contains(ItemDisplayBlockEntity.ITEMSTACK_NBT_KEY)) {
            world.setBlockState(pos, state.with(POWERED, false), 2);
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
        return new ItemDisplayBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hit.getSide() != Direction.UP || !MathUtil.containsInclusive(TOP.getBoundingBox(), hit.getPos().subtract(Vec3d.of(pos)))) {
            return ActionResult.PASS;
        }

        if (world.isClient) {
            var be = world.getBlockEntity(pos);
            if (be instanceof ItemDisplayBlockEntity tileEntity) {
                 var temp = player.getStackInHand(hand).copy();
                 player.setStackInHand(hand, tileEntity.getItem().copy());
                 tileEntity.setItem(temp);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof ItemDisplayBlockEntity itemDisplayBlockEntity) {
                var spin = itemDisplayBlockEntity.getSpin();
                if (itemDisplayBlockEntity.getCachedState().get(POWERED)) {
                    spin = (float) (Math.round(spin / (TAU / 8)) * (TAU / 8));
                } else {
                    spin += 0.05f;
                    if (spin >= TAU) {
                        spin -= TAU;
                    }
                }
                itemDisplayBlockEntity.setSpin(spin);
            }
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            if (state.get(POWERED) != world.isReceivingRedstonePower(pos)) {
                world.setBlockState(pos, state.cycle(POWERED), 2);
                if (world.getBlockEntity(pos) instanceof ItemDisplayBlockEntity be) {
                    be.markDirty();
                }
            }
        }
    }
}
