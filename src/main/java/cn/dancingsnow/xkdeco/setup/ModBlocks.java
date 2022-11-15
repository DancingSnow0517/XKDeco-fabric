package cn.dancingsnow.xkdeco.setup;

import cn.dancingsnow.xkdeco.XKDeco;
import cn.dancingsnow.xkdeco.block.BasicBlock;
import cn.dancingsnow.xkdeco.block.BasicCubeBlock;
import cn.dancingsnow.xkdeco.block.BasicFullDirectionBlock;
import cn.dancingsnow.xkdeco.block.IsotropicCubeBlock;
import cn.dancingsnow.xkdeco.block.IsotropicHollowBlock;
import cn.dancingsnow.xkdeco.block.IsotropicPillarBlock;
import cn.dancingsnow.xkdeco.block.IsotropicRoofBlock;
import cn.dancingsnow.xkdeco.block.IsotropicRoofEaveBlock;
import cn.dancingsnow.xkdeco.block.IsotropicRoofFlatBlock;
import cn.dancingsnow.xkdeco.block.IsotropicSlabBlock;
import cn.dancingsnow.xkdeco.block.IsotropicStairBlock;
import cn.dancingsnow.xkdeco.block.PlantLeavesBlock;
import cn.dancingsnow.xkdeco.block.PlantLeavesShatterBlock;
import cn.dancingsnow.xkdeco.block.PlantSlabBlock;
import cn.dancingsnow.xkdeco.block.SpecialBlockDisplayBlock;
import cn.dancingsnow.xkdeco.block.SpecialConsole;
import cn.dancingsnow.xkdeco.block.SpecialCupBlock;
import cn.dancingsnow.xkdeco.block.SpecialDessertBlock;
import cn.dancingsnow.xkdeco.block.SpecialItemDisplayBlock;
import cn.dancingsnow.xkdeco.block.SpecialLightBar;
import cn.dancingsnow.xkdeco.block.SpecialRoofRidgeBlock;
import cn.dancingsnow.xkdeco.block.SpecialWardrobeBlock;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Arrays;
import java.util.Objects;

import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_BOARD;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_BRICK;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_BRONZE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_CARPET;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_COPPER;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_DESSERT;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_DIRT;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_END_STONE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_GLASS;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_GLASS_NO_OCCLUSION;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_GLASS_WARDROBE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_GOLD;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_HARD_IRON;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_HARD_SAND;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_HARD_STONE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_HOLLOW_IRON;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_IRON;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_LANTERN;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_LEAVES;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_LIGHT;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_METAL_DISPLAY;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_METAL_HALF_LIGHT;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_METAL_LIGHT;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_METAL_LIGHT_NO_COLLISSION;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_METAL_NO_COLLISSION;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_METAL_NO_OCCLUSION;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_METAL_WARDROBE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_METAL_WITHOUT_LIGHT;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_MINIATURE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_MUD;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_NETHER_STONE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_OBSIDIAN;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_PORCELAIN;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_ROOF;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_SAND;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_SANDSTONE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_STONE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_STONE_DISPLAY;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_STONE_LIGHT;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_STONE_NO_OCCLUSION;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_WOOD;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_WOOD_FURNITURE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_WOOD_LIGHT;
import static cn.dancingsnow.xkdeco.setup.ModSettings.BLOCK_WOOD_WARDROBE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.ITEM_BASIC;
import static cn.dancingsnow.xkdeco.setup.ModSettings.ITEM_FUNCTIONAL;
import static cn.dancingsnow.xkdeco.setup.ModSettings.ITEM_FURNITURE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.ITEM_NATURE;
import static cn.dancingsnow.xkdeco.setup.ModSettings.ITEM_STRUCTURE;

public class ModBlocks {
    // Fixes
    public static final String GRASS_PREFIX = "grass_";
    public static final String GLASS_PREFIX = "glass_";
    public static final String LINED_PREFIX = "lined_";
    public static final String WILLOW_PREFIX = "willow_";
    public static final String HOLLOW_PREFIX = "hollow_";
    public static final String LUXURY_PREFIX = "luxury_";
    public static final String PAINTED_PREFIX = "painted_";
    public static final String CHISELED_PREFIX = "chiseled_";
    public static final String PLANTABLE_PREFIX = "plantable_";
    public static final String TRANSLUCENT_PREFIX = "translucent_";
    public static final String DOUBLE_SCREW_PREFIX = "double_screw_";
    public static final String SPECIAL_WALL_PREFIX = "special_wall_";
    public static final String STONE_WATER_PREFIX = "stone_water_";

    public static final String LOG_SUFFIX = "_log";
    public static final String WOOD_SUFFIX = "_wood";
    public static final String SLAB_SUFFIX = "_slab";
    public static final String PATH_SUFFIX = "_path";
    public static final String ROOF_SUFFIX = "_roof";
    public static final String ROOF_EAVE_SUFFIX = "_roof_eave";
    public static final String ROOF_FLAT_SUFFIX = "_roof_flat";
    public static final String STOOL_SUFFIX = "_stool";
    public static final String CHAIR_SUFFIX = "_chair";
    public static final String TABLE_SUFFIX = "_table";
    public static final String GLASS_SUFFIX = "_glass";
    public static final String STAIRS_SUFFIX = "_stairs";
    public static final String PILLAR_SUFFIX = "_pillar";
    public static final String LEAVES_SUFFIX = "_leaves";
    public static final String BLOSSOM_SUFFIX = "_blossom";
    public static final String BIG_TABLE_SUFFIX = "_big_table";
    public static final String TALL_TABLE_SUFFIX = "_tall_table";
    public static final String LEAVES_DARK_SUFFIX = "_leaves_dark";
    public static final String ITEM_DISPLAY_SUFFIX = "_item_display";
    public static final String BLOCK_DISPLAY_SUFFIX = "_block_display";
    public static final String WARDROBE_SUFFIX = "_wardrobe";
    public static final String SHATTER_SUFFIX = "_shatter";
    public static final String CONSOLE_SUFFIX = "_console";
    public static final String VENT_FAN_SUFFIX = "_vent_fan";

    public static final String CUP_SPECIAL = "cup";
    public static final String REFRESHMENT_SPECIAL = "refreshments";
    public static final String FRUIT_PLATTER_SPECIAL = "fruit_platter";
    public static final String ITEM_PROJECTOR_SPECIAL = "item_projector";

    private static Pair<Block, Item> addBasic(String id, ShapeFunction shapeFunction, boolean isSupportNeeded, AbstractBlock.Settings settings, Item.Settings itemSettings) {
        Identifier identifier = new Identifier(XKDeco.MOD_ID, id);
        var horizontalShapes = Maps.toMap(Direction.Type.HORIZONTAL, shapeFunction::getShape);
        if (!isSupportNeeded && horizontalShapes.values().stream().anyMatch(s -> Block
                .isFaceFullSquare(VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), s, BooleanBiFunction.ONLY_FIRST), Direction.DOWN))) {
            var shapes = Maps.toMap(Arrays.stream(Direction.values()).toList(), shapeFunction::getShape);
            var block = new BasicFullDirectionBlock(settings, shapes);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (horizontalShapes.values().stream().allMatch(Block::isShapeFullCube)) {
            var block = new BasicCubeBlock(settings, horizontalShapes);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else {
            var block = new BasicBlock(settings, isSupportNeeded, horizontalShapes);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        }
    }

    private static Pair<Block, Item> addIsotropic(String id, AbstractBlock.Settings settings, Item.Settings itemSettings) {
        Identifier identifier = new Identifier(XKDeco.MOD_ID, id);
        var isGlass = id.contains(GLASS_SUFFIX) || id.contains(TRANSLUCENT_PREFIX) || id.contains(GLASS_PREFIX);
        if (id.contains(SLAB_SUFFIX)) {
            var block = new IsotropicSlabBlock(settings, isGlass);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(STAIRS_SUFFIX)) {
            var block = new IsotropicStairBlock(settings, isGlass);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(LOG_SUFFIX) || id.contains(WOOD_SUFFIX) || id.contains(PILLAR_SUFFIX)) {
            var block = new IsotropicPillarBlock(settings, isGlass);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(LINED_PREFIX) || id.contains(LUXURY_PREFIX) || id.contains(PAINTED_PREFIX)
                || id.contains(CHISELED_PREFIX) || id.contains(DOUBLE_SCREW_PREFIX)) {
            var block = new IsotropicPillarBlock(settings, isGlass);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(BIG_TABLE_SUFFIX) || id.contains(TALL_TABLE_SUFFIX)) {
            var block = new IsotropicHollowBlock(settings, IsotropicHollowBlock.BIG_TABLE_SHAPE);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(TABLE_SUFFIX)) {
            var block = new IsotropicHollowBlock(settings, IsotropicHollowBlock.TABLE_SHAPE);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(HOLLOW_PREFIX)) {
            var block = new IsotropicHollowBlock(settings, VoxelShapes.fullCube());
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(ROOF_FLAT_SUFFIX)) {
            var block = new IsotropicRoofFlatBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(ROOF_EAVE_SUFFIX)) {
            var block = new IsotropicRoofEaveBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(ROOF_SUFFIX)) {
            var block = new IsotropicRoofBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else {
            var block = new IsotropicCubeBlock(settings, isGlass);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        }
    }

    private static Pair<Block, Item> addPlant(String id, AbstractBlock.Settings settings, Item.Settings itemSettings) {
        Identifier identifier = new Identifier(XKDeco.MOD_ID, id);
        var isPath = id.contains(PATH_SUFFIX);
        if (id.contains(LEAVES_SUFFIX) || id.contains(BLOSSOM_SUFFIX)) {
            if (id.endsWith(SHATTER_SUFFIX)) {
                var block = new PlantLeavesShatterBlock(settings);
                return createBlockItemAndRegistry(block, itemSettings, identifier);
            }
            var block = new PlantLeavesBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(SLAB_SUFFIX)) {
            // TODO: Dirt Slab
            var block = new PlantSlabBlock(settings, isPath, new Identifier(XKDeco.MOD_ID, "dirt_slab"));
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else {
            throw new IllegalArgumentException("Illegal id (" + id + ") for plant blocks");
        }
    }

    private static Pair<Block, Item> addSpecial(String id, AbstractBlock.Settings settings, Item.Settings itemSettings) {
        Identifier identifier = new Identifier(XKDeco.MOD_ID, id);
        if (id.equals(CUP_SPECIAL)) {
            var block = new SpecialCupBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.equals(REFRESHMENT_SPECIAL) || id.equals(FRUIT_PLATTER_SPECIAL)) {
            var block = new SpecialDessertBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(ITEM_DISPLAY_SUFFIX) || id.equals(ITEM_PROJECTOR_SPECIAL)) {
            var block = new SpecialItemDisplayBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(BLOCK_DISPLAY_SUFFIX)) {
            var block = new SpecialBlockDisplayBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(WARDROBE_SUFFIX)) {
            var block = new SpecialWardrobeBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(ROOF_SUFFIX)) {
            var block = new SpecialRoofRidgeBlock(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (Objects.equals(id, "factory_light_bar")) {
            var block = new SpecialLightBar(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(VENT_FAN_SUFFIX)) {
            var shapes = Maps.toMap(Arrays.stream(Direction.values()).toList(), d -> ShapeFunction.fromVentFan().getShape(d));
            var block = new BasicFullDirectionBlock(settings, shapes);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else if (id.contains(CONSOLE_SUFFIX)) {
            var block = new SpecialConsole(settings);
            return createBlockItemAndRegistry(block, itemSettings, identifier);
        } else {
            throw new IllegalArgumentException("Illegal id (" + id + ") for special blocks");
        }
    }

//    public static void addSpecialWallBlocks() {
//        var ids = Registry.BLOCK.getIds();
//        for (var id : ids) {
//            if (!id.getNamespace().equals("minecraft")) {
//                continue;
//            }
//            var block = Registry.BLOCK.get(id);
//            if (block instanceof WallBlock wall) {
//                var specialBlock = new SpecialWallBlock(wall);
//                var name = SPECIAL_WALL_PREFIX + id.toString().replace(':', '_');
//                createBlockItemAndRegistry(specialBlock, ITEM_STRUCTURE, new Identifier(XKDeco.MOD_ID, name));
//            }
//        }
//    }

    private static Pair<Block, Item> createBlockItemAndRegistry(Block block, Item.Settings settings, Identifier identifier) {
        BlockItem blockItem = new BlockItem(block, settings);
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, blockItem);
        return new Pair<>(block, blockItem);
    }

    private interface ShapeFunction {
        VoxelShape getShape(Direction direction);

        static ShapeFunction fromBigTable() {
            return d -> Direction.Type.HORIZONTAL.test(d) ? IsotropicHollowBlock.BIG_TABLE_SHAPE : VoxelShapes.fullCube();
        }

        static ShapeFunction fromLongStool() {
            return d -> switch (d) {
                case EAST, WEST -> Block.createCuboidShape(3, 0, 0, 13, 10, 16);
                case NORTH, SOUTH -> Block.createCuboidShape(0, 0, 3, 16, 10, 13);
                default -> VoxelShapes.fullCube();
            };
        }

        static ShapeFunction fromChair() {
            return d -> switch (d) {
                case EAST ->
                        VoxelShapes.union(Block.createCuboidShape(2, 0, 2, 14, 10, 14), Block.createCuboidShape(2, 10, 2, 4, 16, 14));
                case SOUTH ->
                        VoxelShapes.union(Block.createCuboidShape(2, 0, 2, 14, 10, 14), Block.createCuboidShape(2, 10, 2, 14, 16, 4));
                case WEST ->
                        VoxelShapes.union(Block.createCuboidShape(2, 0, 2, 14, 10, 14), Block.createCuboidShape(12, 10, 2, 14, 16, 14));
                case NORTH ->
                        VoxelShapes.union(Block.createCuboidShape(2, 0, 2, 14, 10, 14), Block.createCuboidShape(2, 10, 12, 14, 16, 14));
                default -> VoxelShapes.fullCube();
            };
        }

        static ShapeFunction fromShelf() {
            return d -> switch (d) {
                case EAST, WEST -> VoxelShapes.union(
                        Block.createCuboidShape(0, 0, 0, 16, 1, 16), Block.createCuboidShape(0, 15, 0, 16, 16, 16),
                        Block.createCuboidShape(0, 1, 0, 16, 15, 1), Block.createCuboidShape(0, 1, 15, 16, 15, 16));
                case NORTH, SOUTH -> VoxelShapes.union(
                        Block.createCuboidShape(15, 0, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 1, 16, 16),
                        Block.createCuboidShape(1, 15, 0, 15, 16, 16), Block.createCuboidShape(1, 0, 0, 15, 1, 16));
                default -> VoxelShapes.fullCube();
            };
        }

        static ShapeFunction fromMiniature() {
            return d -> switch (d) {
                case EAST, WEST -> Block.createCuboidShape(3, 0, 0, 13, 6, 16);
                case NORTH, SOUTH -> Block.createCuboidShape(0, 0, 3, 16, 6, 13);
                default -> VoxelShapes.fullCube();
            };
        }

        static ShapeFunction fromTeapot() {
            return d -> Direction.Type.HORIZONTAL.test(d) ? Block.createCuboidShape(4, 0, 4, 12, 6, 12) : VoxelShapes.fullCube();
        }

        static ShapeFunction fromTeaWare() {
            return d -> switch (d) {
                case EAST, WEST -> Block.createCuboidShape(3, 0, 0, 13, 2, 16);
                case NORTH, SOUTH -> Block.createCuboidShape(0, 0, 3, 16, 2, 13);
                default -> VoxelShapes.fullCube();
            };
        }

        static ShapeFunction fromCarpet() {
            return d -> Direction.Type.HORIZONTAL.test(d) ? Block.createCuboidShape(0, 0, 0, 16, 1, 16) : VoxelShapes.fullCube();
        }

        static ShapeFunction fromBoard() {
            return d -> Direction.Type.HORIZONTAL.test(d) ? Block.createCuboidShape(1, 0, 1, 15, 1, 15) : VoxelShapes.fullCube();
        }

        static ShapeFunction fromPorcelain() {
            return d -> Block.createCuboidShape(2, 0, 2, 14, 16, 14);
        }

        static ShapeFunction fromPorcelainSmall() {
            return d -> Block.createCuboidShape(5, 0, 5, 11, 12, 11);
        }

        static ShapeFunction fromLantern() {
            return d -> VoxelShapes.union(Block.createCuboidShape(2, 2, 2, 14, 14, 14),
                    Block.createCuboidShape(5, 0, 5, 11, 16, 11));
        }

        static ShapeFunction fromFestivalLantern() {
            return d -> VoxelShapes.union(Block.createCuboidShape(2, 2, 2, 14, 14, 14),
                    Block.createCuboidShape(5, 0, 5, 11, 16, 11),
                    Block.createCuboidShape(0, 3, 0, 16, 13, 16));
        }

        static ShapeFunction fromCandlestick() {
            return d -> Block.createCuboidShape(5, 0, 5, 11, 13, 11);
        }

        static ShapeFunction fromBigCandlestick() {
            return d -> Block.createCuboidShape(2, 0, 2, 14, 14, 14);
        }

        static ShapeFunction fromCoveredLamp() {
            return d -> Block.createCuboidShape(4, 0, 4, 12, 16, 12);
        }

        static ShapeFunction fromStoneLamp() {
            return d -> Block.createCuboidShape(3, 0, 3, 13, 16, 13);
        }

        static ShapeFunction fromWaterBowl() {
            return d -> Block.createCuboidShape(0, 0, 0, 16, 5, 16);
        }

        static ShapeFunction fromFishBowl() {
            return d -> Block.createCuboidShape(1, 0, 1, 15, 6, 15);
        }

        static ShapeFunction fromFishTank() {
            return d -> VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), Block.createCuboidShape(1, 1, 1, 15, 16, 15), BooleanBiFunction.ONLY_FIRST);
        }

        static ShapeFunction fromWaterTank() {
            return d -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(1, 0, 1, 15, 16, 15), Block.createCuboidShape(3, 3, 3, 13, 16, 13), BooleanBiFunction.ONLY_FIRST);
        }

        static ShapeFunction fromOilLamp() {
            return d -> switch (d) {
                case SOUTH -> Block.createCuboidShape(5, 4, 0, 11, 12, 8);
                case EAST -> Block.createCuboidShape(0, 4, 5, 8, 12, 11);
                case NORTH -> Block.createCuboidShape(5, 4, 8, 11, 12, 16);
                case WEST -> Block.createCuboidShape(8, 4, 5, 16, 12, 11);
                case DOWN -> Block.createCuboidShape(5, 5, 5, 11, 16, 11);
                case UP -> Block.createCuboidShape(5, 0, 5, 11, 8, 11);
            };
        }

        static ShapeFunction fromEmptyCandlestick() {
            return d -> switch (d) {
                case SOUTH -> Block.createCuboidShape(5, 5, 0, 11, 16, 11);
                case EAST -> Block.createCuboidShape(0, 5, 5, 11, 16, 11);
                case NORTH -> Block.createCuboidShape(5, 5, 5, 11, 16, 16);
                case WEST -> Block.createCuboidShape(5, 5, 5, 16, 16, 11);
                default -> Block.createCuboidShape(5, 0, 5, 11, 16, 11);
            };
        }

        static ShapeFunction fromFactoryLamp() {
            return d -> switch (d) {
                case SOUTH -> Block.createCuboidShape(4, 4, 0, 12, 12, 8);
                case EAST -> Block.createCuboidShape(0, 4, 4, 8, 12, 12);
                case NORTH -> Block.createCuboidShape(4, 4, 8, 12, 12, 16);
                case WEST -> Block.createCuboidShape(8, 4, 4, 16, 12, 12);
                case DOWN -> Block.createCuboidShape(4, 8, 4, 12, 16, 12);
                case UP -> Block.createCuboidShape(4, 0, 4, 12, 8, 12);
            };
        }

        static ShapeFunction fromFan() {
            return d -> switch (d) {
                case EAST -> Block.createCuboidShape(1, 0, 1, 6, 15, 15);
                case SOUTH -> Block.createCuboidShape(1, 0, 1, 15, 15, 6);
                case WEST -> Block.createCuboidShape(10, 1, 1, 16, 15, 15);
                case NORTH -> Block.createCuboidShape(1, 1, 10, 15, 15, 16);
                case UP -> Block.createCuboidShape(1, 0, 1, 15, 6, 15);
                case DOWN -> Block.createCuboidShape(1, 10, 1, 15, 16, 15);
            };
        }

        static ShapeFunction fromScreen() {
            return d -> switch (d) {
                case SOUTH -> Block.createCuboidShape(0, 0, 0, 16, 16, 2);
                case WEST -> Block.createCuboidShape(14, 0, 0, 16, 16, 16);
                case NORTH -> Block.createCuboidShape(0, 0, 14, 16, 16, 16);
                default -> Block.createCuboidShape(0, 0, 0, 2, 16, 16);
            };
        }

        static ShapeFunction fromScreen2() {
            return d -> switch (d) {
                case SOUTH -> Block.createCuboidShape(0, 0, 2, 16, 16, 3);
                case WEST -> Block.createCuboidShape(13, 0, 0, 14, 16, 16);
                case NORTH -> Block.createCuboidShape(0, 0, 13, 16, 16, 14);
                default -> Block.createCuboidShape(2, 0, 0, 3, 16, 16);
            };
        }

        static ShapeFunction fromVentFan() {
            return d -> switch (d) {
                case SOUTH, NORTH -> Block.createCuboidShape(0, 0, 2, 16, 16, 14);
                case EAST, WEST -> Block.createCuboidShape(2, 0, 0, 14, 16, 16);
                default -> Block.createCuboidShape(0, 2, 0, 16, 14, 16);
            };
        }

        static ShapeFunction fromTechTable() {
            return d -> VoxelShapes.union(Block.createCuboidShape(2, 0, 2, 14, 10, 14),
                    Block.createCuboidShape(0, 10, 0, 16, 16, 16));
        }

        static ShapeFunction fromHologramBase() {
            return d -> switch (d) {
                case EAST -> Block.createCuboidShape(0, 1, 1, 2, 15, 15);
                case SOUTH -> Block.createCuboidShape(1, 1, 0, 15, 15, 2);
                case WEST -> Block.createCuboidShape(14, 1, 1, 16, 15, 15);
                case NORTH -> Block.createCuboidShape(1, 1, 14, 15, 15, 16);
                case UP -> Block.createCuboidShape(1, 0, 1, 15, 2, 15);
                case DOWN -> Block.createCuboidShape(1, 14, 1, 15, 16, 15);
            };
        }
    }

    // Blocks and BlockItems

    // Basic
    public static final Pair<Block, Item> MAYA_SINGLE_SCREW_THREAD_STONE = addBasic("maya_single_screw_thread_stone", s -> VoxelShapes.fullCube(), false, BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> SCREW_THREAD_BRONZE_BLOCK = addBasic("screw_thread_bronze_block", s -> VoxelShapes.fullCube(), false, BLOCK_BRONZE, ITEM_BASIC);

    public static final Pair<Block, Item> VARNISHED_DESK = addBasic("varnished_desk", ShapeFunction.fromBigTable(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> VARNISHED_STOOL = addBasic("varnished_stool", ShapeFunction.fromLongStool(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> VARNISHED_CHAIR = addBasic("varnished_chair", ShapeFunction.fromChair(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> VARNISHED_EMPTY_SHELF = addBasic("varnished_empty_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> VARNISHED_SHELF = addBasic("varnished_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> VARNISHED_DIVIDED_SHELF = addBasic("varnished_divided_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);

    public static final Pair<Block, Item> EBONY_DESK = addBasic("ebony_desk", ShapeFunction.fromBigTable(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> EBONY_STOOL = addBasic("ebony_stool", ShapeFunction.fromLongStool(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> EBONY_CHAIR = addBasic("ebony_chair", ShapeFunction.fromChair(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> EBONY_EMPTY_SHELF = addBasic("ebony_empty_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> EBONY_SHELF = addBasic("ebony_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> EBONY_DIVIDED_SHELF = addBasic("ebony_divided_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);

    public static final Pair<Block, Item> MAHOGANY_DESK = addBasic("mahogany_desk", ShapeFunction.fromBigTable(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MAHOGANY_STOOL = addBasic("mahogany_stool", ShapeFunction.fromLongStool(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MAHOGANY_CHAIR = addBasic("mahogany_chair", ShapeFunction.fromChair(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MAHOGANY_EMPTY_SHELF = addBasic("mahogany_empty_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MAHOGANY_SHELF = addBasic("mahogany_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MAHOGANY_DIVIDED_SHELF = addBasic("mahogany_divided_shelf", ShapeFunction.fromShelf(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);

    public static final Pair<Block, Item> MINIATURE_TREE = addBasic("miniature_tree", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MINIATURE_CHERRY = addBasic("miniature_cherry", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MINIATURE_GINKGO = addBasic("miniature_ginkgo", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MINIATURE_MAPLE = addBasic("miniature_maple", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MINIATURE_BAMBOO = addBasic("miniature_bamboo", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MINIATURE_CORAL = addBasic("miniature_coral", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MINIATURE_RED_CORAL = addBasic("miniature_red_coral", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MINIATURE_MOUNT = addBasic("miniature_mount", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MINIATURE_SUCCULENTS = addBasic("miniature_succulents", ShapeFunction.fromMiniature(), false, BLOCK_MINIATURE, ITEM_FURNITURE);

    public static final Pair<Block, Item> TEAPOT = addBasic("teapot", ShapeFunction.fromTeapot(), true, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> TEA_WARE = addBasic("tea_ware", ShapeFunction.fromTeaWare(), true, BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> CALLIGRAPHY = addBasic("calligraphy", ShapeFunction.fromCarpet(), true, BLOCK_CARPET, ITEM_FURNITURE);
    public static final Pair<Block, Item> INK_PAINTING = addBasic("ink_painting", ShapeFunction.fromCarpet(), true, BLOCK_CARPET, ITEM_FURNITURE);
    public static final Pair<Block, Item> WEIQI_BOARD = addBasic("weiqi_board", ShapeFunction.fromBoard(), true, BLOCK_BOARD, ITEM_FURNITURE);
    public static final Pair<Block, Item> XIANGQI_BOARD = addBasic("xiangqi_board", ShapeFunction.fromBoard(), true, BLOCK_BOARD, ITEM_FURNITURE);

    public static final Pair<Block, Item> WHITE_PORCELAIN = addBasic("white_porcelain", ShapeFunction.fromPorcelain(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);
    public static final Pair<Block, Item> WHITE_PORCELAIN_TALL = addBasic("white_porcelain_tall", ShapeFunction.fromPorcelain(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);
    public static final Pair<Block, Item> WHITE_PORCELAIN_SMALL = addBasic("white_porcelain_small", ShapeFunction.fromPorcelainSmall(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);
    public static final Pair<Block, Item> BLUEWHITE_PORCELAIN = addBasic("bluewhite_porcelain", ShapeFunction.fromPorcelain(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);
    public static final Pair<Block, Item> BLUEWHITE_PORCELAIN_TALL = addBasic("bluewhite_porcelain_tall", ShapeFunction.fromPorcelain(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);
    public static final Pair<Block, Item> BLUEWHITE_PORCELAIN_SMALL = addBasic("bluewhite_porcelain_small", ShapeFunction.fromPorcelainSmall(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);
    public static final Pair<Block, Item> CELADON_PORCELAIN = addBasic("celadon_porcelain", ShapeFunction.fromPorcelain(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);
    public static final Pair<Block, Item> CELADON_PORCELAIN_TALL = addBasic("celadon_porcelain_tall", ShapeFunction.fromPorcelain(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);
    public static final Pair<Block, Item> CELADON_PORCELAIN_SMALL = addBasic("celadon_porcelain_small", ShapeFunction.fromPorcelainSmall(), false, BLOCK_PORCELAIN, ITEM_FURNITURE);

    public static final Pair<Block, Item> PAPER_LANTERN = addBasic("paper_lantern", ShapeFunction.fromLantern(), false, BLOCK_LANTERN, ITEM_FURNITURE);
    public static final Pair<Block, Item> RED_LANTERN = addBasic("red_lantern", ShapeFunction.fromLantern(), false, BLOCK_LANTERN, ITEM_FURNITURE);
    public static final Pair<Block, Item> FESTIVAL_LANTERN = addBasic("festival_lantern", ShapeFunction.fromFestivalLantern(), false, BLOCK_LANTERN, ITEM_FURNITURE);
    public static final Pair<Block, Item> OIL_LAMP = addBasic("oil_lamp", ShapeFunction.fromOilLamp(), false, BLOCK_METAL_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> CANDLESTICK = addBasic("candlestick", ShapeFunction.fromCandlestick(), false, BLOCK_METAL_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> BIG_CANDLESTICK = addBasic("big_candlestick", ShapeFunction.fromBigCandlestick(), false, BLOCK_METAL_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> EMPTY_CANDLESTICK = addBasic("empty_candlestick", ShapeFunction.fromEmptyCandlestick(), false, BLOCK_METAL_WITHOUT_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> COVERED_LAMP = addBasic("covered_lamp", ShapeFunction.fromCoveredLamp(), false, BLOCK_WOOD_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> ROOFED_LAMP = addBasic("roofed_lamp", ShapeFunction.fromBigCandlestick(), false, BLOCK_STONE_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> STONE_LAMP = addBasic("stone_lamp", ShapeFunction.fromStoneLamp(), false, BLOCK_STONE_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> DEEPSLATE_LAMP = addBasic("deepslate_lamp", ShapeFunction.fromStoneLamp(), false, BLOCK_STONE_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> BLACKSTONE_LAMP = addBasic("blackstone_lamp", ShapeFunction.fromStoneLamp(), false, BLOCK_STONE_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> FISH_BOWL = addBasic("fish_bowl", ShapeFunction.fromFishBowl(), false, BLOCK_STONE_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> DARK_FISH_BOWL = addBasic("dark_fish_bowl", ShapeFunction.fromFishBowl(), false, BLOCK_STONE_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> STONE_WATER_BOWL = addBasic("stone_water_bowl", ShapeFunction.fromWaterBowl(), false, BLOCK_STONE_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> STONE_WATER_TANK = addBasic("stone_water_tank", ShapeFunction.fromWaterTank(), false, BLOCK_STONE_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> FISH_TANK = addBasic("fish_tank", ShapeFunction.fromFishTank(), false, BLOCK_GLASS_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> EMPTY_FISH_TANK = addBasic("empty_fish_tank", ShapeFunction.fromFishTank(), false, BLOCK_GLASS_NO_OCCLUSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> SMALL_BOOK_STACK = addBasic("small_book_stack", s -> Block.createCuboidShape(2, 0, 2, 14, 8, 14), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> BIG_BOOK_STACK = addBasic("big_book_stack", s -> Block.createCuboidShape(0, 0, 0, 16, 10, 16), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> EMPTY_BOTTLE_STACK = addBasic("empty_bottle_stack", s -> Block.createCuboidShape(2, 0, 2, 14, 8, 14), false, BLOCK_GLASS_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> BOTTLE_STACK = addBasic("bottle_stack", s -> Block.createCuboidShape(2, 0, 2, 14, 8, 14), false, BLOCK_GLASS_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> WOOD_GLOBE = addBasic("wood_globe", ShapeFunction.fromCoveredLamp(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> GLOBE = addBasic("globe", ShapeFunction.fromCoveredLamp(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> SOLAR_SYSTEM_MODEL = addBasic("solar_system_model", s -> Block.createCuboidShape(0, 0, 0, 16, 10, 16), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> BIG_SOLAR_SYSTEM_MODEL = addBasic("big_solar_system_model", s -> VoxelShapes.fullCube(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> TELESCOPE = addBasic("telescope", s -> VoxelShapes.fullCube(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);

    public static final Pair<Block, Item> FACTORY_LAMP = addBasic("factory_lamp", ShapeFunction.fromFactoryLamp(), false, BLOCK_METAL_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> FACTORY_LAMP_BROKEN = addBasic("factory_lamp_broken", ShapeFunction.fromFactoryLamp(), false, BLOCK_METAL_WITHOUT_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> FACTORY_WARNING_LAMP = addBasic("factory_warning_lamp", ShapeFunction.fromFactoryLamp(), false, BLOCK_METAL_HALF_LIGHT, ITEM_FURNITURE);

    public static final Pair<Block, Item> FACTORY_CEILING_LAMP = addBasic("factory_ceiling_lamp", s -> Block.createCuboidShape(0, 12, 0, 16, 16, 16), false, BLOCK_METAL_LIGHT, ITEM_FURNITURE);
    public static final Pair<Block, Item> FACTORY_PENDANT = addBasic("factory_pendant", s -> Block.createCuboidShape(2, 4, 2, 14, 16, 14), false, BLOCK_METAL_LIGHT, ITEM_FURNITURE);

    public static final Pair<Block, Item> FAN_BLADE = addBasic("fan_blade", ShapeFunction.fromFan(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> STEEL_WINDMILL = addBasic("steel_windmill", ShapeFunction.fromFan(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> IRON_WINDMILL = addBasic("iron_windmill", ShapeFunction.fromFan(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> WOODEN_WINDMILL = addBasic("wooden_windmill", ShapeFunction.fromFan(), false, BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);

    public static final Pair<Block, Item> MECHANICAL_SCREEN = addBasic("mechanical_screen", ShapeFunction.fromScreen2(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> MECHANICAL_CHAIR = addBasic("mechanical_chair", ShapeFunction.fromChair(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> TECH_SCREEN = addBasic("tech_screen", ShapeFunction.fromScreen2(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> TECH_CHAIR = addBasic("tech_chair", ShapeFunction.fromChair(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> SCREEN_OFF = addBasic("screen_off", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SCREEN = addBasic("screen", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SCREEN_CUBE = addBasic("screen_cube", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SCREEN_DIAGRAM = addBasic("screen_diagram", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SCREEN_DNA = addBasic("screen_dna", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SCREEN_LIST = addBasic("screen_list", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SCREEN_MESSAGE = addBasic("screen_message", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SCREEN_THREEBODIES = addBasic("screen_threebodies", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SCREEN_TRANSPORT = addBasic("screen_transport", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> TECH_TABLE = addBasic("tech_table", ShapeFunction.fromTechTable(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> TECH_TABLE_CIRCLE = addBasic("tech_table_circle", ShapeFunction.fromTechTable(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> TECH_TABLE_BIGCIRCLE = addBasic("tech_table_bigcircle", ShapeFunction.fromTechTable(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> HOLOGRAM_BASE = addBasic("hologram_base", ShapeFunction.fromHologramBase(), false, BLOCK_METAL_NO_COLLISSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> SIGN_ENTRANCE = addBasic("sign_entrance", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SIGN_EXIT = addBasic("sign_exit", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SIGN_LEFT = addBasic("sign_left", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SIGN_RIGHT = addBasic("sign_right", ShapeFunction.fromScreen(), false, BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> SMALL_SIGN_LEFT = addBasic("small_sign_left", ShapeFunction.fromScreen(), false, BLOCK_METAL_LIGHT_NO_COLLISSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SMALL_SIGN_RIGHT = addBasic("small_sign_right", ShapeFunction.fromScreen(), false, BLOCK_METAL_LIGHT_NO_COLLISSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> SMALL_SIGN_GROUND = addBasic("small_sign_ground", s -> Block.createCuboidShape(0, 0, 0, 16, 1, 16), false, BLOCK_METAL_LIGHT_NO_COLLISSION, ITEM_FURNITURE);


    // Isotropic
    public static final Pair<Block, Item> BLACK_TILES = addIsotropic("black_tiles", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> BLACK_TILE_SLAB = addIsotropic("black_tile_slab", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> BLACK_TILE_STAIRS = addIsotropic("black_tile_stairs", BLOCK_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> CYAN_TILES = addIsotropic("cyan_tiles", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> CYAN_TILE_SLAB = addIsotropic("cyan_tile_slab", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> CYAN_TILE_STAIRS = addIsotropic("cyan_tile_stairs", BLOCK_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> YELLOW_TILES = addIsotropic("yellow_tiles", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> YELLOW_TILE_SLAB = addIsotropic("yellow_tile_slab", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> YELLOW_TILE_STAIRS = addIsotropic("yellow_tile_stairs", BLOCK_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> BLUE_TILES = addIsotropic("blue_tiles", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> BLUE_TILE_SLAB = addIsotropic("blue_tile_slab", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> BLUE_TILE_STAIRS = addIsotropic("blue_tile_stairs", BLOCK_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> GREEN_TILES = addIsotropic("green_tiles", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> GREEN_TILE_SLAB = addIsotropic("green_tile_slab", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> GREEN_TILE_STAIRS = addIsotropic("green_tile_stairs", BLOCK_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> RED_TILES = addIsotropic("red_tiles", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> RED_TILE_SLAB = addIsotropic("red_tile_slab", BLOCK_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> RED_TILE_STAIRS = addIsotropic("red_tile_stairs", BLOCK_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> STEEL_TILES = addIsotropic("steel_tiles", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> STEEL_TILE_SLAB = addIsotropic("steel_tile_slab", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> STEEL_TILE_STAIRS = addIsotropic("steel_tile_stairs", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> COPPER_TILES = addIsotropic("copper_tiles", BLOCK_COPPER, ITEM_BASIC);
    public static final Pair<Block, Item> copper_tile_slab = addIsotropic("copper_tile_slab", BLOCK_COPPER, ITEM_BASIC);
    public static final Pair<Block, Item> copper_tile_stairs = addIsotropic("copper_tile_stairs", BLOCK_COPPER, ITEM_BASIC);

    public static final Pair<Block, Item> GLASS_TILES = addIsotropic("glass_tiles", BLOCK_GLASS, ITEM_BASIC);
    public static final Pair<Block, Item> GLASS_TILE_SLAB = addIsotropic("glass_tile_slab", BLOCK_GLASS, ITEM_BASIC);
    public static final Pair<Block, Item> GLASS_TILE_STAIRS = addIsotropic("glass_tile_stairs", BLOCK_GLASS, ITEM_BASIC);

    public static final Pair<Block, Item> MUD_WALL_BLOCK = addIsotropic("mud_wall_block", BLOCK_MUD, ITEM_BASIC);
    public static final Pair<Block, Item> MUD_WALL_SLAB = addIsotropic("mud_wall_slab", BLOCK_MUD, ITEM_BASIC);
    public static final Pair<Block, Item> MUD_WALL_STAIRS = addIsotropic("mud_wall_stairs", BLOCK_MUD, ITEM_BASIC);

    public static final Pair<Block, Item> FRAMED_MUD_WALL_BLOCK = addIsotropic("framed_mud_wall_block", BLOCK_MUD, ITEM_BASIC);

    public static final Pair<Block, Item> LINED_MUD_WALL_BLOCK = addIsotropic("lined_mud_wall_block", BLOCK_MUD, ITEM_BASIC);
    public static final Pair<Block, Item> LINED_MUD_WALL_SLAB = addIsotropic("lined_mud_wall_slab", BLOCK_MUD, ITEM_BASIC);
    public static final Pair<Block, Item> LINED_MUD_WALL_STAIRS = addIsotropic("lined_mud_wall_stairs", BLOCK_MUD, ITEM_BASIC);

    public static final Pair<Block, Item> CROSSED_MUD_WALL_SLAB = addIsotropic("crossed_mud_wall_slab", BLOCK_MUD, ITEM_BASIC);
    public static final Pair<Block, Item> CROSSED_MUD_WALL_STAIRS = addIsotropic("crossed_mud_wall_stairs", BLOCK_MUD, ITEM_BASIC);

    public static final Pair<Block, Item> DIRTY_MUD_WALL_BLOCK = addIsotropic("dirty_mud_wall_block", BLOCK_MUD, ITEM_BASIC);
    public static final Pair<Block, Item> DIRTY_MUD_WALL_SLAB = addIsotropic("dirty_mud_wall_slab", BLOCK_MUD, ITEM_BASIC);
    public static final Pair<Block, Item> DIRTY_MUD_WALL_STAIRS = addIsotropic("dirty_mud_wall_stairs", BLOCK_MUD, ITEM_BASIC);

    public static final Pair<Block, Item> CYAN_BRICKS = addIsotropic("cyan_bricks", BLOCK_BRICK, ITEM_BASIC);
    public static final Pair<Block, Item> CYAN_BRICK_SLAB = addIsotropic("cyan_brick_slab", BLOCK_BRICK, ITEM_BASIC);
    public static final Pair<Block, Item> CYAN_BRICK_STAIRS = addIsotropic("cyan_brick_stairs", BLOCK_BRICK, ITEM_BASIC);

    public static final Pair<Block, Item> BLACK_BRICKS = addIsotropic("black_bricks", BLOCK_BRICK, ITEM_BASIC);
    public static final Pair<Block, Item> BLACK_BRICK_SLAB = addIsotropic("black_brick_slab", BLOCK_BRICK, ITEM_BASIC);
    public static final Pair<Block, Item> BLACK_BRICK_STAIRS = addIsotropic("black_brick_stairs", BLOCK_BRICK, ITEM_BASIC);

    public static final Pair<Block, Item> VARNISHED_WOOD = addIsotropic("varnished_wood", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> VARNISHED_LOG = addIsotropic("varnished_log", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> VARNISHED_LOG_SLAB = addIsotropic("varnished_log_slab", BLOCK_WOOD, ITEM_BASIC);

    public static final Pair<Block, Item> EBONY_WOOD = addIsotropic("ebony_wood", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> EBONY_LOG = addIsotropic("ebony_log", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> EBONY_LOG_SLAB = addIsotropic("ebony_log_slab", BLOCK_WOOD, ITEM_BASIC);

    public static final Pair<Block, Item> MAHOGANY_WOOD = addIsotropic("mahogany_wood", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> MAHOGANY_LOG = addIsotropic("mahogany_log", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> MAHOGANY_LOG_SLAB = addIsotropic("mahogany_log_slab", BLOCK_WOOD, ITEM_BASIC);

    public static final Pair<Block, Item> VARNISHED_PLANKS = addIsotropic("varnished_planks", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> VARNISHED_SLAB = addIsotropic("varnished_slab", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> VARNISHED_STAIRS = addIsotropic("varnished_stairs", BLOCK_WOOD, ITEM_BASIC);

    public static final Pair<Block, Item> EBONY_PLANKS = addIsotropic("ebony_planks", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> EBONY_SLAB = addIsotropic("ebony_slab", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> EBONY_STAIRS = addIsotropic("ebony_stairs", BLOCK_WOOD, ITEM_BASIC);

    public static final Pair<Block, Item> MAHOGANY_PLANKS = addIsotropic("mahogany_planks", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> MAHOGANY_SLAB = addIsotropic("mahogany_slab", BLOCK_WOOD, ITEM_BASIC);
    public static final Pair<Block, Item> MAHOGANY_STAIRS = addIsotropic("mahogany_stairs", BLOCK_WOOD, ITEM_BASIC);

    public static final Pair<Block, Item> SANDSTONE_PILLAR = addIsotropic("sandstone_pillar", BLOCK_SANDSTONE, ITEM_BASIC);

    public static final Pair<Block, Item> POLISHED_SANDSTONE = addIsotropic("polished_sandstone", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> POLISHED_SANDSTONE_SLAB = addIsotropic("polished_sandstone_slab", BLOCK_SANDSTONE, ITEM_BASIC);

    public static final Pair<Block, Item> SANDSTONE_BRICKS = addIsotropic("sandstone_bricks", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> SANDSTONE_BRICK_SLAB = addIsotropic("sandstone_brick_slab", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> SANDSTONE_BRICK_STAIRS = addIsotropic("sandstone_brick_stairs", BLOCK_SANDSTONE, ITEM_BASIC);

    public static final Pair<Block, Item> SANDSTONE_SMALL_BRICKS = addIsotropic("sandstone_small_bricks", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> SANDSTONE_SMALL_BRICK_SLAB = addIsotropic("sandstone_small_brick_slab", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> SANDSTONE_SMALL_BRICK_STAIRS = addIsotropic("sandstone_small_brick_stairs", BLOCK_SANDSTONE, ITEM_BASIC);

    public static final Pair<Block, Item> RED_SANDSTONE_PILLAR = addIsotropic("red_sandstone_pillar", BLOCK_SANDSTONE, ITEM_BASIC);

    public static final Pair<Block, Item> POLISHED_RED_SANDSTONE = addIsotropic("polished_red_sandstone", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> POLISHED_RED_SANDSTONE_SLAB = addIsotropic("polished_red_sandstone_slab", BLOCK_SANDSTONE, ITEM_BASIC);

    public static final Pair<Block, Item> RED_SANDSTONE_BRICKS = addIsotropic("red_sandstone_bricks", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> RED_SANDSTONE_BRICK_SLAB = addIsotropic("red_sandstone_brick_slab", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> RED_SANDSTONE_BRICK_STAIRS = addIsotropic("red_sandstone_brick_stairs", BLOCK_SANDSTONE, ITEM_BASIC);

    public static final Pair<Block, Item> RED_SANDSTONE_SMALL_BRICKS = addIsotropic("red_sandstone_small_bricks", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> RED_SANDSTONE_SMALL_BRICK_SLAB = addIsotropic("red_sandstone_small_brick_slab", BLOCK_SANDSTONE, ITEM_BASIC);
    public static final Pair<Block, Item> RED_SANDSTONE_SMALL_BRICK_STAIRS = addIsotropic("red_sandstone_small_brick_stairs", BLOCK_SANDSTONE, ITEM_BASIC);

    public static final Pair<Block, Item> STONE_BRICK_PILLAR = addIsotropic("stone_brick_pillar", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> STONE_BRICK_PAVEMENT = addIsotropic("stone_brick_pavement", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> STONE_BRICK_PAVEMENT_SLAB = addIsotropic("stone_brick_pavement_slab", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> DEEPSLATE_PILLAR = addIsotropic("deepslate_pillar", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> DEEPSLATE_PAVEMENT = addIsotropic("deepslate_pavement", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> DEEPSLATE_PAVEMENT_SLAB = addIsotropic("deepslate_pavement_slab", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MOSSY_DEEPSLATE_BRICKS = addIsotropic("mossy_deepslate_bricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MOSSY_DEEPSLATE_BRICK_SLAB = addIsotropic("mossy_deepslate_brick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MOSSY_DEEPSLATE_BRICK_STAIRS = addIsotropic("mossy_deepslate_brick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> BLACKSTONE_PILLAR = addIsotropic("blackstone_pillar", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> BLACKSTONE_PAVEMENT = addIsotropic("blackstone_pavement", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> BLACKSTONE_PAVEMENT_SLAB = addIsotropic("blackstone_pavement_slab", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> GILDED_BLACKSTONE_BRICKS = addIsotropic("gilded_blackstone_bricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> GILDED_BLACKSTONE_BRICK_SLAB = addIsotropic("gilded_blackstone_brick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> GILDED_BLACKSTONE_BRICK_STAIRS = addIsotropic("gilded_blackstone_brick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> GILDED_BLACKSTONE_BRICK_PILLAR = addIsotropic("gilded_blackstone_brick_pillar", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> CHISELED_GILDED_BLACKSTONE = addIsotropic("chiseled_gilded_blackstone", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> LUXURY_GILDED_BLACKSTONE = addIsotropic("luxury_gilded_blackstone", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_STONE = addIsotropic("maya_stone", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_STONE_SLAB = addIsotropic("maya_stone_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_STONE_STAIRS = addIsotropic("maya_stone_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_STONEBRICKS = addIsotropic("maya_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_STONEBRICK_SLAB = addIsotropic("maya_stonebrick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_STONEBRICK_STAIRS = addIsotropic("maya_stonebrick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_BRICKS = addIsotropic("maya_bricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_BRICK_SLAB = addIsotropic("maya_brick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_BRICK_STAIRS = addIsotropic("maya_brick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_POLISHED_STONEBRICKS = addIsotropic("maya_polished_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_POLISHED_STONEBRICK_SLAB = addIsotropic("maya_polished_stonebrick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_POLISHED_STONEBRICK_STAIRS = addIsotropic("maya_polished_stonebrick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_MOSSY_STONEBRICKS = addIsotropic("maya_mossy_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_MOSSY_STONEBRICK_SLAB = addIsotropic("maya_mossy_stonebrick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_MOSSY_STONEBRICK_STAIRS = addIsotropic("maya_mossy_stonebrick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_MOSSY_BRICKS = addIsotropic("maya_mossy_bricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_MOSSY_BRICK_SLAB = addIsotropic("maya_mossy_brick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_MOSSY_BRICK_STAIRS = addIsotropic("maya_mossy_brick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_CHISELED_STONEBRICKS = addIsotropic("maya_chiseled_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_CUT_STONEBRICKS = addIsotropic("maya_cut_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_DOUBLE_SCREW_THREAD_STONE = addIsotropic("maya_double_screw_thread_stone", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_QUAD_SCREW_THREAD_STONE = addIsotropic("maya_quad_screw_thread_stone", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_PICTOGRAM_STONE = addIsotropic("maya_pictogram_stone", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_SKULL_STONE = addIsotropic("maya_skull_stone", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> MAYA_PILLAR = addIsotropic("maya_pillar", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> MAYA_MOSSY_PILLAR = addIsotropic("maya_mossy_pillar", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> AZTEC_STONEBRICKS = addIsotropic("aztec_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> AZTEC_STONEBRICK_SLAB = addIsotropic("aztec_stonebrick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> AZTEC_STONEBRICK_STAIRS = addIsotropic("aztec_stonebrick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> AZTEC_MOSSY_STONEBRICKS = addIsotropic("aztec_mossy_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> AZTEC_MOSSY_STONEBRICK_SLAB = addIsotropic("aztec_mossy_stonebrick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> AZTEC_MOSSY_STONEBRICK_STAIRS = addIsotropic("aztec_mossy_stonebrick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> AZTEC_SCULPTURE_STONE = addIsotropic("aztec_sculpture_stone", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> AZTEC_CHISELED_STONEBRICKS = addIsotropic("aztec_chiseled_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> AZTEC_CUT_STONEBRICKS = addIsotropic("aztec_cut_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> INCA_STONE = addIsotropic("inca_stone", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> INCA_STONE_SLAB = addIsotropic("inca_stone_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> INCA_STONE_STAIRS = addIsotropic("inca_stone_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> INCA_STONEBRICKS = addIsotropic("inca_stonebricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> INCA_STONEBRICK_SLAB = addIsotropic("inca_stonebrick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> INCA_STONEBRICK_STAIRS = addIsotropic("inca_stonebrick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> INCA_BRICKS = addIsotropic("inca_bricks", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> INCA_BRICK_SLAB = addIsotropic("inca_brick_slab", BLOCK_HARD_STONE, ITEM_BASIC);
    public static final Pair<Block, Item> INCA_BRICK_STAIRS = addIsotropic("inca_brick_stairs", BLOCK_HARD_STONE, ITEM_BASIC);

    public static final Pair<Block, Item> CUT_OBSIDIAN = addIsotropic("cut_obsidian", BLOCK_OBSIDIAN, ITEM_BASIC);
    public static final Pair<Block, Item> CUT_OBSIDIAN_SLAB = addIsotropic("cut_obsidian_slab", BLOCK_OBSIDIAN, ITEM_BASIC);
    public static final Pair<Block, Item> CUT_OBSIDIAN_PILLAR = addIsotropic("cut_obsidian_pillar", BLOCK_OBSIDIAN, ITEM_BASIC);

    public static final Pair<Block, Item> CUT_OBSIDIAN_BRICKS = addIsotropic("cut_obsidian_bricks", BLOCK_OBSIDIAN, ITEM_BASIC);
    public static final Pair<Block, Item> CUT_OBSIDIAN_BRICK_SLAB = addIsotropic("cut_obsidian_brick_slab", BLOCK_OBSIDIAN, ITEM_BASIC);
    public static final Pair<Block, Item> CUT_OBSIDIAN_BRICK_STAIRS = addIsotropic("cut_obsidian_brick_stairs", BLOCK_OBSIDIAN, ITEM_BASIC);

    public static final Pair<Block, Item> CRYING_OBSIDIAN_BRICKS = addIsotropic("crying_obsidian_bricks", BLOCK_OBSIDIAN, ITEM_BASIC);
    public static final Pair<Block, Item> CRYING_OBSIDIAN_BRICK_SLAB = addIsotropic("crying_obsidian_brick_slab", BLOCK_OBSIDIAN, ITEM_BASIC);
    public static final Pair<Block, Item> CRYING_OBSIDIAN_BRICK_STAIRS = addIsotropic("crying_obsidian_brick_stairs", BLOCK_OBSIDIAN, ITEM_BASIC);

    public static final Pair<Block, Item> CUT_GOLD_BLOCK = addIsotropic("cut_gold_block", BLOCK_GOLD, ITEM_BASIC);
    public static final Pair<Block, Item> CUT_GOLD_BLOCK_SLAB = addIsotropic("cut_gold_block_slab", BLOCK_GOLD, ITEM_BASIC);
    public static final Pair<Block, Item> CUT_GOLD_BLOCK_STAIRS = addIsotropic("cut_gold_block_stairs", BLOCK_GOLD, ITEM_BASIC);

    public static final Pair<Block, Item> GOLD_BRICKS = addIsotropic("gold_bricks", BLOCK_GOLD, ITEM_BASIC);
    public static final Pair<Block, Item> GOLD_BRICK_SLAB = addIsotropic("gold_brick_slab", BLOCK_GOLD, ITEM_BASIC);
    public static final Pair<Block, Item> GOLD_BRICK_STAIRS = addIsotropic("gold_brick_stairs", BLOCK_GOLD, ITEM_BASIC);
    public static final Pair<Block, Item> GOLD_PILLAR = addIsotropic("gold_pillar", BLOCK_GOLD, ITEM_BASIC);

    public static final Pair<Block, Item> CHISELED_GOLD_BLOCK = addIsotropic("chiseled_gold_block", BLOCK_GOLD, ITEM_BASIC);
    public static final Pair<Block, Item> PAINTED_GOLD_BLOCK = addIsotropic("painted_gold_block", BLOCK_GOLD, ITEM_BASIC);

    public static final Pair<Block, Item> BRONZE_BLOCK = addIsotropic("bronze_block", BLOCK_BRONZE, ITEM_BASIC);
    public static final Pair<Block, Item> SMOOTH_BRONZE_BLOCK = addIsotropic("smooth_bronze_block", BLOCK_BRONZE, ITEM_BASIC);
    public static final Pair<Block, Item> INSCRIPTION_BRONZE_BLOCK = addIsotropic("inscription_bronze_block", BLOCK_BRONZE, ITEM_BASIC);

    public static final Pair<Block, Item> CUT_BRONZE_BLOCK = addIsotropic("cut_bronze_block", BLOCK_BRONZE, ITEM_BASIC);
    public static final Pair<Block, Item> CUT_BRONZE_BLOCK_SLAB = addIsotropic("cut_bronze_block_slab", BLOCK_BRONZE, ITEM_BASIC);
    public static final Pair<Block, Item> CUT_BRONZE_BLOCK_STAIRS = addIsotropic("cut_bronze_block_stairs", BLOCK_BRONZE, ITEM_BASIC);

    public static final Pair<Block, Item> CHISELED_BRONZE_BLOCK = addIsotropic("chiseled_bronze_block", BLOCK_BRONZE, ITEM_BASIC);
    public static final Pair<Block, Item> BRONZE_PILLAR = addIsotropic("bronze_pillar", BLOCK_BRONZE, ITEM_BASIC);

    public static final Pair<Block, Item> STEEL_BLOCK = addIsotropic("steel_block", BLOCK_HARD_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> SMOOTH_STEEL_BLOCK = addIsotropic("smooth_steel_block", BLOCK_HARD_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> STEEL_PILLAR = addIsotropic("steel_pillar", BLOCK_HARD_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> STEEL_FLOOR = addIsotropic("steel_floor", BLOCK_HARD_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> STEEL_FLOOR_SLAB = addIsotropic("steel_floor_slab", BLOCK_HARD_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> STEEL_FLOOR_STAIRS = addIsotropic("steel_floor_stairs", BLOCK_HARD_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> CHISELED_STEEL_BLOCK = addIsotropic("chiseled_steel_block", BLOCK_HARD_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> HOLLOW_STEEL_BLOCK = addIsotropic("hollow_steel_block", BLOCK_HOLLOW_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FRAMED_STEEL_BLOCK = addIsotropic("framed_steel_block", BLOCK_HARD_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_BLOCK = addIsotropic("factory_block", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_SLAB = addIsotropic("factory_slab", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_STAIRS = addIsotropic("factory_stairs", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_BLOCK_RUSTING = addIsotropic("factory_block_rusting", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_SLAB_RUSTING = addIsotropic("factory_slab_rusting", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_STAIRS_RUSTING = addIsotropic("factory_stairs_rusting", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_BLOCK_RUSTED = addIsotropic("factory_block_rusted", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_SLAB_RUSTED = addIsotropic("factory_slab_rusted", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_STAIRS_RUSTED = addIsotropic("factory_stairs_rusted", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_DANGER = addIsotropic("factory_danger", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_DANGER_RUSTING = addIsotropic("factory_danger_rusting", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_DANGER_RUSTED = addIsotropic("factory_danger_rusted", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_ATTENTION = addIsotropic("factory_attention", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_ATTENTION_RUSTING = addIsotropic("factory_attention_rusting", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_ATTENTION_RUSTED = addIsotropic("factory_attention_rusted", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_ELECTRICITY = addIsotropic("factory_electricity", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_ELECTRICITY_RUSTING = addIsotropic("factory_electricity_rusting", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_ELECTRICITY_RUSTED = addIsotropic("factory_electricity_rusted", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_TOXIC = addIsotropic("factory_toxic", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_TOXIC_RUSTING = addIsotropic("factory_toxic_rusting", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_TOXIC_RUSTED = addIsotropic("factory_toxic_rusted", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_RADIATION = addIsotropic("factory_radiation", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_RADIATION_RUSTING = addIsotropic("factory_radiation_rusting", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_RADIATION_RUSTED = addIsotropic("factory_radiation_rusted", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_BIOHAZARD = addIsotropic("factory_biohazard", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_BIOHAZARD_RUSTING = addIsotropic("factory_biohazard_rusting", BLOCK_IRON, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_BIOHAZARD_RUSTED = addIsotropic("factory_biohazard_rusted", BLOCK_IRON, ITEM_BASIC);

    public static final Pair<Block, Item> FACTORY_LAMP_BLOCK = addIsotropic("factory_lamp_block", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_LAMP_SLAB = addIsotropic("factory_lamp_slab", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> FACTORY_LAMP_STAIRS = addIsotropic("factory_lamp_stairs", BLOCK_LIGHT, ITEM_BASIC);

    public static final Pair<Block, Item> TECH_LAMP_BLOCK = addIsotropic("tech_lamp_block", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> TECH_LAMP_SLAB = addIsotropic("tech_lamp_slab", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> TECH_LAMP_STAIRS = addIsotropic("tech_lamp_stairs", BLOCK_LIGHT, ITEM_BASIC);

    public static final Pair<Block, Item> TRANSLUCENT_LAMP_BLOCK = addIsotropic("translucent_lamp_block", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> TRANSLUCENT_LAMP_SLAB = addIsotropic("translucent_lamp_slab", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> TRANSLUCENT_LAMP_STAIRS = addIsotropic("translucent_lamp_stairs", BLOCK_LIGHT, ITEM_BASIC);

    public static final Pair<Block, Item> STEEL_FILINGS = addIsotropic("steel_filings", BLOCK_SAND, ITEM_BASIC);

    public static final Pair<Block, Item> QUARTZ_SAND = addIsotropic("quartz_sand", BLOCK_SAND, ITEM_BASIC);
    public static final Pair<Block, Item> TOUGHENED_SAND = addIsotropic("toughened_sand", BLOCK_HARD_SAND, ITEM_BASIC);

    public static final Pair<Block, Item> QUARTZ_GLASS = addIsotropic("quartz_glass", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> QUARTZ_GLASS_SLAB = addIsotropic("quartz_glass_slab", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> QUARTZ_GLASS_STAIRS = addIsotropic("quartz_glass_stairs", BLOCK_LIGHT, ITEM_BASIC);

    public static final Pair<Block, Item> TOUGHENED_GLASS = addIsotropic("toughened_glass", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> TOUGHENED_GLASS_SLAB = addIsotropic("toughened_glass_slab", BLOCK_LIGHT, ITEM_BASIC);
    public static final Pair<Block, Item> TOUGHENED_GLASS_STAIRS = addIsotropic("toughened_glass_stairs", BLOCK_LIGHT, ITEM_BASIC);

    public static final Pair<Block, Item> BLACK_ROOF = addIsotropic("black_roof", BLOCK_ROOF, ITEM_STRUCTURE);
    public static final Pair<Block, Item> BLACK_ROOF_EAVE = addIsotropic("black_roof_eave", BLOCK_ROOF, ITEM_STRUCTURE);
    public static final Pair<Block, Item> BLACK_ROOF_FLAT = addIsotropic("black_roof_flat", BLOCK_ROOF, ITEM_STRUCTURE);

    public static final Pair<Block, Item> NETHERRACK_SLAB = addIsotropic("netherrack_slab", BLOCK_NETHER_STONE, ITEM_NATURE);
    public static final Pair<Block, Item> CRIMSON_NYLIUM_SLAB = addIsotropic("crimson_nylium_slab", BLOCK_NETHER_STONE, ITEM_NATURE);
    public static final Pair<Block, Item> WARPED_NYLIUM_SLAB = addIsotropic("warped_nylium_slab", BLOCK_NETHER_STONE, ITEM_NATURE);
    public static final Pair<Block, Item> END_STONE_SLAB = addIsotropic("end_stone_slab", BLOCK_END_STONE, ITEM_NATURE);

    public static final Pair<Block, Item> DIRT_COBBLESTONE = addIsotropic("dirt_cobblestone", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> GRASS_COBBLESTONE = addIsotropic("grass_cobblestone", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SANDY_COBBLESTONE = addIsotropic("sandy_cobblestone", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SNOWY_COBBLESTONE = addIsotropic("snowy_cobblestone", BLOCK_SANDSTONE, ITEM_NATURE);

    public static final Pair<Block, Item> COBBLESTONE_PATH = addIsotropic("cobblestone_path", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> DIRT_COBBLESTONE_PATH = addIsotropic("dirt_cobblestone_path", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> GRASS_COBBLESTONE_PATH = addIsotropic("grass_cobblestone_path", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SANDY_COBBLESTONE_PATH = addIsotropic("sandy_cobblestone_path", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SNOWY_COBBLESTONE_PATH = addIsotropic("snowy_cobblestone_path", BLOCK_SANDSTONE, ITEM_NATURE);

    public static final Pair<Block, Item> DIRT_COBBLESTONE_SLAB = addIsotropic("dirt_cobblestone_slab", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> GRASS_COBBLESTONE_SLAB = addIsotropic("grass_cobblestone_slab", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SANDY_COBBLESTONE_SLAB = addIsotropic("sandy_cobblestone_slab", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SNOWY_COBBLESTONE_SLAB = addIsotropic("snowy_cobblestone_slab", BLOCK_SANDSTONE, ITEM_NATURE);

    public static final Pair<Block, Item> COBBLESTONE_PATH_SLAB = addIsotropic("cobblestone_path_slab", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> DIRT_COBBLESTONE_PATH_SLAB = addIsotropic("dirt_cobblestone_path_slab", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> GRASS_COBBLESTONE_PATH_SLAB = addIsotropic("grass_cobblestone_path_slab", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SANDY_COBBLESTONE_PATH_SLAB = addIsotropic("sandy_cobblestone_path_slab", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SNOWY_COBBLESTONE_PATH_SLAB = addIsotropic("snowy_cobblestone_path_slab", BLOCK_SANDSTONE, ITEM_NATURE);

    public static final Pair<Block, Item> DIRT_COBBLESTONE_STAIRS = addIsotropic("dirt_cobblestone_stairs", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> GRASS_COBBLESTONE_STAIRS = addIsotropic("grass_cobblestone_stairs", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SANDY_COBBLESTONE_STAIRS = addIsotropic("sandy_cobblestone_stairs", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SNOWY_COBBLESTONE_STAIRS = addIsotropic("snowy_cobblestone_stairs", BLOCK_SANDSTONE, ITEM_NATURE);

    public static final Pair<Block, Item> COBBLESTONE_PATH_STAIRS = addIsotropic("cobblestone_path_stairs", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> DIRT_COBBLESTONE_PATH_STAIRS = addIsotropic("dirt_cobblestone_path_stairs", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> GRASS_COBBLESTONE_PATH_STAIRS = addIsotropic("grass_cobblestone_path_stairs", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SANDY_COBBLESTONE_PATH_STAIRS = addIsotropic("sandy_cobblestone_path_stairs", BLOCK_SANDSTONE, ITEM_NATURE);
    public static final Pair<Block, Item> SNOWY_COBBLESTONE_PATH_STAIRS = addIsotropic("snowy_cobblestone_path_stairs", BLOCK_SANDSTONE, ITEM_NATURE);

    public static final Pair<Block, Item> VARNISHED_TABLE = addIsotropic("varnished_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> VARNISHED_BIG_TABLE = addIsotropic("varnished_big_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> VARNISHED_TALL_TABLE = addIsotropic("varnished_tall_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);

    public static final Pair<Block, Item> EBONY_TABLE = addIsotropic("ebony_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> EBONY_BIG_TABLE = addIsotropic("ebony_big_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> EBONY_TALL_TABLE = addIsotropic("ebony_tall_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);

    public static final Pair<Block, Item> MAHOGANY_TABLE = addIsotropic("mahogany_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MAHOGANY_BIG_TABLE = addIsotropic("mahogany_big_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> MAHOGANY_TALL_TABLE = addIsotropic("mahogany_tall_table", BLOCK_WOOD_FURNITURE, ITEM_FURNITURE);


    // Plant
    public static final Pair<Block, Item> DIRT_SLAB = addPlant("dirt_slab", BLOCK_DIRT, ITEM_NATURE);
    public static final Pair<Block, Item> DIRT_PATH_SLAB = addPlant("dirt_path_slab", BLOCK_DIRT, ITEM_NATURE);
    public static final Pair<Block, Item> GRASS_BLOCK_SLAB = addPlant("grass_block_slab", BLOCK_DIRT, ITEM_NATURE);
    public static final Pair<Block, Item> MYCELIUM_SLAB = addPlant("mycelium_slab", BLOCK_DIRT, ITEM_NATURE);
    public static final Pair<Block, Item> PODZOL_SLAB = addPlant("podzol_slab", BLOCK_DIRT, ITEM_NATURE);

    public static final Pair<Block, Item> GINKGO_LEAVES = addPlant("ginkgo_leaves", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> ORANGE_MAPLE_LEAVES = addPlant("orange_maple_leaves", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> RED_MAPLE_LEAVES = addPlant("red_maple_leaves", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> PEACH_BLOSSOM = addPlant("peach_blossom", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> PEACH_BLOSSOM_LEAVES = addPlant("peach_blossom_leaves", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> CHERRY_BLOSSOM = addPlant("cherry_blossom", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> CHERRY_BLOSSOM_LEAVES = addPlant("cherry_blossom_leaves", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> WHITE_CHERRY_BLOSSOM = addPlant("white_cherry_blossom", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> WHITE_CHERRY_BLOSSOM_LEAVES = addPlant("white_cherry_blossom_leaves", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> PLANTABLE_LEAVES = addPlant("plantable_leaves", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> PLANTABLE_LEAVES_DARK = addPlant("plantable_leaves_dark", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> WILLOW_LEAVES = addPlant("willow_leaves", BLOCK_LEAVES, ITEM_NATURE);

    public static final Pair<Block, Item> GINKGO_LEAVES_SHATTER = addPlant("ginkgo_leaves_shatter", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> ORANGE_MAPLE_LEAVES_SHATTER = addPlant("orange_maple_leaves_shatter", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> RED_MAPLE_LEAVES_SHATTER = addPlant("red_maple_leaves_shatter", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> PEACH_BLOSSOM_SHATTER = addPlant("peach_blossom_shatter", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> CHERRY_BLOSSOM_SHATTER = addPlant("cherry_blossom_shatter", BLOCK_LEAVES, ITEM_NATURE);
    public static final Pair<Block, Item> WHITE_CHERRY_BLOSSOM_SHATTER = addPlant("white_cherry_blossom_shatter", BLOCK_LEAVES, ITEM_NATURE);

    // Special
    public static final Pair<Block, Item> BLACK_ROOF_RIDGE = addSpecial("black_roof_ridge", BLOCK_ROOF, ITEM_STRUCTURE);
    public static final Pair<Block, Item> CUP = addSpecial("cup", BLOCK_MINIATURE, ITEM_FURNITURE);
    public static final Pair<Block, Item> REFRESHMENTS = addSpecial("refreshments", BLOCK_DESSERT, ITEM_FURNITURE);
    public static final Pair<Block, Item> FRUIT_PLATTER = addSpecial("fruit_platter", BLOCK_DESSERT, ITEM_FURNITURE);

    public static final Pair<Block, Item> PLAIN_ITEM_DISPLAY = addSpecial("plain_item_display", BLOCK_STONE_DISPLAY, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> GORGEOUS_ITEM_DISPLAY = addSpecial("gorgeous_item_display", BLOCK_STONE_DISPLAY, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> MECHANICAL_ITEM_DISPLAY = addSpecial("mechanical_item_display", BLOCK_METAL_DISPLAY, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> TECH_ITEM_DISPLAY = addSpecial("tech_item_display", BLOCK_METAL_DISPLAY, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> ITEM_PROJECTOR = addSpecial("item_projector", BLOCK_METAL_DISPLAY, ITEM_FUNCTIONAL);

    public static final Pair<Block, Item> PLAIN_BLOCK_DISPLAY = addSpecial("plain_block_display", BLOCK_STONE_DISPLAY, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> GORGEOUS_BLOCK_DISPLAY = addSpecial("gorgeous_block_display", BLOCK_STONE_DISPLAY, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> MECHANICAL_BLOCK_DISPLAY = addSpecial("mechanical_block_display", BLOCK_METAL_DISPLAY, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> TECH_BLOCK_DISPLAY = addSpecial("tech_block_display", BLOCK_METAL_DISPLAY, ITEM_FUNCTIONAL);

    public static final Pair<Block, Item> VARNISHED_WARDROBE = addSpecial("varnished_wardrobe", BLOCK_WOOD_WARDROBE, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> EBONY_WARDROBE = addSpecial("ebony_wardrobe", BLOCK_WOOD_WARDROBE, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> MAHOGANY_WARDROBE = addSpecial("mahogany_wardrobe", BLOCK_WOOD_WARDROBE, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> IRON_WARDROBE = addSpecial("iron_wardrobe", BLOCK_METAL_WARDROBE, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> GLASS_WARDROBE = addSpecial("glass_wardrobe", BLOCK_METAL_WARDROBE, ITEM_FUNCTIONAL);
    public static final Pair<Block, Item> FULL_GLASS_WARDROBE = addSpecial("full_glass_wardrobe", BLOCK_GLASS_WARDROBE, ITEM_FUNCTIONAL);

    public static final Pair<Block, Item> FACTORY_LIGHT_BAR = addSpecial("factory_light_bar", BLOCK_METAL_LIGHT_NO_COLLISSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> FACTORY_VENT_FAN = addSpecial("factory_vent_fan", BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);
    public static final Pair<Block, Item> FACTORY_VENT_FAN_BIG = addSpecial("factory_vent_fan_big", BLOCK_METAL_NO_OCCLUSION, ITEM_FURNITURE);

    public static final Pair<Block, Item> MECHANICAL_CONSOLE = addSpecial("mechanical_console", BLOCK_METAL_LIGHT, ITEM_FURNITURE);

    public static final Pair<Block, Item> TECH_CONSOLE = addSpecial("tech_console", BLOCK_METAL_LIGHT, ITEM_FURNITURE);

    public static void registry() {

    }

}
