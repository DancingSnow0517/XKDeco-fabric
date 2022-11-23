package cn.dancingsnow.xkdeco.data;

import cn.dancingsnow.xkdeco.XKDeco;
import cn.dancingsnow.xkdeco.setup.ModItemGroups;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cn.dancingsnow.xkdeco.setup.ModBlocks.ROOF_FLAT_SUFFIX;

public class LangDataGenerator extends FabricLanguageProvider {
    protected LangDataGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "en_us");
    }

    private static final Collection<String> EXTRA_KEYS = List.of(
            "block.xkdeco.gilded_blackstone_pillar",
            "block.xkdeco.blue_roof",
            "block.xkdeco.blue_roof_ridge",
            "block.xkdeco.blue_roof_eave",
            "block.xkdeco.blue_roof_end",
            "block.xkdeco.blue_roof_small_eave",
            "block.xkdeco.blue_roof_small_end",
            "block.xkdeco.blue_roof_deco",
            "block.xkdeco.blue_roof_tip",
            "block.xkdeco.green_roof",
            "block.xkdeco.green_roof_ridge",
            "block.xkdeco.green_roof_eave",
            "block.xkdeco.green_roof_end",
            "block.xkdeco.green_roof_small_eave",
            "block.xkdeco.green_roof_small_end",
            "block.xkdeco.green_roof_deco",
            "block.xkdeco.green_roof_tip",
            "block.xkdeco.red_roof",
            "block.xkdeco.red_roof_ridge",
            "block.xkdeco.red_roof_eave",
            "block.xkdeco.red_roof_end",
            "block.xkdeco.red_roof_small_eave",
            "block.xkdeco.red_roof_small_end",
            "block.xkdeco.red_roof_deco",
            "block.xkdeco.red_roof_tip"
//            "block.xkdeco.ginkgo_leaves_shatter",
//            "block.xkdeco.orange_maple_leaves_shatter",
//            "block.xkdeco.red_maple_leaves_shatter",
//            "block.xkdeco.peach_blossom_shatter",
//            "block.xkdeco.cherry_blossom_shatter",
//            "block.xkdeco.white_cherry_blossom_shatter"
    );

    private static final Map<String, String> EXTRA_ENTRIES = Map.ofEntries(
            Map.entry("block.xkdeco.special_wall", "%s (Column)"),
            Map.entry("block.xkdeco.blue_roof_flat", "Blue Flat Roof"),
            Map.entry("block.xkdeco.green_roof_flat", "Green Flat Roof"),
            Map.entry("block.xkdeco.red_roof_flat", "Red Flat Roof")
    );

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        var blockIds = Registry.BLOCK.getIds();
        blockIds.stream().filter(id -> id.getNamespace().equals(XKDeco.MOD_ID)).forEach(id -> translateBlock(id, builder));

        var itemIds = Registry.ITEM.getIds();
        itemIds.stream().filter(id -> id.getNamespace().equals(XKDeco.MOD_ID)).forEach(id -> {
            var item = Registry.ITEM.get(id);
            if (!(item instanceof BlockItem)) {
                translateItem(id, builder);
            }
        });

        Stream.of(ModItemGroups.TAB_BASIC, ModItemGroups.TAB_FURNITURE, ModItemGroups.TAB_FUNCTIONAL,
                ModItemGroups.TAB_NATURE, ModItemGroups.TAB_STRUCTURE)
                .map(group -> ((TranslatableTextContent) group.getDisplayName().getContent()).getKey())
                .forEach(key -> translateCreativeTab(key, builder));

        EXTRA_KEYS.forEach(s -> translateKey(s, builder));
        EXTRA_ENTRIES.forEach(builder::add);
    }

    private void translateCreativeTab(String key, TranslationBuilder builder) {
        builder.add(key, "XKDeco: " + snakeToSpace(
                key.substring(key.lastIndexOf('.') + "xkdeco_".length() + 1)
        ));
    }

    private void translateBlock(Identifier identifier, TranslationBuilder builder) {
        var block = Registry.BLOCK.get(identifier);
        var id = identifier.getPath();
        id = id.replace(ROOF_FLAT_SUFFIX, "_flat_roof");
        var translation = snakeToSpace(id);
        builder.add(block, translation);
    }

    private void translateItem(Identifier identifier, TranslationBuilder builder) {
        var block = Registry.ITEM.get(identifier);
        var id = identifier.getPath();
        id = id.replace(ROOF_FLAT_SUFFIX, "_flat_roof");
        var translation = snakeToSpace(id);
        builder.add(block, translation);
    }

    // borrowed from mod uusi-aurinko
    private static String snakeToSpace(String str) {
        var chars = str.toCharArray();
        for (var i = 0; i < chars.length; i++) {
            var c = chars[i];
            if (c == '_') chars[i] = ' ';
            if ((i == 0 || chars[i - 1] == ' ') && c >= 'a' && c <= 'z') chars[i] -= 32;
        }
        return String.valueOf(chars);
    }

    private void translateKey(String key, TranslationBuilder builder) {
        builder.add(key, snakeToSpace(
                key.substring(key.lastIndexOf('.') + 1)
        ));
    }
}
