package io.github.ricoapon.readableregex;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Wrapper of {@link Pattern} with some extra useful methods.
 * <p>
 * If you want to use methods from {@link Pattern} that are missing in this interface, you can use {@link #getUnderlyingPattern()}
 * to get the {@link Pattern} object.
 */
public interface ReadableRegexPattern {
    /**
     * Matches the regular expression to the text. See {@link Pattern#matcher(CharSequence)} for more information.
     * @param text The text to be matched.
     * @return {@link Matcher}
     */
    Matcher matches(String text);

    /**
     * @param text The text to be matched.
     * @return {@code true} if the pattern matches the full text, else {@code false}.
     */
    default boolean matchesTextExactly(String text) {
        return matches(text).matches();
    }

    /**
     * @return All the {@link PatternFlag}s that are enabled on this pattern.
     */
    Set<PatternFlag> enabledFlags();

    /**
     * @return The wrapped {@link Pattern} object.
     */
    Pattern getUnderlyingPattern();
}
