package dev.isxander.xso.mixins.compat.moreculling;

import ca.fxco.moreculling.config.sodium.FloatSliderControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(FloatSliderControl.class)
public interface FloatSliderControlAccessor {
    @Accessor
    float getMin();

    @Accessor
    float getMax();

    @Accessor
    float getInterval();
}
