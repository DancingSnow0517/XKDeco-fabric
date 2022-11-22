package cn.dancingsnow.xkdeco.setup;

import cn.dancingsnow.xkdeco.XKDeco;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> AXE_BLOCKS = mc("mineable/axe");
        public static final TagKey<Block> PICKAXE_BLOCKS = mc("mineable/pickaxe");
        public static final TagKey<Block> SHOVEL_BLOCKS = mc("mineable/shovel");
        public static final TagKey<Block> HOE_BLOCKS = mc("mineable/hoe");

        public static final TagKey<Block> STONE_TIRE = mc("needs_stone_tool");
        public static final TagKey<Block> IRON_TIRE = mc("needs_iron_tool");
        public static final TagKey<Block> DIAMOND_TIRE = mc("needs_diamond_tool");

        public static TagKey<Block> mod(String path) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier(XKDeco.MOD_ID, path));
        }

        public static TagKey<Block> mc(String path) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier("minecraft", path));
        }
    }

    public static class Items {
        public static TagKey<Item> mod(String path) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier(XKDeco.MOD_ID, path));
        }

        public static TagKey<Item> mc(String path) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("minecraft", path));
        }
    }

}
