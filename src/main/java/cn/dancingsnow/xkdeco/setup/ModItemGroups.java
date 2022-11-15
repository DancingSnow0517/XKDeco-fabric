package cn.dancingsnow.xkdeco.setup;

import cn.dancingsnow.xkdeco.XKDeco;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModItemGroups {
    // TODO: Icon change later
    public static final ItemGroup TAB_BASIC = createItemGroup(XKDeco.MOD_ID + "_basic", "black_tiles");
    public static final ItemGroup TAB_STRUCTURE = createItemGroup(XKDeco.MOD_ID + "_structure", "black_roof_flat");
    public static final ItemGroup TAB_NATURE = createItemGroup(XKDeco.MOD_ID + "_nature", "grass_block_slab");
    public static final ItemGroup TAB_FURNITURE = createItemGroup(XKDeco.MOD_ID + "_furniture", "varnished_big_table");
    public static final ItemGroup TAB_FUNCTIONAL = createItemGroup(XKDeco.MOD_ID + "_functional", "tech_item_display");

    private static ItemGroup createItemGroup(String groupId, String icon) {
        return FabricItemGroupBuilder.create(new Identifier(XKDeco.MOD_ID, groupId))
                .icon(() -> {
                    var block = Registry.BLOCK.get(new Identifier(XKDeco.MOD_ID, icon));
                    return block.asItem().getDefaultStack();
                })
                .build();
    }
}
