package cn.dancingsnow.xkdeco.client.renderer;

import cn.dancingsnow.xkdeco.blockentity.BlockDisplayBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.world.LightType;

import java.util.Objects;

public class BlockDisplayRenderer implements BlockEntityRenderer<BlockDisplayBlockEntity> {

    private final BlockRenderManager blockRenderer;
    private static final float BLOCK_SCALE = 0.99f;

    public BlockDisplayRenderer(BlockEntityRendererFactory.Context ctx) {
        blockRenderer = MinecraftClient.getInstance().getBlockRenderManager();
    }


    @Override
    public void render(BlockDisplayBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        var state = entity.getCachedState();
        if (state.isAir()) return;

        var pos = entity.getPos();
        var level = Objects.requireNonNull(entity.getWorld());
        var packedLight = LightmapTextureManager.pack(level.getLightLevel(LightType.BLOCK, pos.up()), level.getLightLevel(LightType.SKY, pos.up()));

        matrices.push();

        matrices.scale(BLOCK_SCALE, BLOCK_SCALE, BLOCK_SCALE);
        var delta = (1 - BLOCK_SCALE) / 2;
        matrices.translate(delta, 1, delta);
        blockRenderer.renderBlockAsEntity(state, matrices, vertexConsumers, light, overlay);

        matrices.pop();
    }
}
