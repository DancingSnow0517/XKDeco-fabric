package cn.dancingsnow.xkdeco.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public final class SpecialLightBar extends StairsBlock implements XKDecoBlock.Special {
    private static final VoxelShape SOUTH_BOTTOM = Block.createCuboidShape(0, 2, 13, 16, 6, 16);
    private static final VoxelShape NORTH_BOTTOM = Block.createCuboidShape(0, 2, 0, 16, 6, 3);
    private static final VoxelShape WEST_BOTTOM = Block.createCuboidShape(0, 2, 0, 3, 6, 16);
    private static final VoxelShape EAST_BOTTOM = Block.createCuboidShape(13, 2, 0, 16, 6, 16);

    private static final VoxelShape SOUTH_TOP = Block.createCuboidShape(0, 10, 13, 16, 14, 16);
    private static final VoxelShape NORTH_TOP = Block.createCuboidShape(0, 10, 0, 16, 14, 3);
    private static final VoxelShape WEST_TOP = Block.createCuboidShape(0, 10, 0, 3, 14, 16);
    private static final VoxelShape EAST_TOP = Block.createCuboidShape(13, 10, 0, 16, 14, 16);

    private static final VoxelShape NE_TOP_INNER = VoxelShapes.union(NORTH_TOP, EAST_TOP);
    private static final VoxelShape ES_TOP_INNER = VoxelShapes.union(SOUTH_TOP, EAST_TOP);
    private static final VoxelShape SW_TOP_INNER = VoxelShapes.union(WEST_TOP, SOUTH_TOP);
    private static final VoxelShape WN_TOP_INNER = VoxelShapes.union(NORTH_TOP, WEST_TOP);

    private static final VoxelShape NE_TOP_OUTER = VoxelShapes.combineAndSimplify(NORTH_TOP, EAST_TOP, BooleanBiFunction.AND);
    private static final VoxelShape ES_TOP_OUTER = VoxelShapes.combineAndSimplify(SOUTH_TOP, EAST_TOP, BooleanBiFunction.AND);
    private static final VoxelShape SW_TOP_OUTER = VoxelShapes.combineAndSimplify(WEST_TOP, SOUTH_TOP, BooleanBiFunction.AND);
    private static final VoxelShape WN_TOP_OUTER = VoxelShapes.combineAndSimplify(NORTH_TOP, WEST_TOP, BooleanBiFunction.AND);

    private static final VoxelShape NE_BOTTOM_INNER = VoxelShapes.union(NORTH_BOTTOM, EAST_BOTTOM);
    private static final VoxelShape ES_BOTTOM_INNER = VoxelShapes.union(SOUTH_BOTTOM, EAST_BOTTOM);
    private static final VoxelShape SW_BOTTOM_INNER = VoxelShapes.union(WEST_BOTTOM, SOUTH_BOTTOM);
    private static final VoxelShape WN_BOTTOM_INNER = VoxelShapes.union(NORTH_BOTTOM, WEST_BOTTOM);

    private static final VoxelShape NE_BOTTOM_OUTER = VoxelShapes.combineAndSimplify(NORTH_BOTTOM, EAST_BOTTOM, BooleanBiFunction.AND);
    private static final VoxelShape ES_BOTTOM_OUTER = VoxelShapes.combineAndSimplify(SOUTH_BOTTOM, EAST_BOTTOM, BooleanBiFunction.AND);
    private static final VoxelShape SW_BOTTOM_OUTER = VoxelShapes.combineAndSimplify(WEST_BOTTOM, SOUTH_BOTTOM, BooleanBiFunction.AND);
    private static final VoxelShape WN_BOTTOM_OUTER = VoxelShapes.combineAndSimplify(NORTH_BOTTOM, WEST_BOTTOM, BooleanBiFunction.AND);

    public SpecialLightBar(Settings settings) {
        super(Blocks.AIR.getDefaultState(), settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}
