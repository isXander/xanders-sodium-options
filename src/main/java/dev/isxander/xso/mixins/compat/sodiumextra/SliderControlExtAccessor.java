package dev.isxander.xso.mixins.compat.sodiumextra;

import me.flashyreese.mods.sodiumextra.client.gui.options.control.SliderControlExtended;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(SliderControlExtended.class)
public interface SliderControlExtAccessor {
    @Accessor
    int getMin();

    @Accessor
    int getMax();

    @Accessor
    int getInterval();

    @Accessor
    ControlValueFormatter getMode();
}
