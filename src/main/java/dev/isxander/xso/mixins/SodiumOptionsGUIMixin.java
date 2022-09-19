package dev.isxander.xso.mixins;

import dev.isxander.xso.XandersSodiumOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SodiumOptionsGUI.class)
public abstract class SodiumOptionsGUIMixin extends Screen {
    @Shadow @Final private List<OptionPage> pages;

    @Shadow @Final private Screen prevScreen;

    protected SodiumOptionsGUIMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void redirectToYACL(CallbackInfo ci) {
        client.setScreen(XandersSodiumOptions.wrapSodiumScreen((SodiumOptionsGUI) (Object) this, pages, prevScreen));
    }
}
