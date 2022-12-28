package dev.isxander.xso.mixins.compat.moreculling;

import ca.fxco.moreculling.config.sodium.MoreCullingSodiumOptionImpl;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.isxander.xso.compat.MoreCullingCompat;
import dev.isxander.xso.utils.ClassCapture;
import dev.isxander.yacl.api.Option;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = MoreCullingSodiumOptionImpl.class, remap = false)
public abstract class MoreCullingOptionImplMixin<S, T> implements ClassCapture<T>, MoreCullingCompat.OptionHolder<T> {
    @Unique
    private Class<T> xso$capturedClass = null;

    @Unique
    private Option<T> xso$heldOption = null;

    @ModifyReturnValue(method = "createBuilder", at = @At("RETURN"))
    private static <S, T> MoreCullingSodiumOptionImpl.Builder<S, T> passClassToBuilder(MoreCullingSodiumOptionImpl.Builder<S, T> builder, Class<T> type, OptionStorage<S> storage) {
        ((ClassCapture<T>) builder).setCapturedClass(type);
        return builder;
    }

    @Inject(method = "setAvailable", at = @At("RETURN"))
    private void matchAvailability(boolean available, CallbackInfo ci) {
        if (xso$heldOption != null) xso$heldOption.setAvailable(available);
    }

    @Override
    public Class<T> getCapturedClass() {
        return xso$capturedClass;
    }

    @Override
    public void setCapturedClass(Class<T> capturedClass) {
        this.xso$capturedClass = capturedClass;
    }

    @Override
    public void holdOption(Option<T> option) {
        this.xso$heldOption = option;
    }
}
