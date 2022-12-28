package dev.isxander.xso.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.isxander.xso.utils.ClassCapture;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = OptionImpl.class, remap = false)
public class OptionImplMixin<S, T> implements ClassCapture<T> {
    @Unique
    private Class<T> xso$capturedClass = null;

    @ModifyReturnValue(method = "createBuilder", at = @At("RETURN"))
    private static <S, T> OptionImpl.Builder<S, T> passClassToBuilder(OptionImpl.Builder<S, T> builder, Class<T> type, OptionStorage<S> storage) {
        ((ClassCapture<T>) builder).setCapturedClass(type);
        return builder;
    }

    @Override
    public Class<T> getCapturedClass() {
        return xso$capturedClass;
    }

    @Override
    public void setCapturedClass(Class<T> capturedClass) {
        this.xso$capturedClass = capturedClass;
    }
}
