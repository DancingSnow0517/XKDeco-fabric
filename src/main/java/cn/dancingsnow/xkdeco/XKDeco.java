package cn.dancingsnow.xkdeco;

import cn.dancingsnow.xkdeco.entity.CushionEntity;
import cn.dancingsnow.xkdeco.setup.ModBlockEntities;
import cn.dancingsnow.xkdeco.setup.ModBlocks;
import cn.dancingsnow.xkdeco.setup.ModEntities;
import cn.dancingsnow.xkdeco.setup.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class XKDeco implements ModInitializer {

    public static final String MOD_ID = "xkdeco";

    @Override
    public void onInitialize() {

        ModBlocks.registry();
        ModItems.registry();
        ModBlockEntities.registry();
        ModEntities.registry();
//        ModBlocks.addSpecialWallBlocks();

        // Use Block Event
        UseBlockCallback.EVENT.register(CushionEntity::onRightClickBlock);

    }

}
