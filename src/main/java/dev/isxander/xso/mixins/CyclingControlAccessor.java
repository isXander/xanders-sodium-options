package dev.isxander.xso.mixins;

import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CyclingControl.class)
public interface CyclingControlAccessor<T extends Enum<T>> {
    @Accessor
    T[] getAllowedValues();
}
