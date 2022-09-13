package dev.isxander.xso.mixins;

import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(SodiumOptionsGUI.class)
public interface SodiumOptionsGUIAccessor {
    @Accessor
    List<OptionPage> getPages();

    @Accessor
    Screen getPrevScreen();
}
