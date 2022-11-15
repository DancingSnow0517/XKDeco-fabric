package cn.dancingsnow.xkdeco.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public final class BasicCubeBlock extends Block implements XKDecoBlock.Basic {
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private final ImmutableMap<Direction, VoxelShape> shapes;

    public BasicCubeBlock(Settings settings, Map<Direction, ? extends VoxelShape> shapes) {
        super(settings);
        this.shapes = ImmutableMap.copyOf(shapes);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Objects.requireNonNull(this.shapes.get(state.get(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var directions = ctx.getPlacementDirections();
        var direction = Arrays.stream(directions).filter(Direction.Type.HORIZONTAL).findFirst();

        return this.getDefaultState().with(FACING, direction.orElse(Direction.SOUTH).getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
