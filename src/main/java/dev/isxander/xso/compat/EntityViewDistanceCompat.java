package dev.isxander.xso.compat;

import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.OptionDescription;
import eu.pb4.entityviewdistance.modcompat.SodiumCompat;
import me.jellysquid.mods.sodium.client.gui.options.Option;

import java.util.Optional;

public class EntityViewDistanceCompat {
    public static Optional<dev.isxander.yacl3.api.Option<?>> convertFakeOption(Option<?> sodiumOption) {
        if (sodiumOption instanceof SodiumCompat.FakeOptionImpl fakeOption) {
            return Optional.of(ButtonOption.createBuilder()
                    .name(sodiumOption.getName())
                    .description(OptionDescription.of(sodiumOption.getTooltip()))
                    .action(((yaclScreen, buttonOption) -> fakeOption.setValue(SodiumCompat.Void.VOID)))
                    .build());
        }

        return Optional.empty();
    }
}
