package cn.dancingsnow.xkdeco.block;

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

import static cn.dancingsnow.xkdeco.utils.RoofUtil.RoofHalf;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.getPlacementHalf;

public final class IsotropicRoofFlatBlock extends Block implements Waterloggable, XKDecoBlock.Isotropic {
    public static final EnumProperty<RoofHalf> HALF = EnumProperty.of("half", RoofHalf.class);
    public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final VoxelShape ROOF_FLAT_TIP = Block.createCuboidShape(0, 0, 0, 16, 8, 16);
    public static final VoxelShape ROOF_FLAT_BASE = Block.createCuboidShape(0, 8, 0, 16, 16, 16);

    public IsotropicRoofFlatBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(HALF, RoofHalf.TIP).with(AXIS, Direction.Axis.X)
                .with(WATERLOGGED, false));
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
        builder.add(HALF, AXIS, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(AXIS, ctx.getPlayerFacing().getAxis()).with(HALF, getPlacementHalf(ctx))
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }


    @Override
    public boolean isGlass() {
        return false;
    }

    @Override
    public VoxelShape getShapeStatic(BlockState state) {
        return switch (state.get(HALF)) {
            case BASE -> ROOF_FLAT_BASE;
            case TIP -> ROOF_FLAT_TIP;
        };
    }
}
