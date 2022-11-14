package cn.dancingsnow.xkdeco.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class XkdecoClient implements ClientModInitializer {

    public static final String MOD_ID = "xkdeco";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    @Override
    public void onInitializeClient() {

    }
}
