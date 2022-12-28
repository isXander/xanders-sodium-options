package dev.isxander.xso.mixins;

import dev.isxander.xso.XandersSodiumOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
    private Screen modifyScreen(Screen screen) {
        if (XandersSodiumOptions.shouldConvertGui() && screen instanceof SodiumOptionsGUI sodiumOptionsGUI) {
            var accessor = (SodiumOptionsGUIAccessor) sodiumOptionsGUI;
            return XandersSodiumOptions.wrapSodiumScreen(sodiumOptionsGUI, accessor.getPages(), accessor.getPrevScreen());
        }

        return screen;
    }
}
