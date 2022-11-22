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
    }
}
