package dev.isxander.xso.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.ConfigInstance;
import dev.isxander.yacl3.config.GsonConfigInstance;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

public class XsoConfig {
    public static final ConfigInstance<XsoConfig> INSTANCE = new GsonConfigInstance<>(
            XsoConfig.class,
            FabricLoader.getInstance().getConfigDir().resolve("xanders-sodium-options.json")
    );

    @SerialEntry
    public boolean enabled = true;
    @SerialEntry public boolean lenientOptions = true;
    @SerialEntry public boolean hardCrash = false;

    public static ConfigCategory getConfigCategory() {
        XsoConfig config = INSTANCE.getConfig();
        XsoConfig defaults = INSTANCE.getDefaults();

        return ConfigCategory.createBuilder()
                .name(Text.translatable("xso.title"))
                .option(Option.createBuilder(boolean.class)
                        .name(Text.translatable("xso.cfg.enabled"))
                        .description(OptionDescription.of(Text.translatable("xso.cfg.enabled.tooltip")))
                        .binding(defaults.enabled, () -> config.enabled, val -> config.enabled = val)
                        .controller(BooleanControllerBuilder::create)
                        .build())
                .option(Option.createBuilder(boolean.class)
                        .name(Text.translatable("xso.cfg.lenient_opts"))
                        .description(OptionDescription.of(Text.translatable("xso.cfg.lenient_opts.tooltip")))
                        .binding(defaults.lenientOptions, () -> config.lenientOptions, val -> config.lenientOptions = val)
                        .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(false))
                        .build())
                .option(Option.createBuilder(boolean.class)
                        .name(Text.translatable("xso.cfg.hard_crash"))
                        .description(OptionDescription.of(Text.translatable("xso.cfg.hard_crash.tooltip")))
                        .binding(defaults.hardCrash, () -> config.hardCrash, val -> config.hardCrash = val)
                        .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(false))
                        .build())
                .build();
    }
}
