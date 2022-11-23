package cn.dancingsnow.xkdeco.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public final class IsotropicFallingBlock extends FallingBlock implements XKDecoBlock.Isotropic {


    public IsotropicFallingBlock(Settings settings) {
        super(settings);
    }
    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideInvisible(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return cubeSideInvisible(pState, pAdjacentBlockState, pDirection) || super.isSideInvisible(pState, pAdjacentBlockState, pDirection);
    }

    @SuppressWarnings("DuplicatedCode")
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
    public boolean isGlass() {
        return false;
    }

    @Override
    public VoxelShape getShapeStatic(BlockState state) {
        return VoxelShapes.fullCube();
    }
}
