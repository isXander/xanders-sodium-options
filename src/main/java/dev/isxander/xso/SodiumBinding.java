package dev.isxander.xso;

import dev.isxander.xso.mixins.OptionImplAccessor;
import dev.isxander.yacl.api.Binding;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.binding.OptionBinding;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

public class SodiumBinding<S, T> implements Binding<T> {
    private final OptionBinding<S, T> sodiumBinding;
    private final OptionStorage<S> sodiumStorage;

    public SodiumBinding(OptionImpl<S, T> sodiumOption) {
        this.sodiumBinding = ((OptionImplAccessor<S, T>) sodiumOption).getBinding();
        this.sodiumStorage = (OptionStorage<S>) sodiumOption.getStorage();
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
