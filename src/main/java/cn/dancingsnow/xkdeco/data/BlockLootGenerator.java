package cn.dancingsnow.xkdeco.data;


import cn.dancingsnow.xkdeco.XKDeco;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiConsumer;

import static cn.dancingsnow.xkdeco.setup.ModBlocks.BLOSSOM_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.LEAVES_SUFFIX;
import static cn.dancingsnow.xkdeco.setup.ModBlocks.SLAB_SUFFIX;

public class BlockLootGenerator extends SimpleFabricLootTableProvider {


    public BlockLootGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        var ids = Registry.BLOCK.getIds();
        for (var id : ids) {
            if (id.getNamespace().equals(XKDeco.MOD_ID)) {
                var path = id.getPath();
                if (path.endsWith(SLAB_SUFFIX)) {
                    if (path.contains("glass_tile")) {
                        biConsumer.accept(new Identifier(XKDeco.MOD_ID, "blocks/" + path), ModBlockLootTableGenerator.dropSlabsWithSilkTouch(Registry.BLOCK.get(id)));
                    } else {
                        biConsumer.accept(new Identifier(XKDeco.MOD_ID, "blocks/" + path), BlockLootTableGenerator.slabDrops(Registry.BLOCK.get(id)));
                    }
                } else if (path.contains(LEAVES_SUFFIX) || path.contains(BLOSSOM_SUFFIX)) {
                    biConsumer.accept(new Identifier(XKDeco.MOD_ID, "blocks/" + path), BlockLootTableGenerator.dropsWithShears(Registry.ITEM.get(id)));
                } else if (path.contains("glass_tile")) {
                    biConsumer.accept(new Identifier(XKDeco.MOD_ID, "blocks/" + path), BlockLootTableGenerator.dropsWithSilkTouch(Registry.ITEM.get(id)));
                } else if (path.equals("calligraphy")||path.equals("ink_painting")) {
                    biConsumer.accept(new Identifier(XKDeco.MOD_ID, "blocks/" + path), BlockLootTableGenerator.dropsWithShears(Registry.ITEM.get(id)));
                } else {
                    biConsumer.accept(new Identifier(XKDeco.MOD_ID, "blocks/" + path), BlockLootTableGenerator.drops(Registry.ITEM.get(id)));
                }
            }
        }
    }
}
