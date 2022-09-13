package dev.isxander.xso.utils;

public interface ClassCapture<T> {
    Class<T> getCapturedClass();

    void setCapturedClass(Class<T> capturedClass);
}
