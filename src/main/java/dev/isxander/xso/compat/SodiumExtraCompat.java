package dev.isxander.xso.compat;

import dev.isxander.xso.mixins.compat.sodiumextra.SliderControlExtAccessor;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import me.flashyreese.mods.sodiumextra.client.gui.options.control.SliderControlExtended;
import net.minecraft.text.Text;

public class SodiumExtraCompat {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> boolean convertControl(Option.Builder yaclOption, me.jellysquid.mods.sodium.client.gui.options.Option<T> sodiumOption) {
        if (sodiumOption.getControl() instanceof SliderControlExtended sliderControl) {
            SliderControlExtAccessor accessor = (SliderControlExtAccessor) sliderControl;
            yaclOption.controller(opt -> new IntegerSliderController((Option<Integer>) opt, accessor.getMin(), accessor.getMax(), accessor.getInterval(), value -> Text.of(accessor.getMode().format(value))));
            return true;
        }

        return false;
    }
}
