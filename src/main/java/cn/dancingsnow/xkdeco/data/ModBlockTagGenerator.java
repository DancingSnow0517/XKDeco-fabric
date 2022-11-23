package cn.dancingsnow.xkdeco.data;

import cn.dancingsnow.xkdeco.setup.ModBlocks;
import cn.dancingsnow.xkdeco.setup.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class ModBlockTagGenerator extends FabricTagProvider<Block> {
    /**
     * Construct a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided. For example @see BlockTagProvider
     *
     * @param dataGenerator The data generator instance
     */
    public ModBlockTagGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Registry.BLOCK);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(ModTags.Blocks.PICKAXE_BLOCKS)
                .add(ModBlocks.BLACK_BRICK_SLAB.getLeft());

        getOrCreateTagBuilder(ModTags.Blocks.STONE_TIRE)
                .add(ModBlocks.BLACK_BRICK_SLAB.getLeft());

        getOrCreateTagBuilder(ModTags.Blocks.DIRT)
                .add(ModBlocks.DIRT_SLAB.getLeft())
                .add(ModBlocks.GRASS_BLOCK_SLAB.getLeft())
                .add(ModBlocks.MYCELIUM_SLAB.getLeft())
                .add(ModBlocks.PODZOL_SLAB.getLeft())
                .add(ModBlocks.CRIMSON_NYLIUM_SLAB.getLeft())
                .add(ModBlocks.WARPED_NYLIUM_SLAB.getLeft())
                .add(ModBlocks.DIRT_COBBLESTONE.getLeft())
                .add(ModBlocks.GRASS_COBBLESTONE.getLeft())
                .add(ModBlocks.DIRT_COBBLESTONE_PATH.getLeft())
                .add(ModBlocks.GRASS_COBBLESTONE_PATH.getLeft())
                .add(ModBlocks.DIRT_COBBLESTONE_SLAB.getLeft())
                .add(ModBlocks.GRASS_COBBLESTONE_SLAB.getLeft())
                .add(ModBlocks.DIRT_COBBLESTONE_PATH_SLAB.getLeft())
                .add(ModBlocks.GRASS_COBBLESTONE_PATH_SLAB.getLeft())
                .add(ModBlocks.DIRT_COBBLESTONE_STAIRS.getLeft())
                .add(ModBlocks.GRASS_COBBLESTONE_STAIRS.getLeft())
                .add(ModBlocks.DIRT_COBBLESTONE_PATH_STAIRS.getLeft())
                .add(ModBlocks.GRASS_COBBLESTONE_PATH_STAIRS.getLeft())
                .add(ModBlocks.PLANTABLE_LEAVES.getLeft())
                .add(ModBlocks.PLANTABLE_LEAVES_DARK.getLeft());

        getOrCreateTagBuilder(ModTags.Blocks.MUSHROOM_GROW_BLOCK)
                .add(ModBlocks.MYCELIUM_SLAB.getLeft())
                .add(ModBlocks.CRIMSON_NYLIUM_SLAB.getLeft())
                .add(ModBlocks.WARPED_NYLIUM_SLAB.getLeft());
    }
}
