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

    public static ArgumentType<String> STRING = new ArgumentType<>(String.class, s -> s, s -> s);
    public static ArgumentType<Integer> INT = new ArgumentType<>(Integer.class, Integer::parseInt, Object::toString);
    public static ArgumentType<Long> LONG = new ArgumentType<>(Long.class, Long::parseLong, Object::toString);
    public static ArgumentType<Double> DOUBLE = new ArgumentType<>(Double.class, Double::parseDouble, Object::toString);
    public static ArgumentType<Float> FLOAT = new ArgumentType<>(Float.class, Float::parseFloat, Object::toString);
    public static ArgumentType<Boolean> BOOLEAN = new ArgumentType<>(Boolean.class, Boolean::parseBoolean, Object::toString);
    public static ArgumentType<Byte> BYTE = new ArgumentType<>(Byte.class, Byte::parseByte, Object::toString);
    public static ArgumentType<Short> SHORT = new ArgumentType<>(Short.class, Short::parseShort, Object::toString);
}
