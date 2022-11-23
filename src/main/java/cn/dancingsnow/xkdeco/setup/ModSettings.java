package cn.dancingsnow.xkdeco.setup;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class ModSettings {

    public static final Item.Settings ITEM_BASIC = new FabricItemSettings().group(ModItemGroups.TAB_BASIC);
    public static final Item.Settings ITEM_STRUCTURE = new FabricItemSettings().group(ModItemGroups.TAB_STRUCTURE);
    public static final Item.Settings ITEM_NATURE = new FabricItemSettings().group(ModItemGroups.TAB_NATURE);
    public static final Item.Settings ITEM_FURNITURE = new FabricItemSettings().group(ModItemGroups.TAB_FURNITURE);
    public static final Item.Settings ITEM_FUNCTIONAL = new FabricItemSettings().group(ModItemGroups.TAB_FUNCTIONAL);

    public static final Block.Settings BLOCK_MUD = FabricBlockSettings.of(Material.STONE).strength(1.5f, 3f);
    public static final Block.Settings BLOCK_SANDSTONE = FabricBlockSettings.of(Material.STONE).strength(1.5f, 6f);
    public static final Block.Settings BLOCK_GLASS = FabricBlockSettings.of(Material.GLASS).nonOpaque().allowsSpawning((s, g, p, e) -> false).solidBlock((s, g, p) -> false).suffocates((s, g, p) -> false).blockVision((s, g, p) -> false).strength(0.3f).sounds(BlockSoundGroup.GLASS);
    public static final Block.Settings BLOCK_IRON = FabricBlockSettings.of(Material.METAL).strength(2f, 12f).requiresTool().sounds(BlockSoundGroup.METAL);
    public static final Block.Settings BLOCK_HARD_IRON = FabricBlockSettings.of(Material.METAL).strength(8f, 12f).requiresTool().sounds(BlockSoundGroup.METAL);
    public static final Block.Settings BLOCK_HOLLOW_IRON = FabricBlockSettings.of(Material.METAL).strength(3f, 6f).nonOpaque().requiresTool().sounds(BlockSoundGroup.METAL);
    public static final Block.Settings BLOCK_GOLD = FabricBlockSettings.of(Material.METAL, MapColor.GOLD).strength(3f, 6f).requiresTool().sounds(BlockSoundGroup.METAL);
    public static final Block.Settings BLOCK_COPPER = FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(3f, 6f).requiresTool().sounds(BlockSoundGroup.COPPER);
    public static final Block.Settings BLOCK_BRONZE = FabricBlockSettings.of(Material.METAL, MapColor.TEAL).strength(3f, 12f).requiresTool().sounds(BlockSoundGroup.COPPER);
    public static final Block.Settings BLOCK_WOOD = FabricBlockSettings.of(Material.WOOD).strength(2f, 3f).sounds(BlockSoundGroup.WOOD);
    public static final Block.Settings BLOCK_BRICK = FabricBlockSettings.of(Material.STONE).strength(1.8f, 6f).requiresTool();
    public static final Block.Settings BLOCK_STONE = FabricBlockSettings.of(Material.STONE).strength(1.8f, 9f).requiresTool();
    public static final Block.Settings BLOCK_HARD_STONE = FabricBlockSettings.of(Material.STONE).strength(2f, 10f).requiresTool();
    public static final Block.Settings BLOCK_OBSIDIAN = FabricBlockSettings.of(Material.PISTON, MapColor.BLACK).strength(20f, 20f).requiresTool();
    public static final Block.Settings CRYING_BLOCK_OBSIDIAN = FabricBlockSettings.of(Material.PISTON, MapColor.BLACK).strength(20f, 20f).requiresTool().luminance(s -> 10);
    public static final Block.Settings BLOCK_LIGHT = FabricBlockSettings.of(Material.GLASS).nonOpaque().strength(2f, 10f).luminance(s -> 15).sounds(BlockSoundGroup.GLASS);
    public static final Block.Settings BLOCK_SAND = FabricBlockSettings.of(Material.AGGREGATE).strength(1f, 10f).sounds(BlockSoundGroup.SAND);
    public static final Block.Settings BLOCK_HARD_SAND = FabricBlockSettings.of(Material.AGGREGATE).strength(1f, 12f).sounds(BlockSoundGroup.GRAVEL);
    public static final Block.Settings BLOCK_DIRT = FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5f, 1f).sounds(BlockSoundGroup.GRAVEL);
    public static final Block.Settings BLOCK_NETHER_STONE = FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).strength(0.4f).sounds(BlockSoundGroup.NETHERRACK).requiresTool();
    public static final Block.Settings BLOCK_END_STONE = FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).strength(3f, 9f).requiresTool();
    public static final Block.Settings BLOCK_LEAVES = FabricBlockSettings.of(Material.LEAVES).strength(0.2f).sounds(BlockSoundGroup.GRASS).ticksRandomly().allowsSpawning((s, g, p, e) -> e == EntityType.OCELOT || e == EntityType.PARROT).suffocates((s, w, p) -> false).blockVision((s, w, p) -> false).nonOpaque();
    public static final Block.Settings BLOCK_WOOD_FURNITURE = FabricBlockSettings.of(Material.WOOD).strength(2f, 2.5f).nonOpaque().requiresTool();
    public static final Block.Settings BLOCK_MINIATURE = FabricBlockSettings.of(Material.STONE).strength(0.5f, 0.5f).nonOpaque().requiresTool();
    public static final Block.Settings BLOCK_DESSERT = FabricBlockSettings.of(Material.CAKE).strength(0.5f, 0.5f);
    public static final Block.Settings BLOCK_CARPET = FabricBlockSettings.of(Material.WOOL).strength(0.5f, 0.5f).nonOpaque();
    public static final Block.Settings BLOCK_BOARD = FabricBlockSettings.of(Material.WOOD).strength(0.5f, 0.5f).nonOpaque();
    public static final Block.Settings BLOCK_ROOF = FabricBlockSettings.of(Material.STONE).strength(1.8f, 12f).nonOpaque().noCollision();
    public static final Block.Settings BLOCK_STONE_DISPLAY = FabricBlockSettings.of(Material.METAL).strength(1.5f, 6f).solidBlock((a, b, c) -> false);
    public static final Block.Settings BLOCK_METAL_DISPLAY = FabricBlockSettings.of(Material.METAL).strength(1.5f, 6f).solidBlock((a, b, c) -> false);
    public static final Block.Settings BLOCK_WOOD_WARDROBE = FabricBlockSettings.of(Material.WOOD).strength(1.5f, 6f).nonOpaque();
    public static final Block.Settings BLOCK_METAL_WARDROBE = FabricBlockSettings.of(Material.METAL).strength(1.5f, 6f).nonOpaque();
    public static final Block.Settings BLOCK_GLASS_WARDROBE = FabricBlockSettings.of(Material.GLASS).strength(1.5f, 6f).nonOpaque();
    public static final Block.Settings BLOCK_PORCELAIN = FabricBlockSettings.of(Material.GLASS).strength(0.5f, 0.5f);
    public static final Block.Settings BLOCK_LANTERN = FabricBlockSettings.of(Material.WOOL).strength(0.5f, 0.5f).luminance(s -> 15);
    public static final Block.Settings BLOCK_METAL_LIGHT = FabricBlockSettings.of(Material.METAL).nonOpaque().strength(0.5f, 0.5f).luminance(s -> 15);
    public static final Block.Settings BLOCK_METAL_HALF_LIGHT = FabricBlockSettings.of(Material.METAL).nonOpaque().strength(0.5f, 0.5f).luminance(s -> 7);
    public static final Block.Settings BLOCK_METAL_WITHOUT_LIGHT = FabricBlockSettings.of(Material.METAL).nonOpaque().strength(0.5f, 0.5f);
    public static final Block.Settings BLOCK_STONE_LIGHT = FabricBlockSettings.of(Material.STONE).nonOpaque().strength(0.5f, 0.5f).luminance(s -> 15);
    public static final Block.Settings BLOCK_WOOD_LIGHT = FabricBlockSettings.of(Material.WOOD).nonOpaque().strength(0.5f, 0.5f).luminance(s -> 15);
    public static final Block.Settings BLOCK_STONE_NO_OCCLUSION = FabricBlockSettings.of(Material.STONE).nonOpaque().strength(0.5f, 0.5f);
    public static final Block.Settings BLOCK_GLASS_NO_OCCLUSION = FabricBlockSettings.of(Material.GLASS).nonOpaque().strength(0.5f, 0.5f);
    public static final Block.Settings BLOCK_METAL_NO_OCCLUSION = FabricBlockSettings.of(Material.METAL).nonOpaque().strength(0.5f, 0.5f);
    public static final Block.Settings BLOCK_METAL_NO_COLLISSION = FabricBlockSettings.of(Material.METAL).noCollision().strength(0.5f, 0.5f);
    public static final Block.Settings BLOCK_METAL_LIGHT_NO_COLLISSION = FabricBlockSettings.of(Material.METAL).noCollision().strength(0.5f, 0.5f).luminance(s -> 15);
}
