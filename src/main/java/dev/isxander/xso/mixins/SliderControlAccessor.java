package dev.isxander.xso.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//? if <1.21 {
/*import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;*///?} else {
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl;
//?}

@Mixin(SliderControl.class)
public interface SliderControlAccessor {
    @Accessor
    int getMin();

    @Accessor
    int getMax();

    @Accessor
    int getInterval();

    @Accessor
    ControlValueFormatter getMode();
}
