package dev.isxander.xso.mixins;

import dev.isxander.xso.XandersSodiumOptions;

import dev.isxander.xso.utils.DonationPrompt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

//? if <1.21 {
/*import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.data.fingerprint.HashedFingerprint;
import me.jellysquid.mods.sodium.client.SodiumClientMod;*///?} else {
import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.data.fingerprint.HashedFingerprint;
//?}

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
    private Screen modifyScreen(Screen screen) {
        if (XandersSodiumOptions.shouldConvertGui() && screen instanceof SodiumOptionsGUI sodiumOptionsGUI) {
            var accessor = (SodiumOptionsGUIAccessor) sodiumOptionsGUI;
            var target = XandersSodiumOptions.wrapSodiumScreen(sodiumOptionsGUI, accessor.getPages(), accessor.getPrevScreen());

            return target;
        }

        return screen;
    }
}
