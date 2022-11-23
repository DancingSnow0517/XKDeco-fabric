package cn.dancingsnow.xkdeco.data;

import cn.dancingsnow.xkdeco.block.IsotropicFallingBlock;
import cn.dancingsnow.xkdeco.block.PlantLeavesBlock;
import cn.dancingsnow.xkdeco.block.PlantLeavesShatterBlock;
import cn.dancingsnow.xkdeco.block.SpecialBlockDisplayBlock;
import cn.dancingsnow.xkdeco.block.SpecialItemDisplayBlock;
import cn.dancingsnow.xkdeco.block.SpecialWardrobeBlock;
import cn.dancingsnow.xkdeco.block.XKDecoBlock;
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

    private static FabricTagBuilder<Block> AXE_BLOCKS_BUILDER;
    private static FabricTagBuilder<Block> PICKAXE_BLOCKS_BUILDER;
    private static FabricTagBuilder<Block> SHOVEL_BLOCKS_BUILDER;
    private static FabricTagBuilder<Block> HOE_BLOCKS_BUILDER;

    private static FabricTagBuilder<Block> STONE_TIRE_BUILDER;
    private static FabricTagBuilder<Block> DIAMOND_TIRE_BUILDER;

    @SuppressWarnings("DuplicateExpressions")
    @Override
    protected void generateTags() {
        AXE_BLOCKS_BUILDER = getOrCreateTagBuilder(ModTags.Blocks.AXE_BLOCKS);
        PICKAXE_BLOCKS_BUILDER = getOrCreateTagBuilder(ModTags.Blocks.PICKAXE_BLOCKS);
        SHOVEL_BLOCKS_BUILDER = getOrCreateTagBuilder(ModTags.Blocks.SHOVEL_BLOCKS);
        HOE_BLOCKS_BUILDER = getOrCreateTagBuilder(ModTags.Blocks.HOE_BLOCKS);

        STONE_TIRE_BUILDER = getOrCreateTagBuilder(ModTags.Blocks.STONE_TIRE);
        DIAMOND_TIRE_BUILDER = getOrCreateTagBuilder(ModTags.Blocks.DIAMOND_TIRE);
        // NATURE
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

        for (var id : Registry.BLOCK.getIds()) {
            if (id.getNamespace().equals("minecraft")) continue;
            var path = id.getPath();
            var block = Registry.BLOCK.get(id);
            if (block instanceof XKDecoBlock.Plant) {
                if (block instanceof PlantLeavesBlock || block instanceof PlantLeavesShatterBlock) {
                    addBlockTool(block, ToolType.HOE);
                } else if (path.equals("dirt_slab") || path.equals("dirt_path_slab") || path.equals("grass_block_slab") || path.equals("mycelium_slab") || path.equals("podzol_slab")) {
                    addBlockTool(block, ToolType.SHOVEL);
                } else {
                    addBlockTool(block, ToolType.PICKAXE);
                }
            } else if (block instanceof XKDecoBlock.Isotropic) {
                if (path.contains("varnished") || path.contains("ebony") || path.contains("mahogany")) {
                    addBlockTool(block, ToolType.AXE);
                } else if (block instanceof IsotropicFallingBlock) {
                    addBlockTool(block, ToolType.SHOVEL);
                } else {
                    addBlockTool(block, ToolType.PICKAXE);
                }
                if (path.startsWith("factory_") && !path.startsWith("factory_lamp_")) {
                    addBlockTire(block, ToolTire.STONE);
                } else if (path.contains("gold_") || path.contains("bronze_") || path.contains("steel_") || path.contains("copper_")) {
                    addBlockTire(block, ToolTire.STONE);
                } else if (path.contains("_obsidian")) {
                    addBlockTire(block, ToolTire.DIAMOND);
                }
            } else if (block instanceof XKDecoBlock.Basic) {
                if (path.contains("varnished") || path.contains("ebony") || path.contains("mahogany")) {
                    addBlockTool(block, ToolType.AXE);
                } else if (path.equals("weiqi_board") || path.equals("xiangqi_board") || path.equals("covered_lamp") || path.equals("telescope") || path.equals("wooden_windmill")) {
                    addBlockTool(block, ToolType.AXE);
                } else if (path.endsWith("_lantern") || path.endsWith("_book_stack") || path.endsWith("globe") || path.endsWith("_model")) {
                    addBlockTool(block, ToolType.AXE);
                } else if (path.equals("calligraphy") || path.equals("ink_painting")) {

                } else {
                    addBlockTool(block, ToolType.PICKAXE);
                }
            } else if (block instanceof XKDecoBlock.Special) {
                if (path.equals("refreshments") || path.equals("fruit_platter")) {

                } else if (block instanceof SpecialBlockDisplayBlock || block instanceof SpecialItemDisplayBlock) {
                    addBlockTool(block, ToolType.PICKAXE);
                } else if (block instanceof SpecialWardrobeBlock) {
                    if (path.contains("varnished") || path.contains("ebony") || path.contains("mahogany")) {
                        addBlockTool(block, ToolType.AXE);
                    } else {
                        addBlockTool(block, ToolType.PICKAXE);
                    }
                }
            }
            addBlockTool(ModBlocks.FACTORY_LIGHT_BAR.getLeft(), ToolType.PICKAXE);
            addBlockTool(ModBlocks.FACTORY_VENT_FAN.getLeft(), ToolType.PICKAXE);
            addBlockTool(ModBlocks.FACTORY_VENT_FAN_BIG.getLeft(), ToolType.PICKAXE);
            addBlockTool(ModBlocks.TECH_CONSOLE.getLeft(), ToolType.PICKAXE);
            addBlockTool(ModBlocks.MECHANICAL_CONSOLE.getLeft(), ToolType.PICKAXE);
        }

    }

    private void addBlockTool(Block block, ToolType type) {
        switch (type) {
            case AXE -> AXE_BLOCKS_BUILDER.add(block);
            case PICKAXE -> PICKAXE_BLOCKS_BUILDER.add(block);
            case SHOVEL -> SHOVEL_BLOCKS_BUILDER.add(block);
            case HOE -> HOE_BLOCKS_BUILDER.add(block);
        }
    }

    private void addBlockTire(Block block, ToolTire tire) {
        switch (tire) {
            case STONE -> STONE_TIRE_BUILDER.add(block);
            case DIAMOND -> DIAMOND_TIRE_BUILDER.add(block);
        }
    }

    private enum ToolType {
        AXE, PICKAXE, SHOVEL, HOE
    }

    private enum ToolTire {
        STONE, IRON, DIAMOND
    }
}
