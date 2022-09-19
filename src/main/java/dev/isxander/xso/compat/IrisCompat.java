package dev.isxander.xso.compat;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.PlaceholderCategory;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import net.coderbot.iris.gui.screen.ShaderPackScreen;

public class IrisCompat {
    public static ConfigCategory replaceShaderPackPage(SodiumOptionsGUI optionsGUI, OptionPage page) {
        ShaderPageHolder shaderPageHolder = (ShaderPageHolder) optionsGUI;
        if (shaderPageHolder.getShaderPage() == page) {
            return PlaceholderCategory.createBuilder()
                    .name(page.getName())
                    .screen((client, parent) -> new ShaderPackScreen(parent))
                    .build();
        }

        return null;
    }

    public interface ShaderPageHolder {
        OptionPage getShaderPage();
    }
}
