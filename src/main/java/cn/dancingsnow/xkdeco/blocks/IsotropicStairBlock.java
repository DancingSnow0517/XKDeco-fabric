package cn.dancingsnow.xkdeco.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public final class IsotropicStairBlock extends StairsBlock implements XKDecoBlock.Isotropic {
    public static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};

    private final boolean isGlass;

    public IsotropicStairBlock(Settings settings, boolean isGlass) {
        super(Blocks.AIR.getDefaultState(), settings);
        this.isGlass = isGlass;
    }

    @Override
    @SuppressWarnings({"deprecation", "DuplicatedCode"})
    public boolean isSideInvisible(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        boolean faceBlocked = false;
        var block = pAdjacentBlockState.getBlock();
        if (block instanceof Isotropic ib && ib.isGlass()) {
            var shape1 = ib.getShapeStatic(pAdjacentBlockState);
            var shape2 = this.getShapeStatic(pState);
            if ((Block.isFaceFullSquare(shape1, pDirection) && Block.isFaceFullSquare(shape2, pDirection.getOpposite()))) {
                faceBlocked = true;
            }
        }
        return (this.isGlass && faceBlocked) || super.isSideInvisible(pState, pAdjacentBlockState, pDirection);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return this.isGlass ? 1.0F : super.getAmbientOcclusionLightLevel(state, world, pos);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return this.isGlass || super.isTranslucent(state, world, pos);
    }

    @Override
    public boolean isGlass() {
        return isGlass;
    }

    @Override
    public VoxelShape getShapeStatic(BlockState state) {
        return (state.get(HALF) == BlockHalf.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_BY_STATE[getShapeIndexS(state)]];
    }

    private static int getShapeIndexS(BlockState pState) {
        return pState.get(SHAPE).ordinal() * 4 + pState.get(FACING).getHorizontal();
    }
}
