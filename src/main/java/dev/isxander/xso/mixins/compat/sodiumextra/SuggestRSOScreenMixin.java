package dev.isxander.xso.mixins.compat.sodiumextra;

import me.flashyreese.mods.sodiumextra.client.SodiumExtraClientMod;
import me.flashyreese.mods.sodiumextra.client.gui.SuggestRSOScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SuggestRSOScreen.class)
public class SuggestRSOScreenMixin extends Screen {
    @Shadow @Final private Screen prevScreen;

    protected SuggestRSOScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void avoidScreen(CallbackInfo ci) {
        if (!SodiumExtraClientMod.options().notificationSettings.hideRSORecommendation) {
            SodiumExtraClientMod.options().notificationSettings.hideRSORecommendation = true;
            SodiumExtraClientMod.options().writeChanges();
        }

        client.setScreen(prevScreen);
    }
}
