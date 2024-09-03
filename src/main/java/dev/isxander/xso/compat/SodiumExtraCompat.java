package dev.isxander.xso.compat;

import dev.isxander.xso.mixins.compat.sodiumextra.SliderControlExtAccessor;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import me.flashyreese.mods.sodiumextra.client.gui.options.control.SliderControlExtended;

public class SodiumExtraCompat {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> boolean convertControl(Option.Builder yaclOption, me.jellysquid.mods.sodium.client.gui.options.Option<T> sodiumOption) {
        if (sodiumOption.getControl() instanceof SliderControlExtended sliderControl) {
            SliderControlExtAccessor accessor = (SliderControlExtAccessor) sliderControl;
            yaclOption.controller(opt -> IntegerSliderControllerBuilder.create((Option<Integer>) opt).range(accessor.getMin(), accessor.getMax()).step(accessor.getInterval()).formatValue(value -> accessor.getMode().format(value)));
            return true;
        }

        return false;
    }
}
