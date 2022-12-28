package dev.isxander.xso.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.isxander.xso.utils.ClassCapture;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = OptionImpl.Builder.class, remap = false)
public class OptionImplBuilderMixin<S, T> implements ClassCapture<T> {
    @Unique
    private Class<T> xso$capturedClass = null;

    @ModifyReturnValue(method = "build", at = @At("RETURN"))
    private OptionImpl<S, T> captureBuiltClass(OptionImpl<S, T> result) {
        ((ClassCapture<T>) result).setCapturedClass(getCapturedClass());
        return result;
    }

    @Override
    public Class<T> getCapturedClass() {
        return xso$capturedClass;
    }

    @Override
    public void setCapturedClass(Class<T> capturedClass) {
        xso$capturedClass = capturedClass;
    }
}
