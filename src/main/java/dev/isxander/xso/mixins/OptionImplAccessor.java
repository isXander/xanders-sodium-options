package dev.isxander.xso.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//? if <1.21 {
/*import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;import me.jellysquid.mods.sodium.client.gui.options.binding.OptionBinding;*///?} else {
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.binding.OptionBinding;
//?}

@Mixin(OptionImpl.class)
public interface OptionImplAccessor<S, T> {
    @Accessor
    OptionBinding<S, T> getBinding();
}
