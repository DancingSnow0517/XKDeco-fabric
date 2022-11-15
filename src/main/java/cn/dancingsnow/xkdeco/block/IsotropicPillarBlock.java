package cn.dancingsnow.xkdeco.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public final class IsotropicPillarBlock extends PillarBlock implements XKDecoBlock.Isotropic {
    private final boolean isGlass;

    public IsotropicPillarBlock(Settings settings, boolean isGlass) {
        super(settings);
        this.isGlass = isGlass;
    }

    @Override
    @SuppressWarnings({"deprecation", "DuplicatedCode"})
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        boolean faceBlocked = false;
        var block = stateFrom.getBlock();
        if (block instanceof Isotropic ib && ib.isGlass()) {
            var shape1 = ib.getShapeStatic(stateFrom);
            var shape2 = this.getShapeStatic(state);
            if ((Block.isFaceFullSquare(shape1, direction) && Block.isFaceFullSquare(shape2, direction.getOpposite()))) {
                faceBlocked = true;
            }
        }
        return (this.isGlass && faceBlocked) || super.isSideInvisible(state, stateFrom, direction);
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
