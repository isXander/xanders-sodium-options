package dev.isxander.xso;

import dev.isxander.xso.compat.Compat;
import dev.isxander.xso.compat.IrisCompat;
import dev.isxander.xso.compat.MoreCullingCompat;
import dev.isxander.xso.compat.SodiumExtraCompat;
import dev.isxander.xso.mixins.SliderControlAccessor;
import dev.isxander.xso.utils.ClassCapture;
import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.EnumController;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TranslatableOption;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XandersSodiumOptions {
    public static Screen wrapSodiumScreen(SodiumOptionsGUI sodiumOptionsGUI, List<OptionPage> pages, Screen prevScreen) {
        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("Sodium Options"));

        for (OptionPage page : pages) {
            if (Compat.IRIS) {
                ConfigCategory shaderPackPage = IrisCompat.replaceShaderPackPage(sodiumOptionsGUI, page);
                if (shaderPackPage != null) {
                    builder.category(shaderPackPage);
                    continue;
                }
            }


            ConfigCategory.Builder categoryBuilder = ConfigCategory.createBuilder()
                    .name(page.getName());

            for (me.jellysquid.mods.sodium.client.gui.options.OptionGroup group : page.getGroups()) {
                OptionGroup.Builder groupBuilder = OptionGroup.createBuilder();

                for (me.jellysquid.mods.sodium.client.gui.options.Option<?> option : group.getOptions()) {
                    groupBuilder.option(convertOption(option));
                }

                categoryBuilder.group(groupBuilder.build());
            }

            builder.category(categoryBuilder.build());
        }

        builder.save(() -> {
            Set<OptionStorage<?>> storages = new HashSet<>();
            pages.stream().flatMap(s -> s.getOptions().stream()).forEach(opt -> storages.add(opt.getStorage()));
            storages.forEach(OptionStorage::save);
        });

        return builder.build().generateScreen(prevScreen);
    }

    private static <T> Option<T> convertOption(me.jellysquid.mods.sodium.client.gui.options.Option<T> sodiumOption) {
        Option.Builder<T> builder = Option.createBuilder(((ClassCapture<T>) sodiumOption).getCapturedClass())
                .name(sodiumOption.getName())
                .tooltip(sodiumOption.getTooltip())
                .flags(convertFlags(sodiumOption))
                .available(sodiumOption.isAvailable())
                .binding(Compat.MORE_CULLING ? MoreCullingCompat.getBinding(sodiumOption) : new SodiumBinding<>(sodiumOption))
                .available(sodiumOption.isAvailable());

        if (sodiumOption.getImpact() != null) {
            builder.tooltip(Text.translatable("sodium.options.performance_impact_string", sodiumOption.getImpact().getLocalizedName()).formatted(Formatting.GRAY));
        }

        genericBuilder(builder, sodiumOption);

        Option<T> built = builder.build();
        if (Compat.MORE_CULLING) MoreCullingCompat.addAvailableCheck(built, sodiumOption);
        return built;
    }

    // nasty, nasty raw types to make the compiler not commit die
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T> void genericBuilder(Option.Builder yaclOption, me.jellysquid.mods.sodium.client.gui.options.Option<T> sodiumOption) {
        if (sodiumOption.getControl() instanceof TickBoxControl) {
            yaclOption.controller(opt -> new TickBoxController((Option<Boolean>) opt));
            return;
        }

        if (sodiumOption.getControl() instanceof CyclingControl) {
            yaclOption.controller(opt -> new EnumController((Option) opt, value -> {
                if (value instanceof TextProvider textProvider)
                    return textProvider.getLocalizedName();
                if (value instanceof TranslatableOption translatableOption)
                    return translatableOption.getText();
                return Text.of(((Enum<?>) value).name());
            }));
            return;
        }

        if (sodiumOption.getControl() instanceof SliderControl sliderControl) {
            SliderControlAccessor accessor = (SliderControlAccessor) sliderControl;
            yaclOption.controller(opt -> new IntegerSliderController((Option<Integer>) opt, accessor.getMin(), accessor.getMax(), accessor.getInterval(), value -> Text.of(accessor.getMode().format(value))));
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
}
