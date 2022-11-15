package cn.dancingsnow.xkdeco.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public final class BasicBlock extends Block implements Waterloggable, XKDecoBlock.Basic {

    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private final boolean isSupportNeeded;
    private final ImmutableMap<Direction, VoxelShape> shapes;


    public BasicBlock(Settings settings, boolean isSupportNeeded, Map<Direction, ? extends VoxelShape> shapes) {
        super(settings);
        this.isSupportNeeded = isSupportNeeded;
        this.shapes = ImmutableMap.copyOf(shapes);
        this.setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var directions = ctx.getPlacementDirections();
        var fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        var direction = Arrays.stream(directions).filter(Direction.Type.HORIZONTAL).findFirst();
        return this.getDefaultState()
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER)
                .with(FACING, direction.orElse(Direction.NORTH).getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Objects.requireNonNull(this.shapes.get(state.get(FACING)));
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
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
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
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !this.isSupportNeeded || world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }
}
