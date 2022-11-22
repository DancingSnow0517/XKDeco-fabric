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

public class BlockLootGenerator extends SimpleFabricLootTableProvider {


    public BlockLootGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        var ids = Registry.BLOCK.getIds();
        for (var id : ids) {
            if (id.getNamespace().equals(XKDeco.MOD_ID)) {
                biConsumer.accept(new Identifier(XKDeco.MOD_ID, "blocks/" + id.getPath()), BlockLootTableGenerator.drops(Registry.ITEM.get(id)));
            }
        }
    }
}
