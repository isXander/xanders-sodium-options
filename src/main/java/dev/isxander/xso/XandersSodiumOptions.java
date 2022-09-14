package dev.isxander.xso;

import dev.isxander.xso.compat.Compat;
import dev.isxander.xso.compat.SodiumExtraCompat;
import dev.isxander.xso.mixins.SliderControlAccessor;
import dev.isxander.xso.utils.ClassCapture;
import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.EnumController;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XandersSodiumOptions {
    public static Screen wrapSodiumScreen(List<OptionPage> pages, Screen prevScreen) {
        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("Sodium Options"));

        for (OptionPage page : pages) {
            builder.category(ConfigCategory.createBuilder()
                    .name(page.getName())
                    .groups(page.getGroups().stream().map(group -> OptionGroup.createBuilder().options(group.getOptions().stream().map(opt -> convertOption((OptionImpl<?, ?>) opt)).collect(Collectors.toList())).build()).toList()).build());
        }

        return builder.build().generateScreen(prevScreen);
    }

    private static <S, T> Option<T> convertOption(me.jellysquid.mods.sodium.client.gui.options.OptionImpl<S, T> sodiumOption) {
        Option.Builder<T> builder = Option.createBuilder(((ClassCapture<T>) sodiumOption).getCapturedClass())
                .name(sodiumOption.getName())
                .tooltip(sodiumOption.getTooltip())
                .flags(convertFlags(sodiumOption))
                .binding(new SodiumBinding<>(sodiumOption));

        if (sodiumOption.getImpact() != null) {
            builder.tooltip(Text.translatable("sodium.options.performance_impact_string", sodiumOption.getImpact().getLocalizedName()).formatted(Formatting.GRAY));
        }

        convertControl(builder, sodiumOption);

        return builder.build();
    }

    // nasty, nasty raw types to make the compiler not commit die
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <S, T> void convertControl(Option.Builder yaclOption, OptionImpl<S, T> sodiumOption) {
        if (sodiumOption.getControl() instanceof TickBoxControl) {
            yaclOption.controller(opt -> new TickBoxController((Option<Boolean>) opt));
            return;
        }

        if (sodiumOption.getControl() instanceof CyclingControl) {
            yaclOption.controller(opt -> new EnumController((Option) opt));
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
