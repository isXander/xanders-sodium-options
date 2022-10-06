package dev.isxander.xso.compat;

import dev.isxander.yacl.api.ButtonOption;
import dev.isxander.yacl.gui.controllers.ActionController;
import eu.pb4.entityviewdistance.modcompat.SodiumCompat;
import me.jellysquid.mods.sodium.client.gui.options.Option;

public class EntityViewDistanceCompat {
    public static boolean isFakeOption(Option<?> sodiumOption) {
        return sodiumOption instanceof SodiumCompat.FakeOptionImpl;
    }

    public static ButtonOption convertFakeOption(Option<?> sodiumOption) {
        SodiumCompat.FakeOptionImpl fakeOption = (SodiumCompat.FakeOptionImpl) sodiumOption;
        return ButtonOption.createBuilder()
                .name(sodiumOption.getName())
                .tooltip(sodiumOption.getTooltip())
                .controller(ActionController::new)
                .action(((yaclScreen, buttonOption) -> fakeOption.setValue(SodiumCompat.Void.VOID)))
                .build();
    }
}
