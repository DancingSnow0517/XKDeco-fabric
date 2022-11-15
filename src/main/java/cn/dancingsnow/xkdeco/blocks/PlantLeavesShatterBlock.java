package cn.dancingsnow.xkdeco.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public final class PlantLeavesShatterBlock extends LeavesBlock implements XKDecoBlock.Plant {
    private static final VoxelShape AABB = Block.createCuboidShape(0, 0, 0, 16, 1, 16);

    public PlantLeavesShatterBlock(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AABB;
    }
}
