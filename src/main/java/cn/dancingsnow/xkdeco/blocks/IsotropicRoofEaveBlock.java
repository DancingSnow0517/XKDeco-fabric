package cn.dancingsnow.xkdeco.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static cn.dancingsnow.xkdeco.utils.RoofUtil.RoofShape;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.RoofHalf;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.Rotation;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.getPlacementHalf;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isHalfOpenClockWiseSide;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isHalfOpenCounterClockWiseSide;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.tryConnectToEave;


public final class IsotropicRoofEaveBlock extends Block implements Waterloggable, XKDecoBlock.Isotropic {
    public static final EnumProperty<RoofShape> SHAPE = EnumProperty.of("shape", RoofShape.class);
    public static final EnumProperty<RoofHalf> HALF = EnumProperty.of("half", RoofHalf.class);
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final VoxelShape ROOF_EAVE_TIP = Block.createCuboidShape(0, 0, 0, 16, 8, 16);
    public static final VoxelShape ROOF_EAVE_BASE = Block.createCuboidShape(0, 8, 0, 16, 16, 16);

    public IsotropicRoofEaveBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(SHAPE, RoofShape.STRAIGHT).with(HALF, RoofHalf.TIP)
                .with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShapeStatic(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, HALF, FACING, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var initialState = this.getDefaultState()
                .with(FACING, ctx.getPlayerFacing()).with(HALF, getPlacementHalf(ctx))
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);

        for (var trial : List.of(
                tryConnectToEave(Rotation.FRONT,
                        (r, s, h) -> isHalfOpenClockWiseSide(s, r, Rotation.BACK),
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.OUTER).with(HALF, tgt.get(HALF)).with(FACING, curr.get(FACING).rotateYCounterclockwise())),
                tryConnectToEave(Rotation.FRONT,
                        (r, s, h) -> isHalfOpenCounterClockWiseSide(s, r, Rotation.BACK),
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.OUTER).with(HALF, tgt.get(HALF)).with(FACING, curr.get(FACING))),
                tryConnectToEave(Rotation.BACK,
                        (r, s, h) -> isHalfOpenCounterClockWiseSide(s, r, Rotation.FRONT),
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.INNER).with(HALF, tgt.get(HALF)).with(FACING, curr.get(FACING).rotateYCounterclockwise())),
                tryConnectToEave(Rotation.BACK,
                        (r, s, h) -> isHalfOpenClockWiseSide(s, r, Rotation.FRONT),
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.INNER).with(HALF, tgt.get(HALF)).with(FACING, curr.get(FACING)))
        )) {
            var result = trial.apply(ctx.getWorld(), ctx.getBlockPos(), ctx.getPlayerFacing(), initialState);
            if (result.isPresent()) return result.get().with(HALF, getPlacementHalf(ctx));
        }
        return initialState;
    }

    @Override
    public boolean isGlass() {
        return false;
    }

    @Override
    public VoxelShape getShapeStatic(BlockState state) {
        return switch (state.get(HALF)) {
            case BASE -> ROOF_EAVE_BASE;
            case TIP -> ROOF_EAVE_TIP;
        };
    }
}
