package dev.isxander.xso.compat;

import ca.fxco.moreculling.config.sodium.FloatSliderControl;
import ca.fxco.moreculling.config.sodium.IntSliderControl;
import ca.fxco.moreculling.config.sodium.MoreCullingSodiumOptionImpl;
import ca.fxco.moreculling.utils.CacheUtils;
import dev.isxander.xso.SodiumBinding;
import dev.isxander.xso.mixins.compat.moreculling.FloatSliderControlAccessor;
import dev.isxander.xso.mixins.compat.moreculling.IntSliderControlAccessor;
import dev.isxander.xso.mixins.compat.moreculling.MoreCullingSodiumOptionImplAccessor;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;

//? if <1.21 {
/*import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;import me.jellysquid.mods.sodium.client.gui.options.Option;import me.jellysquid.mods.sodium.client.gui.options.OptionPage;import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;*///?} else {
import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;
//?}

import net.minecraft.text.Text;

public class MoreCullingCompat {
    public static <S, T> SodiumBinding<S, T> getBinding(Option<T> option) {
        if (option instanceof MoreCullingSodiumOptionImpl<?, ?> moreCullingOption) {
            return new SodiumBinding<>(((MoreCullingSodiumOptionImplAccessor<S, T>) moreCullingOption).getBinding(), (OptionStorage<S>) moreCullingOption.getStorage());
        } else {
            return new SodiumBinding<>(option);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> void addAvailableCheck(dev.isxander.yacl3.api.Option<T> yaclOption, Option<T> sodiumOption) {
        if (!(sodiumOption instanceof MoreCullingSodiumOptionImpl<?,?>))
            return;

        ((OptionHolder<T>) sodiumOption).holdOption(yaclOption);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> boolean convertControl(dev.isxander.yacl3.api.Option.Builder<T> yaclOption, Option<T> sodiumOption) {
        if (sodiumOption.getControl() instanceof IntSliderControl sliderControl) {
            IntSliderControlAccessor accessor = (IntSliderControlAccessor) sliderControl;
            yaclOption.controller(opt -> (dev.isxander.yacl3.api.controller.ControllerBuilder<T>) IntegerSliderControllerBuilder.create((dev.isxander.yacl3.api.Option<Integer>) opt).range(accessor.getMin(), accessor.getMax()).step(accessor.getInterval()));
            return true;
        }

        if (sodiumOption.getControl() instanceof FloatSliderControl sliderControl) {
            FloatSliderControlAccessor accessor = (FloatSliderControlAccessor) sliderControl;
            yaclOption.controller(opt -> (dev.isxander.yacl3.api.controller.ControllerBuilder<T>) FloatSliderControllerBuilder.create((dev.isxander.yacl3.api.Option<Float>) opt).range(accessor.getMin(), accessor.getMax()).step(accessor.getInterval()));
            return true;
        }

        return false;
    }

    public static void extendMoreCullingPage(SodiumOptionsGUI optionsGUI, OptionPage page, ConfigCategory.Builder builder) {
        MoreCullingPageHolder shaderPageHolder = (MoreCullingPageHolder) optionsGUI;
        if (shaderPageHolder.getMoreCullingPage() == page) {
            builder.option(dev.isxander.yacl3.api.ButtonOption.createBuilder()
                    .name(Text.translatable("moreculling.config.resetCache"))
                    .action((screen, button) -> CacheUtils.resetAllCache())
                    .build());
        }
    }

    public interface OptionHolder<T> {
        void holdOption(dev.isxander.yacl3.api.Option<T> option);
    }

    public interface MoreCullingPageHolder {
        OptionPage getMoreCullingPage();
    }
}
