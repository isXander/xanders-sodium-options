package dev.isxander.xso.mixins.compat.moreculling;

import ca.fxco.moreculling.config.sodium.MoreCullingOptionImpl;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.isxander.xso.utils.ClassCapture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MoreCullingOptionImpl.Builder.class)
public class MoreCullingOptionImplBuilderMixin<S, T> implements ClassCapture<T> {
    @Unique
    private Class<T> xso$capturedClass = null;

    @ModifyReturnValue(method = "build", at = @At("RETURN"))
    private MoreCullingOptionImpl<S, T> captureBuiltClass(MoreCullingOptionImpl<S, T> result) {
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
