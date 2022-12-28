package dev.isxander.xso.mixins.compat.sodiumextra;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraGameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(value = SodiumExtraGameOptions.class, remap = false)
public class SodiumExtraGameOptionsMixins {
    @ModifyReturnValue(method = "hasSuggestedRSO", at = @At("RETURN"))
    private boolean dontShowRSOScreen(boolean hasSuggested) {
        return true;
    }
}
