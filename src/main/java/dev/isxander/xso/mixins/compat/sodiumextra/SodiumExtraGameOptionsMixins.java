package dev.isxander.xso.mixins.compat.sodiumextra;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraGameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SodiumExtraGameOptions.class)
public class SodiumExtraGameOptionsMixins {
    @ModifyReturnValue(method = "hasSuggestedRSO", at = @At("RETURN"))
    private boolean dontShowRSOScreen(boolean hasSuggested) {
        return true;
    }
}
