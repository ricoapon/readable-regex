package io.github.ricoapon.readableregex;

import io.github.ricoapon.readableregex.internal.instantiation.RegexObjectInstantiationImpl;

/**
 * Class with methods that are used to instantiate objects based on regular expressions.
 */
public interface RegexObjectInstantiation {
    /**
     * Creates a new instance of an object of the given class. The following criteria must hold to make this work:
     * <ul>
     *     <li>If there is more than one constructor, exactly one constructor must be annotated with {@link javax.inject.Inject}.</li>
     *     <li>There must exist a named group inside the pattern for each name of the constructor parameter.</li>
     *     <li>All constructor parameters must be a primitive type, a boxed primitive type or {@link String}.</li>
     * </ul>
     * @param pattern The regular expression.
     * @param data    The {@link String} containing the information in the format as defined in the {@code pattern}.
     * @param clazz   The class of the object to instantiate.
     * @param <T>     The type of  the object to instantiate.
     * @return Instance of {@link T}.
     */
    static <T> T instantiateObject(ReadableRegexPattern pattern, String data, Class<T> clazz) {
        return new RegexObjectInstantiationImpl<T>().constructObject(pattern, data, clazz);
    }
}
