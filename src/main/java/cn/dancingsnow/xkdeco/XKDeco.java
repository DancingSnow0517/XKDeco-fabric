package cn.dancingsnow.xkdeco;

import cn.dancingsnow.xkdeco.entity.CushionEntity;
import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import cn.dancingsnow.xkdeco.setup.ModBlocks;
import cn.dancingsnow.xkdeco.setup.ModEntities;
import cn.dancingsnow.xkdeco.setup.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Arrays;

public class XKDeco implements ModInitializer {

    public static final String MOD_ID = "xkdeco";

    public static final ConfiguredFeature<? ,?> OVERWORLD_MAYA_STONE_CONFIGURED_FEATURE = new ConfiguredFeature<>(
            Feature.ORE, new OreFeatureConfig(
                    OreConfiguredFeatures.BASE_STONE_OVERWORLD, ModBlocks.MAYA_STONE.getLeft().getDefaultState(), 64)
    );
    public static final PlacedFeature OVERWORLD_MAYA_STONE_PLACED_FEATURE = new PlacedFeature(
            RegistryEntry.of(OVERWORLD_MAYA_STONE_CONFIGURED_FEATURE),
            Arrays.asList(
                    CountPlacementModifier.of(6),
                    SquarePlacementModifier.of(),
                    HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))
            )
    );

    public static final ConfiguredFeature<? ,?> OVERWORLD_INCA_STONE_CONFIGURED_FEATURE = new ConfiguredFeature<>(
            Feature.ORE, new OreFeatureConfig(
            OreConfiguredFeatures.BASE_STONE_OVERWORLD, ModBlocks.INCA_STONE.getLeft().getDefaultState(), 64)
    );
    public static final PlacedFeature OVERWORLD_INCA_STONE_PLACED_FEATURE = new PlacedFeature(
            RegistryEntry.of(OVERWORLD_INCA_STONE_CONFIGURED_FEATURE),
            Arrays.asList(
                    CountPlacementModifier.of(6),
                    SquarePlacementModifier.of(),
                    HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))
            )
    );



    @Override
    public void onInitialize() {

        ModBlocks.registry();
        ModItems.registry();
        ModBlockEntities.registry();
        ModEntities.registry();
//        ModBlocks.addSpecialWallBlocks();

        addFeature();

        // Use Block Event
        UseBlockCallback.EVENT.register(CushionEntity::onRightClickBlock);

    }

    public static void addFeature() {
        // Maya stone
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new Identifier(MOD_ID, "overworld_maya_ore"), OVERWORLD_MAYA_STONE_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE,
                new Identifier(MOD_ID, "overworld_maya_ore"), OVERWORLD_MAYA_STONE_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID, "overworld_maya_ore")));

        // Inca stone
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new Identifier(MOD_ID, "overworld_inca_ore"), OVERWORLD_INCA_STONE_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE,
                new Identifier(MOD_ID, "overworld_inca_ore"), OVERWORLD_INCA_STONE_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID, "overworld_inca_ore")));
    }
}
