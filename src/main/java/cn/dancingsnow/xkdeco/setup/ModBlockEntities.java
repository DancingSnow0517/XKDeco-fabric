package cn.dancingsnow.xkdeco.setup;

import cn.dancingsnow.xkdeco.XKDeco;
import cn.dancingsnow.xkdeco.blockentities.BlockDisplayBlockEntity;
import cn.dancingsnow.xkdeco.blockentities.ItemDisplayBlockEntity;
import cn.dancingsnow.xkdeco.blockentities.WallBlockEntity;
import cn.dancingsnow.xkdeco.blockentities.WardrobeBlockEntity;
import cn.dancingsnow.xkdeco.blocks.SpecialWallBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static final BlockEntityType<BlockDisplayBlockEntity> BLOCK_DISPLAY_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(XKDeco.MOD_ID, "block_display"),
            FabricBlockEntityTypeBuilder.create(BlockDisplayBlockEntity::new,
                    ModBlocks.PLAIN_BLOCK_DISPLAY.getLeft(), ModBlocks.GORGEOUS_BLOCK_DISPLAY.getLeft(),
                    ModBlocks.MECHANICAL_BLOCK_DISPLAY.getLeft(), ModBlocks.TECH_BLOCK_DISPLAY.getLeft()
            ).build()
    );

    public static final BlockEntityType<ItemDisplayBlockEntity> ITEM_DISPLAY_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(XKDeco.MOD_ID, "item_display"),
            FabricBlockEntityTypeBuilder.create(ItemDisplayBlockEntity::new,
                    ModBlocks.ITEM_PROJECTOR.getLeft(),
                    ModBlocks.PLAIN_ITEM_DISPLAY.getLeft(), ModBlocks.GORGEOUS_ITEM_DISPLAY.getLeft(),
                    ModBlocks.MECHANICAL_ITEM_DISPLAY.getLeft(), ModBlocks.TECH_ITEM_DISPLAY.getLeft()
            ).build()
    );

    public static final BlockEntityType<WallBlockEntity> WALL_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(XKDeco.MOD_ID, "special_wall"),
            FabricBlockEntityTypeBuilder.create(WallBlockEntity::new,
                    Registry.BLOCK.getIds().stream().map(Registry.BLOCK::get)
                            .filter(SpecialWallBlock.class::isInstance).toArray(Block[]::new)
                    ).build()
    );

    public static final BlockEntityType<WardrobeBlockEntity> WARDROBE_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(XKDeco.MOD_ID, "wardrobe"),
            FabricBlockEntityTypeBuilder.create(WardrobeBlockEntity::new,
                    ModBlocks.VARNISHED_WARDROBE.getLeft(), ModBlocks.EBONY_WARDROBE.getLeft(),
                    ModBlocks.MAHOGANY_WARDROBE.getLeft(), ModBlocks.IRON_WARDROBE.getLeft(),
                    ModBlocks.GLASS_WARDROBE.getLeft(), ModBlocks.FULL_GLASS_WARDROBE.getLeft()
            ).build()
    );

    public static void registry() {
    }

}
