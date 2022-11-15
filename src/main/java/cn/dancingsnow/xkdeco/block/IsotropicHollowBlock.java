package cn.dancingsnow.xkdeco.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public final class IsotropicHollowBlock extends Block implements Waterloggable, XKDecoBlock.Isotropic {

    private static final VoxelShape TABLE_BASE = Block.createCuboidShape(4, 0, 4, 12, 3, 12);
    private static final VoxelShape TABLE_LEG = Block.createCuboidShape(6, 3, 6, 10, 13, 10);
    private static final VoxelShape TABLE_TOP = Block.createCuboidShape(0, 13, 0, 16, 16, 16);
    private static final VoxelShape BIG_TABLE_TOP = Block.createCuboidShape(0, 8, 0, 16, 16, 16);
    private static final VoxelShape BIG_TABLE_LEG_NN = Block.createCuboidShape(0, 0, 0, 2, 8, 2);
    private static final VoxelShape BIG_TABLE_LEG_NP = Block.createCuboidShape(0, 0, 14, 2, 8, 16);
    private static final VoxelShape BIG_TABLE_LEG_PN = Block.createCuboidShape(14, 0, 0, 16, 8, 2);
    private static final VoxelShape BIG_TABLE_LEG_PP = Block.createCuboidShape(14, 0, 14, 16, 8, 16);

    public static final VoxelShape TABLE_SHAPE = VoxelShapes.union(TABLE_BASE, TABLE_LEG, TABLE_TOP);
    public static final VoxelShape BIG_TABLE_SHAPE = VoxelShapes.union(BIG_TABLE_TOP, BIG_TABLE_LEG_PP, BIG_TABLE_LEG_PN, BIG_TABLE_LEG_NP, BIG_TABLE_LEG_NN);

    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private final VoxelShape blockShape;

    public IsotropicHollowBlock(Settings settings, VoxelShape blockShape) {
        super(settings);
        this.blockShape = blockShape;
        this.setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.blockShape;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.EMPTY.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public boolean isGlass() {
        return false;
    }

    @Override
    public VoxelShape getShapeStatic(BlockState state) {
        return this.blockShape;
    }
}
