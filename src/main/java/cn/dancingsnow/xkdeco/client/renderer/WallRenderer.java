package cn.dancingsnow.xkdeco.client.renderer;

import cn.dancingsnow.xkdeco.block.SpecialWallBlock;
import cn.dancingsnow.xkdeco.blockentity.WallBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.random.Random;


public class WallRenderer implements BlockEntityRenderer<WallBlockEntity> {
    private final BlockRenderManager blockRenderer;

    public WallRenderer(BlockEntityRendererFactory.Context context) {
        blockRenderer = context.getRenderManager();
    }

    @Override
    public int getRenderDistance() {
        return 256;
    }

    @Override
    public void render(WallBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var world = entity.getWorld();
        var pos = entity.getPos();
        var state = entity.getCachedState();

        if (state.getBlock() instanceof SpecialWallBlock wall) {
            var wallState = this.withState(state, wall.getWallDelegate(), Properties.UP);
            if (world == null) {
                this.blockRenderer.renderBlockAsEntity(wallState, matrices, vertexConsumers, light, overlay);
            } else {
                var random = Random.create();
                var renderTypes = RenderLayer.getBlockLayers();
                for (var renderType : renderTypes) {

                    this.blockRenderer.renderBlock(wallState, pos, world, matrices,
                            vertexConsumers.getBuffer(renderType), false, random);

                    var eastWall = wall.connectsTo(world.getBlockState(pos.east()));
                    if (eastWall.isPresent()) {
                        var eastWallState = this.withState(state, eastWall.get(), Properties.EAST_WALL_SHAPE);
                        this.blockRenderer.renderBlock(eastWallState, pos, world, matrices,
                                vertexConsumers.getBuffer(renderType), false, random);
                    }

                    var northWall = wall.connectsTo(world.getBlockState(pos.north()));
                    if (northWall.isPresent()) {
                        var northWallState = this.withState(state, northWall.get(), Properties.NORTH_WALL_SHAPE);
                        this.blockRenderer.renderBlock(northWallState, pos, world, matrices,
                                vertexConsumers.getBuffer(renderType), false, random);
                    }

                    var southWall = wall.connectsTo(world.getBlockState(pos.south()));
                    if (southWall.isPresent()) {
                        var southWallState = this.withState(state, southWall.get(), Properties.SOUTH_WALL_SHAPE);
                        this.blockRenderer.renderBlock(southWallState, pos, world, matrices,
                                vertexConsumers.getBuffer(renderType), false, random);

                    }
                    var westWall = wall.connectsTo(world.getBlockState(pos.west()));
                    if (westWall.isPresent()) {
                        var westWallState = this.withState(state, westWall.get(), Properties.WEST_WALL_SHAPE);
                        this.blockRenderer.renderBlock(westWallState, pos, world, matrices,
                                vertexConsumers.getBuffer(renderType), false, random);
                    }

                }
            }
        }

    }

    private <T extends Comparable<T>> BlockState withState(BlockState source, Block target, Property<T> property) {
        var state = target.getDefaultState();
        if (state.contains(Properties.UP)) {
            state = state.with(Properties.UP, false);
        }
        if (state.contains(property)) {
            state = state.with(property, source.get(property));
        }
        return state;
    }
}
