package dev.isxander.xso.compat;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.PlaceholderCategory;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import net.coderbot.iris.gui.screen.ShaderPackScreen;

import java.util.Optional;

public class IrisCompat {
    public static Optional<ConfigCategory> replaceShaderPackPage(SodiumOptionsGUI optionsGUI, OptionPage page) {
        ShaderPageHolder shaderPageHolder = (ShaderPageHolder) optionsGUI;
        if (shaderPageHolder.getShaderPage() == page) {
            return Optional.of(PlaceholderCategory.createBuilder()
                    .name(page.getName())
                    .screen((client, parent) -> new ShaderPackScreen(parent))
                    .build());
        }

        return Optional.empty();
    }

    public interface ShaderPageHolder {
        OptionPage getShaderPage();
    }
}
