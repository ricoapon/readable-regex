package io.github.ricoapon.readableregex.internal.instantiation;

import io.github.ricoapon.readableregex.RegexObjectInstantiationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class with methods to help convert {@link String}s to any other type.
 */
public class StringConverter {
    private static final Map<Class<?>, Function<String, ?>> CONVERTER_MAP;

    static {
        Map<Class<?>, Function<String, ?>> map = new HashMap<>();
        CONVERTER_MAP = Collections.unmodifiableMap(map);

        map.put(Byte.class, Byte::valueOf);
        map.put(byte.class, Byte::parseByte);
        map.put(Short.class, Short::valueOf);
        map.put(short.class, Short::parseShort);
        map.put(Integer.class, Integer::valueOf);
        map.put(int.class, Integer::parseInt);
        map.put(Long.class, Long::valueOf);
        map.put(long.class, Long::parseLong);
        map.put(Float.class, Float::valueOf);
        map.put(float.class, Float::parseFloat);
        map.put(Double.class, Double::valueOf);
        map.put(double.class, Double::parseDouble);
        map.put(Boolean.class, Boolean::valueOf);
        map.put(boolean.class, Boolean::parseBoolean);
        map.put(Character.class, (s) -> s.charAt(0));
        map.put(char.class, (s) -> s.charAt(0));
        map.put(String.class, Function.identity());
    }

    /**
     * Converts a {@link String} to an object of a specified class.
     * @param clazz The class of the object to convert to.
     * @param s The {@link String} to convert.
     * @param <T> The type of the object to convert to.
     * @return The converted object.
     */
    public static <T> T convertStringTo(Class<T> clazz, String s) {
        if (!CONVERTER_MAP.containsKey(clazz)) {
            throw new RegexObjectInstantiationException("Injecting an object of class " + clazz.getName() + " is not supported.");
        }

        //noinspection unchecked
        return (T) CONVERTER_MAP.get(clazz).apply(s);
    }
}
