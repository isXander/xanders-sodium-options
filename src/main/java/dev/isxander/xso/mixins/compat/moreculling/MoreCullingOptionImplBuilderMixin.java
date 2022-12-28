package dev.isxander.xso.mixins.compat.moreculling;

import ca.fxco.moreculling.config.sodium.MoreCullingSodiumOptionImpl;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.isxander.xso.utils.ClassCapture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(value = MoreCullingSodiumOptionImpl.Builder.class, remap = false)
public class MoreCullingOptionImplBuilderMixin<S, T> implements ClassCapture<T> {
    @Unique
    private Class<T> xso$capturedClass = null;

    @ModifyReturnValue(method = "build", at = @At("RETURN"))
    private MoreCullingSodiumOptionImpl<S, T> captureBuiltClass(MoreCullingSodiumOptionImpl<S, T> result) {
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
