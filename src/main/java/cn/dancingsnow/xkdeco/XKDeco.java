package cn.dancingsnow.xkdeco;

import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import cn.dancingsnow.xkdeco.setup.ModBlocks;
import cn.dancingsnow.xkdeco.setup.ModItems;
import net.fabricmc.api.ModInitializer;

public class XKDeco implements ModInitializer {

    public static final String MOD_ID = "xkdeco";

    @Override
    public void onInitialize() {

        ModItems.registry();
        ModBlocks.registry();
        ModBlockEntities.registry();
//        ModBlocks.addSpecialWallBlocks();

    }
}
