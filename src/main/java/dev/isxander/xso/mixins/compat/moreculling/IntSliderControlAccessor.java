package dev.isxander.xso.mixins.compat.moreculling;

import ca.fxco.moreculling.config.sodium.IntSliderControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(IntSliderControl.class)
public interface IntSliderControlAccessor {
    @Accessor
    int getMin();

    @Accessor
    int getMax();

    @Accessor
    int getInterval();
}
