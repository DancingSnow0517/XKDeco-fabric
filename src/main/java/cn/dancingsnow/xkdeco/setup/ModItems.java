package cn.dancingsnow.xkdeco.setup;

import cn.dancingsnow.xkdeco.XKDeco;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item HOLOGRAM_PLANET = addItem("hologram_planet", ModSettings.ITEM_FURNITURE);
    public static final Item HOLOGRAM_DNA = addItem("hologram_dna", ModSettings.ITEM_FURNITURE);
    public static final Item HOLOGRAM_PICTURES = addItem("hologram_pictures", ModSettings.ITEM_FURNITURE);
    public static final Item HOLOGRAM_MESSAGE = addItem("hologram_message", ModSettings.ITEM_FURNITURE);
    public static final Item HOLOGRAM_XEKR_LOGO = addItem("hologram_xekr_logo", ModSettings.ITEM_FURNITURE);

    private static Item addItem(String id, Item.Settings settings) {
        var item = new Item(settings);
        Registry.register(Registry.ITEM, new Identifier(XKDeco.MOD_ID, id), item);
        return item;
    }

    public static void registry() {
    }

}
