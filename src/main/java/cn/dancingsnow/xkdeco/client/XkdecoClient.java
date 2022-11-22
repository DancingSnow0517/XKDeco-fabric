package cn.dancingsnow.xkdeco.client;

import cn.dancingsnow.xkdeco.XKDeco;
import cn.dancingsnow.xkdeco.block.XKDecoBlock;
import cn.dancingsnow.xkdeco.client.renderer.BlockDisplayRenderer;
import cn.dancingsnow.xkdeco.client.renderer.ItemDisplayRenderer;
import cn.dancingsnow.xkdeco.client.renderer.WallRenderer;
import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import cn.dancingsnow.xkdeco.setup.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static cn.dancingsnow.xkdeco.setup.ModBlocks.BIG_TABLE_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.BLOSSOM_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.GLASS_PREFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.GLASS_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.GRASS_PREFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.HOLLOW_PREFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.LEAVES_DARK_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.LEAVES_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.PLANTABLE_PREFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.ROOF_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.STONE_WATER_PREFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.TALL_TABLE_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.TRANSLUCENT_PREFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.WILLOW_PREFIX;

@Environment(EnvType.CLIENT)
public class XkdecoClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockEntityRendererRegistry.register(ModBlockEntities.BLOCK_DISPLAY_BLOCK_ENTITY, BlockDisplayRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, ItemDisplayRenderer::new);
//        BlockEntityRendererRegistry.register(ModBlockEntities.WALL_BLOCK_ENTITY, WallRenderer::new);

        EntityRendererRegistry.register(ModEntities.CUSHION_ENTITY, EmptyEntityRenderer<Entity>::new);

//        setItemColors();
        setBlockColors();
        setCutoutBlocks();

    }

    @SuppressWarnings("DuplicatedCode")
    public static void setItemColors() {
        System.out.println("setItemColors");
        var blockColors = new BlockColors();
//        var blockColors = MinecraftClient.getInstance().getBlockColors();

        var blockItemColor = (ItemColorProvider) (stack, tintIndex) -> {
            var state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(state, null, null, tintIndex);
        };
        var waterItemColor = (ItemColorProvider) (stack, tintIndex) -> 0x3f76e4;

        var ids = Registry.BLOCK.getIds();
        ColorProviderRegistry.ITEM.register(blockItemColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(GRASS_PREFIX))
                .map(Registry.ITEM::get).toArray(Item[]::new));

        ColorProviderRegistry.ITEM.register(blockItemColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(PLANTABLE_PREFIX))
                .map(Registry.ITEM::get).toArray(Item[]::new));

        ColorProviderRegistry.ITEM.register(blockItemColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(WILLOW_PREFIX))
                .map(Registry.ITEM::get).toArray(Item[]::new));

        ColorProviderRegistry.ITEM.register(blockItemColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(LEAVES_DARK_SUFFIX))
                .map(Registry.ITEM::get).toArray(Item[]::new));

        ColorProviderRegistry.ITEM.register(waterItemColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(STONE_WATER_PREFIX))
                .map(Registry.ITEM::get).toArray(Item[]::new));
    }

    @SuppressWarnings("DuplicatedCode")
    public static void setBlockColors() {
        System.out.println("setBlockColors");
        var grassBlockColor = (BlockColorProvider) (state, world, pos, tintIndex) -> {
            if (pos != null && world!=null) {
                return BiomeColors.getGrassColor(world, pos);
            }
            return GrassColors.getColor(0.5, 1.0);
        };
        var leavesBlockColor = (BlockColorProvider) (state, world, pos, tintIndex) -> {
            if (pos != null && world != null) {
                return BiomeColors.getFoliageColor(world, pos);
            }
            return FoliageColors.getDefaultColor();
        };
        var waterBlockColor = (BlockColorProvider) (state, world, pos, tintIndex) -> {
            if (pos != null && world != null) {
                return BiomeColors.getWaterColor(world, pos);
            }
            return 0x3f76e4;
        };

        var ids = Registry.BLOCK.getIds();
        ColorProviderRegistry.BLOCK.register(grassBlockColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(GRASS_PREFIX))
                .map(Registry.BLOCK::get).toArray(Block[]::new));

        ColorProviderRegistry.BLOCK.register(grassBlockColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(PLANTABLE_PREFIX))
                .map(Registry.BLOCK::get).toArray(Block[]::new));

        ColorProviderRegistry.BLOCK.register(leavesBlockColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(WILLOW_PREFIX))
                .map(Registry.BLOCK::get).toArray(Block[]::new));

        ColorProviderRegistry.BLOCK.register(leavesBlockColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(LEAVES_DARK_SUFFIX))
                .map(Registry.BLOCK::get).toArray(Block[]::new));

        ColorProviderRegistry.BLOCK.register(waterBlockColor, ids.stream().filter(id -> XKDeco.MOD_ID.equals(id.getNamespace())).filter(id -> id.getPath().contains(STONE_WATER_PREFIX))
                .map(Registry.BLOCK::get).toArray(Block[]::new));
    }

    public static void setCutoutBlocks() {
        var ids = Registry.BLOCK.getIds();
        for (Identifier identifier : ids) {
            var id = identifier.getPath();
            var block = Registry.BLOCK.get(identifier);
            if ("mechanical_screen".equals(id) || "tech_screen".equals(id)) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            } else if (block instanceof XKDecoBlock.Basic) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            } else if (block instanceof XKDecoBlock.Special) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            } else if (id.contains(GLASS_SUFFIX) || id.contains(TRANSLUCENT_PREFIX)) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            } else if (id.contains(GLASS_PREFIX) || id.contains(HOLLOW_PREFIX) || id.contains(BIG_TABLE_SUFFIX) || id.contains(TALL_TABLE_SUFFIX) || id.contains(ROOF_SUFFIX)) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            } else if (id.contains(GRASS_PREFIX) || id.contains(LEAVES_SUFFIX) || id.contains(BLOSSOM_SUFFIX)) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
            }
        }
    }
}
