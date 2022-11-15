package cn.dancingsnow.xkdeco.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public final class SpecialDessertBlock extends Block implements XKDecoBlock.Special {
    private static final int MAXIMUM_COUNT = 7;

    private static final IntProperty COUNT = IntProperty.of("count", 1, MAXIMUM_COUNT);

    private static final VoxelShape DESSERT_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 2, 15);

    public SpecialDessertBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(COUNT, MAXIMUM_COUNT));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return DESSERT_SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var item = player.getStackInHand(hand);
        if (world.isClient) {
            if (this.eat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }
            if (item.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return this.eat(world, pos, state, player);
    }

    public ActionResult eat(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (player.canConsume(false)) {
            // TODO: player stats
            // player.awardStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(2, 0.1f);
            world.emitGameEvent(player, GameEvent.EAT, pos);
            var count = (int) state.get(COUNT);
            if (count > 1) {
                world.setBlockState(pos, state.with(COUNT, count - 1), NOTIFY_ALL);
            } else {
                world.removeBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COUNT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return 2 * state.get(COUNT);
    }
}
