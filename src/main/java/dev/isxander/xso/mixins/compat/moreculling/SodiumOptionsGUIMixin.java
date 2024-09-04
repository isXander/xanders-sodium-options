package dev.isxander.xso.mixins.compat.moreculling;

import dev.isxander.xso.compat.MoreCullingCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;

//? if <1.21 {
/*import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;import me.jellysquid.mods.sodium.client.gui.options.OptionPage;*///?} else {
import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
//?}

@Pseudo
@Mixin(value = SodiumOptionsGUI.class, priority = 2000)
public class SodiumOptionsGUIMixin implements MoreCullingCompat.MoreCullingPageHolder {
    @Unique
    private OptionPage moreCullingPage;

    @Override
    public OptionPage getMoreCullingPage() {
        return moreCullingPage;
    }
}
