package dev.isxander.xso.compat;

import ca.fxco.moreculling.config.sodium.FloatSliderControl;
import ca.fxco.moreculling.config.sodium.IntSliderControl;
import ca.fxco.moreculling.config.sodium.MoreCullingOptionImpl;
import dev.isxander.xso.SodiumBinding;
import dev.isxander.xso.mixins.compat.moreculling.FloatSliderControlAccessor;
import dev.isxander.xso.mixins.compat.moreculling.IntSliderControlAccessor;
import dev.isxander.xso.mixins.compat.moreculling.MoreCullingOptionImplAccessor;
import dev.isxander.yacl.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

public class MoreCullingCompat {
    public static <S, T> SodiumBinding<S, T> getBinding(Option<T> option) {
        if (option instanceof MoreCullingOptionImpl<?, ?> moreCullingOption) {
            return new SodiumBinding<>(((MoreCullingOptionImplAccessor<S, T>) moreCullingOption).getBinding(), (OptionStorage<S>) moreCullingOption.getStorage());
        } else {
            return new SodiumBinding<>(option);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> void addAvailableCheck(dev.isxander.yacl.api.Option<T> yaclOption, Option<T> sodiumOption) {
        if (!(sodiumOption instanceof MoreCullingOptionImpl<?, ?>))
            return;

        ((OptionHolder<T>) sodiumOption).holdOption(yaclOption);
    }

        @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> boolean convertControl(dev.isxander.yacl.api.Option.Builder yaclOption, Option<T> sodiumOption) {
        if (sodiumOption.getControl() instanceof IntSliderControl sliderControl) {
            IntSliderControlAccessor accessor = (IntSliderControlAccessor) sliderControl;
            yaclOption.controller(opt -> new IntegerSliderController((dev.isxander.yacl.api.Option<Integer>) opt, accessor.getMin(), accessor.getMax(), accessor.getInterval()));
            return true;
        }

        if (sodiumOption.getControl() instanceof FloatSliderControl sliderControl) {
            FloatSliderControlAccessor accessor = (FloatSliderControlAccessor) sliderControl;
            yaclOption.controller(opt -> new FloatSliderController((dev.isxander.yacl.api.Option<Float>) opt, accessor.getMin(), accessor.getMax(), accessor.getInterval()));
            return true;
        }

        return false;
    }

    public interface OptionHolder<T> {
        void holdOption(dev.isxander.yacl.api.Option<T> option);
    }
}
