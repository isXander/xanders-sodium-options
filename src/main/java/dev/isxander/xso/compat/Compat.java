package dev.isxander.xso.compat;

import net.fabricmc.loader.api.FabricLoader;

public class Compat {
    public static final boolean SODIUM_EXTRA = FabricLoader.getInstance().isModLoaded("sodium-extra");
}
