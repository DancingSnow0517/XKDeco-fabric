package cn.dancingsnow.xkdeco.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public final class SpecialCupBlock extends Block implements XKDecoBlock.Special {

    private static final int MAXIMUM_COUNT = 4;

    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final IntProperty COUNT = IntProperty.of("count", 1, MAXIMUM_COUNT);

    private static final VoxelShape ONE_SHAPE = Block.createCuboidShape(6, 0, 6, 10, 6, 10);
    private static final VoxelShape TWO_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 6, 13);
    private static final VoxelShape THREE_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 6, 14);
    private static final VoxelShape FOUR_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 7, 14);

    public SpecialCupBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(WATERLOGGED, false)
                .with(COUNT, 1));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(COUNT)) {
            case 1 -> ONE_SHAPE;
            case 2 -> TWO_SHAPE;
            case 3 -> THREE_SHAPE;
            default -> FOUR_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var state = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (state.isOf(this)) {
            return state.with(COUNT, Math.min(MAXIMUM_COUNT, state.get(COUNT) + 1));
        }
        var fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings({"deprecation", "DuplicatedCode"})
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction()) {
            var item = context.getStack();
            if (item.isOf(this.asItem()) && state.get(COUNT) < MAXIMUM_COUNT) {
                return true;
            }
        }
        return super.canReplace(state, context);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COUNT, WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(COUNT);
    }
}
