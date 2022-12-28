package dev.isxander.xso;

import dev.isxander.xso.config.XsoConfig;
import net.fabricmc.api.ClientModInitializer;

public class ModEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        XsoConfig.INSTANCE.load();
    }
}
