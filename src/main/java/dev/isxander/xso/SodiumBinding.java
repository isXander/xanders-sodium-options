package dev.isxander.xso;

import dev.isxander.xso.mixins.OptionImplAccessor;
import dev.isxander.yacl3.api.Binding;

//? if <1.21 {
/*import me.jellysquid.mods.sodium.client.gui.options.Option;import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;import me.jellysquid.mods.sodium.client.gui.options.binding.OptionBinding;import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;*///?} else {
import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.binding.OptionBinding;
import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;
//?}

public class SodiumBinding<S, T> implements Binding<T> {
    private final OptionBinding<S, T> sodiumBinding;
    private final OptionStorage<S> sodiumStorage;

    public SodiumBinding(Option<T> sodiumOption) {
        this(((OptionImplAccessor<S, T>) (OptionImpl<S, T>) sodiumOption).getBinding(), (OptionStorage<S>) sodiumOption.getStorage());
    }

    public SodiumBinding(OptionBinding<S, T> sodiumBinding, OptionStorage<S> sodiumStorage) {
        this.sodiumBinding = sodiumBinding;
        this.sodiumStorage = sodiumStorage;
    }

    @Override
    public void setValue(T value) {
        sodiumBinding.setValue(sodiumStorage.getData(), value);
    }

    @Override
    public T getValue() {
        return sodiumBinding.getValue(sodiumStorage.getData());
    }

    @Override
    public T defaultValue() {
        return getValue();
    }
}
