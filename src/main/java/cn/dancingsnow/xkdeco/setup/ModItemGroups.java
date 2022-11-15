package cn.dancingsnow.xkdeco.setup;

import cn.dancingsnow.xkdeco.XKDeco;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    // TODO: Icon change later
    public static final ItemGroup TAB_BASIC = createItemGroup(XKDeco.MOD_ID + "_basic", Items.ACACIA_BOAT);
    public static final ItemGroup TAB_STRUCTURE = createItemGroup(XKDeco.MOD_ID + "_structure", Items.ACACIA_BOAT);
    public static final ItemGroup TAB_NATURE = createItemGroup(XKDeco.MOD_ID + "_nature", Items.ACACIA_BOAT);
    public static final ItemGroup TAB_FURNITURE = createItemGroup(XKDeco.MOD_ID + "_furniture", Items.ACACIA_BOAT);
    public static final ItemGroup TAB_FUNCTIONAL = createItemGroup(XKDeco.MOD_ID + "_functional", Items.ACACIA_BOAT);

    private static ItemGroup createItemGroup(String groupId, Item icon) {
        return FabricItemGroupBuilder.create(new Identifier(XKDeco.MOD_ID, groupId))
                .icon(icon::getDefaultStack)
                .build();
    }
}
