package dev.isxander.xso.compat;

import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.OptionDescription;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import net.irisshaders.iris.gui.screen.ShaderPackScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.Optional;

public class IrisCompat {
    public static Optional<ConfigCategory> replaceShaderPackPage(SodiumOptionsGUI optionsGUI, OptionPage page) {
        if (page.getName().contains(Text.translatable("options.iris.shaderPackSelection"))) {
            return Optional.of(ConfigCategory.createBuilder()
                    .name(page.getName())
                    .option(ButtonOption.createBuilder()
                            .name(page.getName())
                            .description(OptionDescription.EMPTY)
                            .text(ScreenTexts.PROCEED)
                            .action((e, a) -> MinecraftClient.getInstance().setScreen(new ShaderPackScreen(optionsGUI)))
                            .build())
                    .build());
        }

        return Optional.empty();
    }

    public interface ShaderPageHolder {
        OptionPage getShaderPage();
    }
}
