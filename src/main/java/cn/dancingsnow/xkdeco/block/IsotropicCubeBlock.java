package cn.dancingsnow.xkdeco.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public final class IsotropicCubeBlock extends Block implements XKDecoBlock.Isotropic {
    private final boolean isGlass;

    public IsotropicCubeBlock(Settings settings, boolean isGlass) {
        super(settings);
        this.isGlass = isGlass;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideInvisible(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return cubeSideInvisible(pState, pAdjacentBlockState, pDirection) || super.isSideInvisible(pState, pAdjacentBlockState, pDirection);
    }

    public static boolean cubeSideInvisible(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        var block1 = pState.getBlock();
        var block2 = pAdjacentBlockState.getBlock();
        if (block2 instanceof Isotropic ib2 && ib2.isGlass() && block1 instanceof Isotropic ib1 && ib1.isGlass()) {
            var shape1 = ib2.getShapeStatic(pAdjacentBlockState);
            var shape2 = ib1.getShapeStatic(pState);
            return (Block.isFaceFullSquare(shape1, pDirection) && Block.isFaceFullSquare(shape2, pDirection.getOpposite()) && !(pAdjacentBlockState.getBlock() instanceof StairsBlock))
                    || (pAdjacentBlockState.getBlock() instanceof StairsBlock) && ((pAdjacentBlockState.get(StairsBlock.HALF) == BlockHalf.BOTTOM && pDirection == Direction.UP)
                    || pAdjacentBlockState.get(StairsBlock.HALF) == BlockHalf.TOP && pDirection == Direction.DOWN);
        }

        return false;
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
        return VoxelShapes.fullCube();
    }
}
