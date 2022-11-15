package cn.dancingsnow.xkdeco.client.renderer;

import cn.dancingsnow.xkdeco.blockentity.ItemDisplayBlockEntity;
import cn.dancingsnow.xkdeco.block.SpecialItemDisplayBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.LightType;

import java.util.Objects;
import java.util.Random;

public class ItemDisplayRenderer implements BlockEntityRenderer<ItemDisplayBlockEntity> {


    private final ItemRenderer itemRenderer;
    private final Random random = new Random();

    public ItemDisplayRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public boolean isInRenderDistance(ItemDisplayBlockEntity blockEntity, Vec3d pos) {
        return blockEntity.isProjector() ? Vec3d.of(blockEntity.getPos()).isInRange(pos,
                MinecraftClient.getInstance().options.getViewDistance().getValue() * 16)
                : BlockEntityRenderer.super.isInRenderDistance(blockEntity, pos);
    }

    @Override
    public void render(ItemDisplayBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // borrowed from ItemEntityRenderer

        var itemstack = entity.getItem();
        if (itemstack.isEmpty()) return;

        var speed = 1;
        var pos = entity.getPos();
        var spin = entity.getSpin();
        if (!entity.getCachedState().get(SpecialItemDisplayBlock.POWERED)) {
            spin += tickDelta / 20;
        }

        this.random.setSeed(itemstack.isEmpty() ? 187 : Item.getRawId(itemstack.getItem()) + itemstack.getDamage());

        matrices.push();
        var bakedmodel = this.itemRenderer.getModel(itemstack, entity.getWorld(), null, speed);
        var gui3d = bakedmodel.hasDepth();
        var amount = this.getRenderAmount(itemstack);
        var modelScale = bakedmodel.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
        matrices.translate(0.5, 1 + 0.1F + 0.25 * modelScale * (entity.isProjector() ? 24 : 1), 0.5);
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(spin));

        if (entity.isProjector()) {
            matrices.scale(16, 16, 16);
        }

        if (!gui3d) {
            matrices.translate(
                    -0.0F * (float) (amount - 1) * 0.5F,
                    -0.0F * (float) (amount - 1) * 0.5F,
                    -0.09375F * (float) (amount - 1) * 0.5F);
        }


        for (var k = 0; k < amount; ++k) {
            matrices.push();
            if (k > 0) {
                if (gui3d) matrices.translate(
                        (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F,
                        (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F,
                        (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F);
                else matrices.translate(
                        (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F,
                        (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F,
                        0.0D);
            }

            var world = Objects.requireNonNull(entity.getWorld());
            var packedLight = LightmapTextureManager.pack(world.getLightLevel(LightType.BLOCK, pos.up()), world.getLightLevel(LightType.SKY, pos.up()));
            this.itemRenderer.renderItem(itemstack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, packedLight, OverlayTexture.DEFAULT_UV, bakedmodel);
            matrices.pop();
            if (!gui3d) {
                matrices.translate(0.0, 0.0, 0.09375F);
            }
        }

        matrices.pop();
    }

    private int getRenderAmount(ItemStack pStack) {
        var i = 1;
        if (pStack.getCount() > 48) {
            i = 5;
        } else if (pStack.getCount() > 32) {
            i = 4;
        } else if (pStack.getCount() > 16) {
            i = 3;
        } else if (pStack.getCount() > 1) {
            i = 2;
        }

        return i;
    }
}
