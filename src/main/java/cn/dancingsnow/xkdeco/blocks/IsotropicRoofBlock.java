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
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static cn.dancingsnow.xkdeco.utils.RoofUtil.RoofHalf;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.RoofShape;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.RoofVariant;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.Rotation;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isClosedSide;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isEave;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isFlatRoof;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isOpenSide;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isRoof;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.tryConnectTo;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.tryConnectToEave;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.tryConnectToFlat;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isHalfOpenClockWiseSide;
import static cn.dancingsnow.xkdeco.utils.RoofUtil.isHalfOpenCounterClockWiseSide;


public final class IsotropicRoofBlock extends Block implements Waterloggable, XKDecoBlock.Isotropic {

    public static final EnumProperty<RoofVariant> VARIANT = EnumProperty.of("variant", RoofVariant.class);
    public static final EnumProperty<RoofShape> SHAPE = EnumProperty.of("shape", RoofShape.class);
    public static final EnumProperty<RoofHalf> HALF = EnumProperty.of("half", RoofHalf.class);
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final VoxelShape ROOF_E = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 8, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_INNER_EN = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 8, 16, 8), Block.createCuboidShape(0, 8, 8, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_INNER_NW = VoxelShapes.union(Block.createCuboidShape(0, 8, 8, 8, 16, 16), Block.createCuboidShape(8, 8, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_INNER_SE = VoxelShapes.union(Block.createCuboidShape(8, 8, 0, 16, 16, 8), Block.createCuboidShape(0, 8, 0, 8, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_INNER_BASE_EN = VoxelShapes.union(Block.createCuboidShape(0, 16, 0, 8, 24, 8), Block.createCuboidShape(0, 16, 8, 16, 24, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_INNER_BASE_NW = VoxelShapes.union(Block.createCuboidShape(0, 16, 8, 8, 24, 16), Block.createCuboidShape(8, 16, 0, 16, 24, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_INNER_BASE_SE = VoxelShapes.union(Block.createCuboidShape(8, 16, 0, 16, 24, 8), Block.createCuboidShape(0, 16, 0, 8, 24, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_INNER_BASE_WS = VoxelShapes.union(Block.createCuboidShape(8, 16, 8, 16, 24, 16), Block.createCuboidShape(0, 16, 0, 16, 24, 8), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_INNER_WS = VoxelShapes.union(Block.createCuboidShape(8, 8, 8, 16, 16, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_N = VoxelShapes.union(Block.createCuboidShape(0, 8, 8, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_OUTER_EN = VoxelShapes.union(Block.createCuboidShape(0, 8, 8, 8, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_OUTER_NW = VoxelShapes.union(Block.createCuboidShape(8, 8, 8, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_OUTER_SE = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 8, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_OUTER_BASE_EN = VoxelShapes.union(Block.createCuboidShape(0, 16, 8, 8, 24, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_OUTER_BASE_NW = VoxelShapes.union(Block.createCuboidShape(8, 16, 8, 16, 24, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_OUTER_BASE_SE = VoxelShapes.union(Block.createCuboidShape(0, 16, 0, 8, 24, 8), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_OUTER_BASE_WS = VoxelShapes.union(Block.createCuboidShape(8, 16, 0, 16, 24, 8), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_OUTER_WS = VoxelShapes.union(Block.createCuboidShape(8, 8, 0, 16, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_S = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 16, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape ROOF_BASE_E = VoxelShapes.union(Block.createCuboidShape(0, 16, 0, 8, 24, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_BASE_N = VoxelShapes.union(Block.createCuboidShape(0, 16, 8, 16, 24, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_BASE_S = VoxelShapes.union(Block.createCuboidShape(0, 16, 0, 16, 24, 8), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_BASE_W = VoxelShapes.union(Block.createCuboidShape(8, 16, 0, 16, 24, 16), Block.createCuboidShape(0, 8, 0, 16, 16, 16));
    public static final VoxelShape ROOF_W = VoxelShapes.union(Block.createCuboidShape(8, 8, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape SLOW_ROOF_E = VoxelShapes.union(Block.createCuboidShape(0, 4, 0, 8, 8, 16), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_INNER_EN = VoxelShapes.union(Block.createCuboidShape(0, 4, 8, 16, 8, 16), Block.createCuboidShape(0, 4, 0, 8, 8, 8), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_INNER_NW = VoxelShapes.union(Block.createCuboidShape(8, 4, 0, 16, 8, 16), Block.createCuboidShape(0, 4, 8, 8, 8, 16), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_INNER_SE = VoxelShapes.union(Block.createCuboidShape(0, 4, 0, 8, 8, 16), Block.createCuboidShape(8, 4, 0, 16, 8, 8), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_INNER_BASE_EN = VoxelShapes.union(Block.createCuboidShape(0, 12, 8, 16, 16, 16), Block.createCuboidShape(0, 12, 0, 8, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_INNER_BASE_NW = VoxelShapes.union(Block.createCuboidShape(8, 12, 0, 16, 16, 16), Block.createCuboidShape(0, 12, 8, 8, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_INNER_BASE_SE = VoxelShapes.union(Block.createCuboidShape(0, 12, 0, 8, 16, 16), Block.createCuboidShape(8, 12, 0, 16, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_INNER_BASE_WS = VoxelShapes.union(Block.createCuboidShape(0, 12, 0, 16, 16, 8), Block.createCuboidShape(8, 12, 8, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_INNER_WS = VoxelShapes.union(Block.createCuboidShape(0, 4, 0, 16, 8, 8), Block.createCuboidShape(8, 4, 8, 16, 8, 16), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_N = VoxelShapes.union(Block.createCuboidShape(0, 4, 8, 16, 8, 16), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_OUTER_EN = VoxelShapes.union(Block.createCuboidShape(0, 4, 8, 8, 8, 16), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_OUTER_NW = VoxelShapes.union(Block.createCuboidShape(8, 4, 8, 16, 8, 16), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_OUTER_SE = VoxelShapes.union(Block.createCuboidShape(0, 4, 0, 8, 8, 8), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_OUTER_BASE_EN = VoxelShapes.union(Block.createCuboidShape(0, 12, 8, 8, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_OUTER_BASE_NW = VoxelShapes.union(Block.createCuboidShape(8, 12, 8, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_OUTER_BASE_SE = VoxelShapes.union(Block.createCuboidShape(0, 12, 0, 8, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_OUTER_BASE_WS = VoxelShapes.union(Block.createCuboidShape(8, 12, 0, 16, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_OUTER_WS = VoxelShapes.union(Block.createCuboidShape(8, 4, 0, 16, 8, 8), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_S = VoxelShapes.union(Block.createCuboidShape(0, 4, 0, 16, 8, 8), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape SLOW_ROOF_BASE_E = VoxelShapes.union(Block.createCuboidShape(0, 12, 0, 8, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_BASE_N = VoxelShapes.union(Block.createCuboidShape(0, 12, 8, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_BASE_S = VoxelShapes.union(Block.createCuboidShape(0, 12, 0, 16, 16, 8), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_BASE_W = VoxelShapes.union(Block.createCuboidShape(8, 12, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 12, 16));
    public static final VoxelShape SLOW_ROOF_W = VoxelShapes.union(Block.createCuboidShape(8, 4, 0, 16, 8, 16), Block.createCuboidShape(0, 0, 0, 16, 4, 16));
    public static final VoxelShape STEEP_ROOF_E = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 4, 16, 16), Block.createCuboidShape(0, 0, 0, 8, 8, 16));
    public static final VoxelShape STEEP_ROOF_INNER_EN = VoxelShapes.union(Block.createCuboidShape(0, 8, 12, 16, 16, 16), Block.createCuboidShape(0, 8, 0, 4, 16, 12));
    public static final VoxelShape STEEP_ROOF_INNER_NW = VoxelShapes.union(Block.createCuboidShape(12, 8, 0, 16, 16, 16), Block.createCuboidShape(0, 8, 12, 12, 16, 16));
    public static final VoxelShape STEEP_ROOF_INNER_SE = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 4, 16, 16), Block.createCuboidShape(4, 8, 0, 16, 16, 4));
    public static final VoxelShape STEEP_ROOF_INNER_BASE_EN = VoxelShapes.union(Block.createCuboidShape(0, 8, 4, 16, 16, 16), Block.createCuboidShape(0, 8, 0, 12, 16, 4), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_INNER_BASE_NW = VoxelShapes.union(Block.createCuboidShape(4, 8, 0, 16, 16, 16), Block.createCuboidShape(0, 8, 4, 4, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_INNER_BASE_SE = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 12, 16, 16), Block.createCuboidShape(12, 8, 0, 16, 16, 12), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_INNER_BASE_WS = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 16, 16, 12), Block.createCuboidShape(4, 8, 12, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_INNER_WS = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 16, 16, 4), Block.createCuboidShape(12, 8, 4, 16, 16, 16));
    public static final VoxelShape STEEP_ROOF_N = VoxelShapes.union(Block.createCuboidShape(0, 8, 12, 16, 16, 16), Block.createCuboidShape(0, 0, 8, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_OUTER_EN = VoxelShapes.union(Block.createCuboidShape(0, 0, 8, 8, 8, 16), Block.createCuboidShape(0, 8, 12, 4, 16, 16));
    public static final VoxelShape STEEP_ROOF_OUTER_NW = VoxelShapes.union(Block.createCuboidShape(8, 0, 8, 16, 8, 16), Block.createCuboidShape(12, 8, 12, 16, 16, 16));
    public static final VoxelShape STEEP_ROOF_OUTER_SE = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 8, 8, 8), Block.createCuboidShape(0, 8, 0, 4, 16, 4));
    public static final VoxelShape STEEP_ROOF_OUTER_BASE_EN = VoxelShapes.union(Block.createCuboidShape(0, 8, 4, 12, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_OUTER_BASE_NW = VoxelShapes.union(Block.createCuboidShape(4, 8, 4, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_OUTER_BASE_SE = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 12, 16, 12), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_OUTER_BASE_WS = VoxelShapes.union(Block.createCuboidShape(4, 8, 0, 16, 16, 12), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_OUTER_WS = VoxelShapes.union(Block.createCuboidShape(8, 0, 0, 16, 8, 8), Block.createCuboidShape(12, 8, 0, 16, 16, 4));
    public static final VoxelShape STEEP_ROOF_S = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 16, 16, 4), Block.createCuboidShape(0, 0, 0, 16, 8, 8));
    public static final VoxelShape STEEP_ROOF_BASE_E = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 12, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_BASE_N = VoxelShapes.union(Block.createCuboidShape(0, 8, 4, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_BASE_S = VoxelShapes.union(Block.createCuboidShape(0, 8, 0, 16, 16, 12), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_BASE_W = VoxelShapes.union(Block.createCuboidShape(4, 8, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16));
    public static final VoxelShape STEEP_ROOF_W = VoxelShapes.union(Block.createCuboidShape(12, 8, 0, 16, 16, 16), Block.createCuboidShape(8, 0, 0, 16, 8, 16));

    public static final List<VoxelShape> ROOF_SHAPES = List.of(
            VoxelShapes.fullCube(), ROOF_OUTER_EN, ROOF_E, ROOF_INNER_SE,
            ROOF_INNER_WS, VoxelShapes.fullCube(), ROOF_OUTER_SE, ROOF_S,
            ROOF_W, ROOF_INNER_NW, VoxelShapes.fullCube(), ROOF_OUTER_WS,
            ROOF_OUTER_NW, ROOF_N, ROOF_INNER_EN, VoxelShapes.fullCube(),
            VoxelShapes.fullCube(), SLOW_ROOF_OUTER_EN, SLOW_ROOF_E, SLOW_ROOF_INNER_SE,
            SLOW_ROOF_INNER_WS, VoxelShapes.fullCube(), SLOW_ROOF_OUTER_SE, SLOW_ROOF_S,
            SLOW_ROOF_W, SLOW_ROOF_INNER_NW, VoxelShapes.fullCube(), SLOW_ROOF_OUTER_WS,
            SLOW_ROOF_OUTER_NW, SLOW_ROOF_N, SLOW_ROOF_INNER_EN, VoxelShapes.fullCube(),
            VoxelShapes.fullCube(), STEEP_ROOF_OUTER_EN, STEEP_ROOF_E, STEEP_ROOF_INNER_SE,
            STEEP_ROOF_INNER_WS, VoxelShapes.fullCube(), STEEP_ROOF_OUTER_SE, STEEP_ROOF_S,
            STEEP_ROOF_W, STEEP_ROOF_INNER_NW, VoxelShapes.fullCube(), STEEP_ROOF_OUTER_WS,
            STEEP_ROOF_OUTER_NW, STEEP_ROOF_N, STEEP_ROOF_INNER_EN, VoxelShapes.fullCube());
    public static final List<VoxelShape> ROOF_BASE_SHAPES = List.of(
            VoxelShapes.fullCube(), ROOF_OUTER_BASE_EN, ROOF_BASE_E, ROOF_INNER_BASE_SE,
            ROOF_INNER_BASE_WS, VoxelShapes.fullCube(), ROOF_OUTER_BASE_SE, ROOF_BASE_S,
            ROOF_BASE_W, ROOF_INNER_BASE_NW, VoxelShapes.fullCube(), ROOF_OUTER_BASE_WS,
            ROOF_OUTER_BASE_NW, ROOF_BASE_N, ROOF_INNER_BASE_EN, VoxelShapes.fullCube(),
            VoxelShapes.fullCube(), SLOW_ROOF_OUTER_BASE_EN, SLOW_ROOF_BASE_E, SLOW_ROOF_INNER_BASE_SE,
            SLOW_ROOF_INNER_BASE_WS, VoxelShapes.fullCube(), SLOW_ROOF_OUTER_BASE_SE, SLOW_ROOF_BASE_S,
            SLOW_ROOF_BASE_W, SLOW_ROOF_INNER_BASE_NW, VoxelShapes.fullCube(), SLOW_ROOF_OUTER_BASE_WS,
            SLOW_ROOF_OUTER_BASE_NW, SLOW_ROOF_BASE_N, SLOW_ROOF_INNER_BASE_EN, VoxelShapes.fullCube(),
            VoxelShapes.fullCube(), STEEP_ROOF_OUTER_BASE_EN, STEEP_ROOF_BASE_E, STEEP_ROOF_INNER_BASE_SE,
            STEEP_ROOF_INNER_BASE_WS, VoxelShapes.fullCube(), STEEP_ROOF_OUTER_BASE_SE, STEEP_ROOF_BASE_S,
            STEEP_ROOF_BASE_W, STEEP_ROOF_INNER_BASE_NW, VoxelShapes.fullCube(), STEEP_ROOF_OUTER_BASE_WS,
            STEEP_ROOF_OUTER_BASE_NW, STEEP_ROOF_BASE_N, STEEP_ROOF_INNER_BASE_EN, VoxelShapes.fullCube());

    public IsotropicRoofBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(VARIANT, RoofVariant.NORMAL)
                .with(SHAPE, RoofShape.STRAIGHT)
                .with(HALF, RoofHalf.TIP)
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasSidedTransparency(BlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShapeStatic(state);
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

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var initialState = this.getDefaultState()
                .with(FACING, ctx.getPlayerFacing())
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);

        for (var trial : List.of(
                tryConnectTo(Rotation.FRONT,
                        (r, s, h, v) -> isHalfOpenClockWiseSide(s, r, Rotation.BACK),
                        (curr, tgt) -> curr.with(VARIANT, tgt.get(VARIANT)).with(SHAPE, RoofShape.OUTER).with(HALF, tgt.get(HALF)).with(FACING, curr.get(FACING).rotateYCounterclockwise())),
                tryConnectTo(Rotation.FRONT,
                        (r, s, h, v) -> isHalfOpenCounterClockWiseSide(s, r, Rotation.BACK),
                        (curr, tgt) -> curr.with(VARIANT, tgt.get(VARIANT)).with(SHAPE, RoofShape.OUTER).with(HALF, tgt.get(HALF)).with(FACING, curr.get(FACING))),
                tryConnectTo(Rotation.BACK,
                        (r, s, h, v) -> isHalfOpenCounterClockWiseSide(s, r, Rotation.FRONT),
                        (curr, tgt) -> curr.with(VARIANT, tgt.get(VARIANT)).with(SHAPE, RoofShape.INNER).with(HALF, tgt.get(HALF)).with(FACING, curr.get(FACING).rotateYCounterclockwise())),
                tryConnectTo(Rotation.BACK,
                        (r, s, h, v) -> isHalfOpenClockWiseSide(s, r, Rotation.FRONT),
                        (curr, tgt) -> curr.with(VARIANT, tgt.get(VARIANT)).with(SHAPE, RoofShape.INNER).with(HALF, tgt.get(HALF)).with(FACING, curr.get(FACING))),

                // try to become a slow, non-straight roof matching the roof at left/right
                tryConnectTo(Rotation.LEFT,
                        (r, s, h, v) -> r == Rotation.RIGHT && s == RoofShape.STRAIGHT && h == RoofHalf.TIP && v == RoofVariant.SLOW,
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.OUTER).with(HALF, RoofHalf.BASE).with(VARIANT, RoofVariant.SLOW).with(FACING, tgt.get(FACING).rotateYCounterclockwise())),
                tryConnectTo(Rotation.RIGHT,
                        (r, s, h, v) -> r == Rotation.LEFT && s == RoofShape.STRAIGHT && h == RoofHalf.TIP && v == RoofVariant.SLOW,
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.OUTER).with(HALF, RoofHalf.BASE).with(VARIANT, RoofVariant.SLOW).with(FACING, tgt.get(FACING))),
                tryConnectTo(Rotation.LEFT,
                        (r, s, h, v) -> r == Rotation.LEFT && s == RoofShape.STRAIGHT && h == RoofHalf.BASE && v == RoofVariant.SLOW,
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.INNER).with(HALF, RoofHalf.TIP).with(VARIANT, RoofVariant.SLOW).with(FACING, tgt.get(FACING))),
                tryConnectTo(Rotation.RIGHT,
                        (r, s, h, v) -> r == Rotation.RIGHT && s == RoofShape.STRAIGHT && h == RoofHalf.BASE && v == RoofVariant.SLOW,
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.INNER).with(HALF, RoofHalf.TIP).with(VARIANT, RoofVariant.SLOW).with(FACING, tgt.get(FACING).rotateYCounterclockwise())),

                // try to become a slow, non-straight roof matching the roof eave at left/right
                tryConnectToEave(Rotation.LEFT,
                        (r, s, h) -> r == Rotation.RIGHT && s == RoofShape.STRAIGHT && h == RoofHalf.TIP,
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.OUTER).with(HALF, RoofHalf.BASE).with(VARIANT, RoofVariant.SLOW).with(FACING, tgt.get(FACING).rotateYCounterclockwise())),
                tryConnectToEave(Rotation.RIGHT,
                        (r, s, h) -> r == Rotation.LEFT && s == RoofShape.STRAIGHT && h == RoofHalf.TIP,
                        (curr, tgt) -> curr.with(SHAPE, RoofShape.OUTER).with(HALF, RoofHalf.BASE).with(VARIANT, RoofVariant.SLOW).with(FACING, tgt.get(FACING))),

                // adjust self to become a slow, straight roof
                tryConnectTo(Rotation.FRONT,
                        (r, s, h, v) -> r == Rotation.FRONT && s == RoofShape.STRAIGHT && h == RoofHalf.TIP && v == RoofVariant.NORMAL
                                || isClosedSide(s, r, Rotation.BACK) && h == RoofHalf.BASE && v == RoofVariant.SLOW,
                        (curr, tgt) -> curr.with(HALF, RoofHalf.TIP).with(VARIANT, RoofVariant.SLOW)),
                tryConnectTo(Rotation.BACK,
                        (r, s, h, v) -> r == Rotation.FRONT && s == RoofShape.STRAIGHT && h == RoofHalf.TIP && v == RoofVariant.NORMAL
                                || isOpenSide(s, r, Rotation.FRONT) && h == RoofHalf.TIP && v == RoofVariant.SLOW,
                        (curr, tgt) -> curr.with(HALF, RoofHalf.BASE).with(VARIANT, RoofVariant.SLOW)),

                // adjust self to become a slow, straight roof matching the flat roof at front/back
                tryConnectToFlat(Rotation.FRONT,
                        (p, h) -> p && h == RoofHalf.TIP,
                        (curr, tgt) -> curr.with(HALF, RoofHalf.TIP).with(VARIANT, RoofVariant.SLOW)),
                tryConnectToFlat(Rotation.BACK,
                        (p, h) -> p && h == RoofHalf.TIP,
                        (curr, tgt) -> curr.with(HALF, RoofHalf.BASE).with(VARIANT, RoofVariant.SLOW)),

                // adjust self to become a slow, straight roof matching the roof eave at back
                tryConnectToEave(Rotation.BACK,
                        (r, s, h) -> isOpenSide(s, r, Rotation.FRONT) && h == RoofHalf.TIP,
                        (curr, tgt) -> curr.with(HALF, RoofHalf.BASE).with(VARIANT, RoofVariant.SLOW)),

                // adjust self to become a steep, straight roof
                tryConnectTo(Rotation.UP,
                        (r, s, h, v) -> r == Rotation.FRONT && h == RoofHalf.TIP && (v == RoofVariant.NORMAL || v == RoofVariant.STEEP),
                        (curr, tgt) -> curr.with(HALF, RoofHalf.BASE).with(VARIANT, RoofVariant.STEEP)),
                tryConnectTo(Rotation.DOWN,
                        (r, s, h, v) -> r == Rotation.FRONT && (h == RoofHalf.TIP && v == RoofVariant.NORMAL || h == RoofHalf.BASE && v == RoofVariant.STEEP),
                        (curr, tgt) -> curr.with(HALF, RoofHalf.TIP).with(VARIANT, RoofVariant.STEEP))

        )) {
            var result = trial.apply(ctx.getWorld(), ctx.getBlockPos(), ctx.getPlayerFacing(), initialState);
            if (result.isPresent()) return result.get();
        }
        return initialState;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        var currDirection = state.get(FACING);
        var currShape = state.get(SHAPE);
        var currVariant = state.get(VARIANT);

        if (isRoof(neighborState)) {
            var facingDirection = neighborState.get(FACING);
            var facingHalf = neighborState.get(HALF);
            var facingShape = neighborState.get(SHAPE);
            var facingVariant = neighborState.get(VARIANT);

            var rotation = Rotation.fromDirections(currDirection, direction);
            switch (rotation) {
                case LEFT, RIGHT -> {
                }
                case FRONT, BACK -> {
                    if (facingVariant == RoofVariant.SLOW && facingShape == RoofShape.STRAIGHT
                            && currDirection == facingDirection
                            && currVariant == RoofVariant.NORMAL && currShape == RoofShape.STRAIGHT) {
                        return state.with(VARIANT, RoofVariant.SLOW).with(HALF, facingHalf.otherHalf());
                    }
                }
                case UP, DOWN -> {
                    if (facingVariant == RoofVariant.STEEP && facingShape == RoofShape.STRAIGHT
                            && currVariant == RoofVariant.NORMAL && currShape == RoofShape.STRAIGHT) {
                        return state.with(VARIANT, RoofVariant.STEEP).with(HALF, facingHalf.otherHalf());
                    }
                }
            }
        } else if (isFlatRoof(neighborState)) {
            var facingAxis = neighborState.get(IsotropicRoofFlatBlock.AXIS);
            var facingHalf = neighborState.get(IsotropicRoofFlatBlock.HALF);

            var rotation = Rotation.fromDirections(currDirection, direction);

            if (currVariant == RoofVariant.NORMAL && currShape == RoofShape.STRAIGHT
                    && currDirection.getAxis() == facingAxis
                    && facingHalf == RoofHalf.TIP) {
                return state.with(VARIANT, RoofVariant.SLOW)
                        .with(HALF, rotation == Rotation.FRONT ? RoofHalf.TIP : RoofHalf.BASE);
            }
        } else if (isEave(neighborState)) {
            var facingDirection = neighborState.get(FACING);
            var facingHalf = neighborState.get(HALF);
            var facingShape = neighborState.get(SHAPE);

            var rotation = Rotation.fromDirections(currDirection, direction);

            if (rotation == Rotation.BACK
                    && facingHalf == RoofHalf.TIP
                    && isOpenSide(facingShape, Rotation.fromDirections(currDirection, facingDirection), Rotation.FRONT)
                    && currVariant == RoofVariant.NORMAL && currShape == RoofShape.STRAIGHT) {
                return state.with(VARIANT, RoofVariant.SLOW).with(HALF, RoofHalf.BASE);
            }
        }
        return state;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VARIANT, SHAPE, HALF, FACING, WATERLOGGED);
    }

    @Override
    public boolean isGlass() {
        return false;
    }

    @Override
    public VoxelShape getShapeStatic(BlockState state) {
        var leftRight = getConnectionLeftRight(state.get(FACING), state.get(SHAPE));
        var leftRightIndex = leftRight.getLeft().getHorizontal() * 4 + leftRight.getRight().getHorizontal();
        return switch (state.get(HALF)) {
            case TIP -> ROOF_SHAPES.get(state.get(VARIANT).ordinal() * 16 + leftRightIndex);
            case BASE -> ROOF_BASE_SHAPES.get(state.get(VARIANT).ordinal() * 16 + leftRightIndex);
        };
    }

    private static Pair<Direction, Direction> getConnectionLeftRight(Direction facing, RoofShape shape) {
        return switch (shape) {
            case STRAIGHT -> Pair.of(facing.rotateYCounterclockwise(), facing.rotateYClockwise());
            case INNER -> Pair.of(facing.rotateYCounterclockwise(), facing.getOpposite());
            case OUTER -> Pair.of(facing, facing.rotateYClockwise());
        };
    }
}
