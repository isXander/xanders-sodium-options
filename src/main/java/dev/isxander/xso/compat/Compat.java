package dev.isxander.xso.compat;

import net.fabricmc.loader.api.FabricLoader;

public class Compat {
    public static final boolean SODIUM_EXTRA = FabricLoader.getInstance().isModLoaded("sodium-extra");
    public static final boolean MORE_CULLING = FabricLoader.getInstance().isModLoaded("moreculling");
    public static final boolean IRIS = FabricLoader.getInstance().isModLoaded("iris");
}
