package dev.isxander.xso.mixins.compat.iris;

import dev.isxander.xso.compat.IrisCompat;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(value = SodiumOptionsGUI.class, priority = 2000)
public class SodiumOptionsGUIMixin implements IrisCompat.ShaderPageHolder {
    private OptionPage shaderPacks;

    @Override
    public OptionPage getShaderPage() {
        return shaderPacks;
    }
}
