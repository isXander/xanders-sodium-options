package dev.isxander.xso.mixins;

import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//? if <1.21 {
/*import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;import me.jellysquid.mods.sodium.client.gui.options.OptionPage;*///?} else {
import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import org.spongepowered.asm.mixin.gen.Invoker;
//?}

import java.util.List;

@Mixin(SodiumOptionsGUI.class)
public interface SodiumOptionsGUIAccessor {
    @Accessor
    List<OptionPage> getPages();

    @Accessor
    Screen getPrevScreen();
}
