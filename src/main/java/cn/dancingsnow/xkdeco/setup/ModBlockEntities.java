package cn.dancingsnow.xkdeco.setup;

import cn.dancingsnow.xkdeco.XKDeco;
import cn.dancingsnow.xkdeco.blockentities.BlockDisplayBlockEntity;
import cn.dancingsnow.xkdeco.blockentities.ItemDisplayBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
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

    public static void registry() {}

}
