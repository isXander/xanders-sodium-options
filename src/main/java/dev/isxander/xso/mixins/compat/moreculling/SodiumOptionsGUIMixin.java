package dev.isxander.xso.mixins.compat.moreculling;

import dev.isxander.xso.compat.MoreCullingCompat;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(value = SodiumOptionsGUI.class, priority = 2000)
public class SodiumOptionsGUIMixin implements MoreCullingCompat.MoreCullingPageHolder {
    private OptionPage moreCullingPage;

    @Override
    public OptionPage getMoreCullingPage() {
        return moreCullingPage;
    }
}
