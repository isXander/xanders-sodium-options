package dev.isxander.xso.config;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.config.ConfigEntry;
import dev.isxander.yacl.config.ConfigInstance;
import dev.isxander.yacl.config.GsonConfigInstance;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

public class XsoConfig {
    public static final ConfigInstance<XsoConfig> INSTANCE = new GsonConfigInstance<>(
            XsoConfig.class,
            FabricLoader.getInstance().getConfigDir().resolve("xanders-sodium-options.json")
    );

    @ConfigEntry public boolean enabled = true;
    @ConfigEntry public boolean lenientOptions = true;
    @ConfigEntry public boolean hardCrash = false;

    public static ConfigCategory getConfigCategory() {
        XsoConfig config = INSTANCE.getConfig();
        XsoConfig defaults = INSTANCE.getDefaults();

        return ConfigCategory.createBuilder()
                .name(Text.translatable("xso.title"))
                .option(Option.createBuilder(boolean.class)
                        .name(Text.translatable("xso.cfg.enabled"))
                        .tooltip(Text.translatable("xso.cfg.enabled.tooltip"))
                        .binding(defaults.enabled, () -> config.enabled, val -> config.enabled = val)
                        .controller(BooleanController::new)
                        .build())
                .option(Option.createBuilder(boolean.class)
                        .name(Text.translatable("xso.cfg.lenient_opts"))
                        .tooltip(Text.translatable("xso.cfg.lenient_opts.tooltip"))
                        .binding(defaults.lenientOptions, () -> config.lenientOptions, val -> config.lenientOptions = val)
                        .controller(opt -> new BooleanController(opt, BooleanController.YES_NO_FORMATTER, false))
                        .build())
                .option(Option.createBuilder(boolean.class)
                        .name(Text.translatable("xso.cfg.hard_crash"))
                        .tooltip(Text.translatable("xso.cfg.hard_crash.tooltip"))
                        .binding(defaults.hardCrash, () -> config.hardCrash, val -> config.hardCrash = val)
                        .controller(opt -> new BooleanController(opt, BooleanController.YES_NO_FORMATTER, false))
                        .build())
                .build();
    }
}
