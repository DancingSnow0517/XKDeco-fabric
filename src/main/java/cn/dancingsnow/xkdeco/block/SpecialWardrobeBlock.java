package cn.dancingsnow.xkdeco.block;

import cn.dancingsnow.xkdeco.blockentity.WardrobeBlockEntity;
import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public final class SpecialWardrobeBlock extends AbstractChestBlock<WardrobeBlockEntity> implements XKDecoBlock.Special {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final EnumProperty<DoorHinge> HINGE = Properties.DOOR_HINGE;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty DOUBLE = BooleanProperty.of("double");

    public static final VoxelShape SHAPE_NORTH_OPEN = Block.createCuboidShape(0, 0, 1, 16, 16, 16);
    public static final VoxelShape SHAPE_SOUTH_OPEN = Block.createCuboidShape(0, 0, 0, 16, 16, 15);
    public static final VoxelShape SHAPE_WEST_OPEN = Block.createCuboidShape(1, 0, 0, 16, 16, 16);
    public static final VoxelShape SHAPE_EAST_OPEN = Block.createCuboidShape(0, 0, 0, 15, 16, 16);

    public SpecialWardrobeBlock(Settings settings) {
        super(settings, () -> ModBlockEntities.WARDROBE_BLOCK_ENTITY);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, HALF, DOUBLE, HINGE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return !state.get(OPEN) ? VoxelShapes.fullCube() : switch (state.get(FACING)) {
            case NORTH -> SHAPE_NORTH_OPEN;
            case EAST -> SHAPE_EAST_OPEN;
            case WEST -> SHAPE_WEST_OPEN;
            case SOUTH -> SHAPE_SOUTH_OPEN;
            default -> throw new IllegalStateException("Block %s has an invalid state property: %s=%s".formatted(
                    state.toString(), FACING.getName(), state.get(FACING)
            ));
        };
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)) {
            return (this.getDefaultState()
                    .with(FACING, ctx.getPlayerFacing().getOpposite()))
                    .with(HINGE, this.getHinge(ctx))
                    .with(OPEN, false)
                    .with(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    /**
     * Borrowed from DoorBlock::getHinge
     */
    private DoorHinge getHinge(ItemPlacementContext ctx) {
        BlockPos clickPos = ctx.getBlockPos();
        Direction facing = ctx.getPlayerFacing();
        int stepX = facing.getOffsetX();
        int stepZ = facing.getOffsetZ();
        Vec3d clickLocation = ctx.getHitPos();
        double xDiff = clickLocation.x - (double) clickPos.getX();
        double zDiff = clickLocation.z - (double) clickPos.getZ();
        if ((stepX >= 0 || !(zDiff < 0.5D)) && (stepX <= 0 || !(zDiff > 0.5D)) && (stepZ >= 0 || !(xDiff > 0.5D)) && (stepZ <= 0 || !(xDiff < 0.5D))) {
            return DoorHinge.LEFT;
        } else {
            return DoorHinge.RIGHT;
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            preventCreativeDropFromBottomPart(world, pos, state, player);
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf currentHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && currentHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            // vertical direction and from a place supposed to be a counterpart
            if (neighborState.isOf(this) && neighborState.get(HALF) != currentHalf) {
                return state.with(FACING, neighborState.get(FACING))
                        .with(OPEN, neighborState.get(OPEN))
                        .with(HINGE, neighborState.get(HINGE))
                        .with(DOUBLE, neighborState.get(DOUBLE));
            } else {
                // the neighbor is a different block or of the same half (not actually a counterpart)
                return Blocks.AIR.getDefaultState();
            }
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        return DoubleBlockProperties.PropertyRetriever::getFallback;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.get(HALF) == DoubleBlockHalf.UPPER;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return this.isTranslucent(state, world, pos) ? 0 : 15;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WardrobeBlockEntity(pos, state);
    }



    /**
     * Borrowed from DoublePlantBlock#preventCreativeDropFromBottomPart
     */
    public static void preventCreativeDropFromBottomPart(World world, BlockPos pPos, BlockState pState, PlayerEntity pPlayer) {
        DoubleBlockHalf doubleblockhalf = pState.get(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pPos.down();
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.isOf(pState.getBlock()) && blockstate.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = blockstate.contains(Properties.WATERLOGGED) && blockstate.get(Properties.WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                world.setBlockState(blockpos, blockstate1, 35);
                world.syncWorldEvent(pPlayer, 2001, blockpos, Block.getRawIdFromState(blockstate));
            }
        }
    }
}
