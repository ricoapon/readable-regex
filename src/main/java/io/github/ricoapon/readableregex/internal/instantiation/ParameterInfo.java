package io.github.ricoapon.readableregex.internal.instantiation;

/**
 * Container for information needed about parameters of a constructor.
 */
public class ParameterInfo {
    private final String name;
    private final Class<?> type;

    public ParameterInfo(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }
}
