package cn.dancingsnow.xkdeco.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(LangDataGenerator::new);
        fabricDataGenerator.addProvider(BlockLootGenerator::new);

        fabricDataGenerator.addProvider(ModBlockTagGenerator::new);
    }
}
