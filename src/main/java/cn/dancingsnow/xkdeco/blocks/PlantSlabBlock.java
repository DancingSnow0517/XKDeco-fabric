package cn.dancingsnow.xkdeco.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public final class PlantSlabBlock extends SlabBlock implements XKDecoBlock.Plant {
    private static final VoxelShape PATH_TOP_AABB = Block.createCuboidShape(0, 8, 0, 16, 15, 16);
    private static final VoxelShape PATH_BOTTOM_AABB = Block.createCuboidShape(0, 0, 0, 16, 7, 16);
    private static final VoxelShape PATH_DOUBLE_AABB = Block.createCuboidShape(0, 0, 0, 16, 15, 16);

    private final boolean isPath;
    private final Identifier dirtSlab;

    public PlantSlabBlock(Settings settings, boolean isPath, Identifier dirtSlab) {
        super(settings);
        this.isPath = isPath;
        this.dirtSlab = dirtSlab;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return this.isPath || state.get(TYPE) == SlabType.BOTTOM;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(TYPE)) {
            case TOP -> this.isPath ? PATH_TOP_AABB : TOP_SHAPE;
            case BOTTOM -> this.isPath ? PATH_BOTTOM_AABB : BOTTOM_SHAPE;
            case DOUBLE -> this.isPath ? PATH_BOTTOM_AABB : VoxelShapes.fullCube();
        };
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis() == Direction.Axis.Y && state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.isPath) {
            this.turnToDirt(state, world, pos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (this.isPath && state.get(TYPE) != SlabType.BOTTOM) {
            var aboveState = world.getBlockState(pos.up());
            return !aboveState.getMaterial().isSolid() || aboveState.getBlock() instanceof FenceGateBlock;
        }
        return true;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !this.isPath;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.canSetBlock(pos)) {
            if (!this.isPath && !canBeGrass(state, world, pos)) {
                turnToDirt(state, world, pos);
            }
        }
        super.randomTick(state, world, pos, random);
    }

    private void turnToDirt(BlockState state, ServerWorld world, BlockPos pos) {
        var dirtSlabBlock = Registry.BLOCK.get(dirtSlab);
        if (state.getBlock() != dirtSlabBlock) {
            world.setBlockState(pos, dirtSlabBlock.getDefaultState()
                    .with(TYPE, state.get(TYPE)).with(WATERLOGGED, state.get(WATERLOGGED)));
        }
    }

    private static boolean canBeGrass(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(TYPE) != SlabType.BOTTOM) {
            var abovePos = pos.up();
            var aboveState = world.getBlockState(abovePos);
            if (!aboveState.isOf(Blocks.SNOW) || aboveState.get(SnowBlock.LAYERS) != 1) {
                if (aboveState.getFluidState().getLevel() != 8) {
                    return ChunkLightProvider.getRealisticOpacity(world, state, pos, aboveState, abovePos,
                            Direction.UP, aboveState.getOpacity(world, abovePos)) < world.getMaxLightLevel();
                }
                return false;
            }
            return true;
        }
        return !state.get(WATERLOGGED);
    }
}
