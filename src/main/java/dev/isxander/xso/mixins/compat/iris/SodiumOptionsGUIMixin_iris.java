package dev.isxander.xso.mixins.compat.iris;

import dev.isxander.xso.compat.IrisCompat;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SodiumOptionsGUI.class)
public class SodiumOptionsGUIMixin_iris implements IrisCompat.ShaderPageHolder {
    private OptionPage shaderPacks;

    @Override
    public OptionPage getShaderPage() {
        return shaderPacks;
    }
}
