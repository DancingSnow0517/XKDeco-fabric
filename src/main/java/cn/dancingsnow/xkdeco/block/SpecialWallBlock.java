package cn.dancingsnow.xkdeco.block;

import cn.dancingsnow.xkdeco.XKDeco;
import cn.dancingsnow.xkdeco.blockentity.WallBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class SpecialWallBlock extends WallBlock implements BlockEntityProvider, XKDecoBlock.Special {
    private static final VoxelShape NORTH_TEST = Block.createCuboidShape(7, 0, 0, 9, 16, 9);
    private static final VoxelShape SOUTH_TEST = Block.createCuboidShape(7, 0, 7, 9, 16, 16);
    private static final VoxelShape WEST_TEST = Block.createCuboidShape(0, 0, 7, 9, 16, 9);
    private static final VoxelShape EAST_TEST = Block.createCuboidShape(7, 0, 7, 16, 16, 9);

    private final WallBlock wall;

    public SpecialWallBlock(WallBlock wallDelegate) {
        super(Settings.copy(wallDelegate));
        this.wall = wallDelegate;
    }

    public WallBlock getWallDelegate() {
        return this.wall;
    }

    public Optional<Block> connectsTo(BlockState pState) {
        if (pState.isIn(BlockTags.WALLS)) {
            var block = pState.getBlock();
            if (!(block instanceof SpecialWallBlock)) {
                return Optional.of(block);
            }
        }
        return Optional.empty();
    }

    private WallShape makeWallState(boolean connectedTo, VoxelShape aboveShape, VoxelShape connectedToShape) {
        if (!connectedTo) {
            return WallShape.NONE;
        }
        if (VoxelShapes.matchesAnywhere(connectedToShape, aboveShape, BooleanBiFunction.ONLY_FIRST)) {
            return WallShape.LOW;
        }
        return WallShape.TALL;
    }

    private BlockState updateSides(BlockPos pos, VoxelShape aboveShape,
                                   BlockState blockState, WorldAccess world) {
        var northWall = this.connectsTo(world.getBlockState(pos.north()));
        var eastWall = this.connectsTo(world.getBlockState(pos.east()));
        var southWall = this.connectsTo(world.getBlockState(pos.south()));
        var westWall = this.connectsTo(world.getBlockState(pos.west()));
        return blockState
                .with(NORTH_SHAPE, this.makeWallState(northWall.isPresent(), aboveShape, NORTH_TEST))
                .with(EAST_SHAPE, this.makeWallState(eastWall.isPresent(), aboveShape, EAST_TEST))
                .with(SOUTH_SHAPE, this.makeWallState(southWall.isPresent(), aboveShape, SOUTH_TEST))
                .with(WEST_SHAPE, this.makeWallState(westWall.isPresent(), aboveShape, WEST_TEST));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var pos = ctx.getBlockPos();
        var abovePos = pos.up();
        var aboveBlockState = ctx.getWorld().getBlockState(abovePos);
        var aboveShape = aboveBlockState.getCollisionShape(ctx.getWorld(), abovePos).getFace(Direction.DOWN);

        var fluidState = ctx.getWorld().getFluidState(pos);
        var blockState = this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return this.updateSides(pos, aboveShape, blockState, ctx.getWorld()).with(UP, true);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.LAVA.getTickRate(world));
        }

        if (direction != Direction.DOWN) {
            var abovePos = pos.up();
            var aboveBlockState = world.getBlockState(abovePos);
            var aboveShape = aboveBlockState.getCollisionShape(world, abovePos).getFace(Direction.DOWN);

            if (world.getBlockEntity(pos) instanceof WallBlockEntity blockEntity) {
                blockEntity.updateBlocksFromLevel(this);
            }

            return this.updateSides(pos, aboveShape, state, world);

        }
        return state;
    }

    @Override
    public String getTranslationKey() {
        return this.wall.getTranslationKey();
    }

    @Override
    public MutableText getName() {
        return Text.translatable("block." + XKDeco.MOD_ID + ".special_wall", super.getName());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        super.spawnBreakParticles(world, player, pos, this.wall.getDefaultState());
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
