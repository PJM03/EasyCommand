package com.github.pjm03.easycommand.typefixed.argument;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class ArgumentType<T> {
    private final Class<T> type;
    private final Function<String, T> toValue;
    private final Function<T, String> fromValue;

    public T stringToValue(String string) {
        return toValue.apply(string);
    }

    public String valueToString(T value) {
        return fromValue.apply(value);
    }
}
