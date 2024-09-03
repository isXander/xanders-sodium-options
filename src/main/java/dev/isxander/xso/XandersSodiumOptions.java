package dev.isxander.xso;

import dev.isxander.xso.compat.*;
import dev.isxander.xso.config.XsoConfig;
import dev.isxander.xso.mixins.CyclingControlAccessor;
import dev.isxander.xso.mixins.SliderControlAccessor;
import dev.isxander.xso.utils.ClassCapture;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.impl.controller.EnumControllerBuilderImpl;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.NoticeScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TranslatableOption;

import java.util.*;

public class XandersSodiumOptions {
    private static boolean errorOccured = false;

    public static Screen wrapSodiumScreen(SodiumOptionsGUI sodiumOptionsGUI, List<OptionPage> pages, Screen prevScreen) {
        try {
            YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                    .title(Text.translatable("Sodium Options"));

            for (OptionPage page : pages) {
                builder.category(convertCategory(page, sodiumOptionsGUI));
            }

            builder.category(XsoConfig.getConfigCategory());

            builder.save(() -> {
                Set<OptionStorage<?>> storages = new HashSet<>();
                pages.stream().flatMap(s -> s.getOptions().stream()).forEach(opt -> storages.add(opt.getStorage()));
                storages.forEach(OptionStorage::save);

                XsoConfig.INSTANCE.save();
            });

            return builder.build().generateScreen(prevScreen);
        } catch (Exception e) {
            var exception = new IllegalStateException("Failed to convert Sodium option screen to YACL with XSO!", e);

            if (XsoConfig.INSTANCE.getConfig().hardCrash) {
                throw exception;
            } else {
                exception.printStackTrace();

                return new NoticeScreen(() -> {
                    errorOccured = true;
                    MinecraftClient.getInstance().setScreen(sodiumOptionsGUI);
                    errorOccured = false;
                }, Text.literal("Xander's Sodium Options failed"), Text.literal("Whilst trying to convert Sodium's GUI to YACL with XSO mod, an error occured which prevented the conversion. This is most likely due to a third-party mod adding its own settings to Sodium's screen. XSO will now display the original GUI.\n\nThe error has been logged to latest.log file."), ScreenTexts.PROCEED, true);
            }
        }
    }

    private static ConfigCategory convertCategory(OptionPage page, SodiumOptionsGUI sodiumOptionsGUI) {
        try {
            if (Compat.IRIS) {
                Optional<ConfigCategory> shaderPackPage = IrisCompat.replaceShaderPackPage(sodiumOptionsGUI, page);
                if (shaderPackPage.isPresent()) {
                    return shaderPackPage.get();
                }
            }

            ConfigCategory.Builder categoryBuilder = ConfigCategory.createBuilder()
                    .name(page.getName());

            for (var group : page.getGroups()) {
                categoryBuilder.option(LabelOption.create(Text.empty()));

                for (var option : group.getOptions()) {
                    categoryBuilder.option(convertOption(option));
                }
            }

            if (Compat.MORE_CULLING)
                MoreCullingCompat.extendMoreCullingPage(sodiumOptionsGUI, page, categoryBuilder);

            return categoryBuilder.build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to convert Sodium option page named '" + page.getName().getString() + "' to YACL config category.", e);
        }
    }

    private static <T> Option<?> convertOption(me.jellysquid.mods.sodium.client.gui.options.Option<T> sodiumOption) {
        try {
            if (sodiumOption.getName().contains(Text.of("Fullscreen Resolution"))) {
                // Halt debugger so i can step by step
                System.out.println("debug");
            }

            if (Compat.ENTITY_VIEW_DIST) {
                Optional<Option<?>> fakeOption = EntityViewDistanceCompat.convertFakeOption(sodiumOption);
                if (fakeOption.isPresent()) return fakeOption.get();
            }

            if (!(sodiumOption instanceof ClassCapture<?>)) {
                throw new IllegalStateException("Failed to capture class of sodium option! Likely due to custom Option implementation.");
            }

            MutableText descText = sodiumOption.getTooltip().copy();

            Option.Builder<T> builder = Option.createBuilder(((ClassCapture<T>) sodiumOption).getCapturedClass())
                    .name(sodiumOption.getName())
                    .flags(convertFlags(sodiumOption))
                    .binding(Compat.MORE_CULLING ? MoreCullingCompat.getBinding(sodiumOption) : new SodiumBinding<>(sodiumOption))
                    .available(sodiumOption.isAvailable());

            if (sodiumOption.getImpact() != null) {
                descText = descText.append("\n").append(Text.translatable("sodium.options.performance_impact_string", sodiumOption.getImpact().getLocalizedName()).formatted(Formatting.GRAY));
            }

            builder.description(OptionDescription.of(descText));

            addController(builder, sodiumOption);

            Option<T> built = builder.build();
            if (Compat.MORE_CULLING) MoreCullingCompat.addAvailableCheck(built, sodiumOption);
            return built;
        } catch (Exception e) {
            if (XsoConfig.INSTANCE.getConfig().lenientOptions) {
                System.out.println("Failed: " + sodiumOption.getName().getString());
                e.printStackTrace();
                return ButtonOption.createBuilder()
                        .name(sodiumOption.getName())
                        .description(OptionDescription.of(sodiumOption.getTooltip(), Text.translatable("xso.incompatible.tooltip").formatted(Formatting.RED)))
                        .available(false)
                        .text(Text.translatable("xso.incompatible.button").formatted(Formatting.RED))
                        .action((screen, opt) -> {})
                        .build();
            } else {
                throw new IllegalStateException("Failed to convert Sodium option named '" + sodiumOption.getName().getString() + "' to a YACL option!", e);
            }
        }
    }

    // nasty, nasty raw types to make the compiler not commit die
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T> void addController(Option.Builder yaclOption, me.jellysquid.mods.sodium.client.gui.options.Option<T> sodiumOption) {
        if (sodiumOption.getControl() instanceof TickBoxControl) {
            yaclOption.controller(opt -> TickBoxControllerBuilder.create((Option<Boolean>) opt));
            return;
        }

        if (sodiumOption.getControl() instanceof CyclingControl cyclingControl) {
            var allowedValues = ((CyclingControlAccessor<?>) cyclingControl).getAllowedValues();

            Class<?> arrType = allowedValues.getClass().getComponentType();

            yaclOption.controller(opt -> new EnumControllerBuilderImpl<>((Option) opt).formatValue(value -> {
                    if (value instanceof TextProvider textProvider)
                        return textProvider.getLocalizedName();
                    if (value instanceof TranslatableOption translatableOption)
                        return translatableOption.getText();
                    return Text.of(((Enum<?>) value).name());
            }).enumClass(arrType));
            return;
        }

        if (sodiumOption.getControl() instanceof SliderControl sliderControl) {
            SliderControlAccessor accessor = (SliderControlAccessor) sliderControl;
            yaclOption.controller(opt -> IntegerSliderControllerBuilder.create((Option<Integer>) opt).step(accessor.getInterval()).range(accessor.getMin(), accessor.getMax()).formatValue(value -> accessor.getMode().format(value)));
            return;
        }

        if (Compat.SODIUM_EXTRA && SodiumExtraCompat.convertControl(yaclOption, sodiumOption)) {
            return;
        }

        if (Compat.MORE_CULLING && MoreCullingCompat.convertControl(yaclOption, sodiumOption)) {
            return;
        }

        throw new IllegalStateException("Unsupported Sodium Controller: " + sodiumOption.getControl().getClass().getName());
    }

    private static List<OptionFlag> convertFlags(me.jellysquid.mods.sodium.client.gui.options.Option<?> sodiumOption) {
        List<OptionFlag> flags = new ArrayList<>();
        if (sodiumOption.getFlags().contains(me.jellysquid.mods.sodium.client.gui.options.OptionFlag.REQUIRES_RENDERER_RELOAD)) {
            flags.add(OptionFlag.RELOAD_CHUNKS);
        } else if (sodiumOption.getFlags().contains(me.jellysquid.mods.sodium.client.gui.options.OptionFlag.REQUIRES_RENDERER_UPDATE)) {
            flags.add(OptionFlag.WORLD_RENDER_UPDATE);
        }

        if (sodiumOption.getFlags().contains(me.jellysquid.mods.sodium.client.gui.options.OptionFlag.REQUIRES_ASSET_RELOAD)) {
            flags.add(OptionFlag.ASSET_RELOAD);
        }

        if (sodiumOption.getFlags().contains(me.jellysquid.mods.sodium.client.gui.options.OptionFlag.REQUIRES_GAME_RESTART)) {
            flags.add(OptionFlag.GAME_RESTART);
        }

        return flags;
    }

    public static boolean shouldConvertGui() {
        return XsoConfig.INSTANCE.getConfig().enabled && !errorOccured;
    }
}
