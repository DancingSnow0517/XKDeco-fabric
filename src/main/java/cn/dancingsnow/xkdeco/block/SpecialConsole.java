package cn.dancingsnow.xkdeco.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public final class SpecialConsole extends StairsBlock implements XKDecoBlock.Special {
    private static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};

    public SpecialConsole(Settings settings) {
        super(Blocks.AIR.getDefaultState(), settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOTTOM_SHAPES[SHAPE_BY_STATE[this.getShapeIndex(state)]];
    }

    private int getShapeIndex(BlockState pState) {
        return pState.get(SHAPE).ordinal() * 4 + pState.get(FACING).getHorizontal();
    }
}
