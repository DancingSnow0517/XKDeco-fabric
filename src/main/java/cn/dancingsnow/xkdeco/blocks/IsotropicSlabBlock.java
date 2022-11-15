package cn.dancingsnow.xkdeco.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public final class IsotropicSlabBlock extends SlabBlock implements XKDecoBlock.Isotropic {
    private final boolean isGlass;

    public IsotropicSlabBlock(Settings settings, boolean isGlass) {
        super(settings);
        this.isGlass = isGlass;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideInvisible(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        boolean faceBlocked = false;
        var block = pAdjacentBlockState.getBlock();
        if (block instanceof Isotropic ib && ib.isGlass()) {
            var shape1 = ib.getShapeStatic(pAdjacentBlockState);
            var shape2 = this.getShapeStatic(pState);
            if ((Block.isFaceFullSquare(shape1, pDirection) && Block.isFaceFullSquare(shape2, pDirection.getOpposite()))) {
                faceBlocked = true;
            }
            if (((pAdjacentBlockState.isOf(this) && pAdjacentBlockState.get(TYPE) == pState.get(TYPE)) || pAdjacentBlockState.getBlock() instanceof IsotropicCubeBlock)
                    && pDirection.getAxis() != Direction.Axis.Y) {
                faceBlocked = true;
            }
        }
        return (this.isGlass && faceBlocked) ||super.isSideInvisible(pAdjacentBlockState, pAdjacentBlockState, pDirection);
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
        SlabType slabtype = state.get(TYPE);
        return switch (slabtype) {
            case DOUBLE -> VoxelShapes.fullCube();
            case TOP -> TOP_SHAPE;
            default -> BOTTOM_SHAPE;
        };
    }
}
