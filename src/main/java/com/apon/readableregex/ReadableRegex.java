package com.apon.readableregex;

import com.apon.readableregex.internal.ReadableRegexImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to help construct readable regular expressions. The regular expressions are constructed using the Builder pattern.
 * <p>
 * The construction stays close to the way Java handles regular expression. Using this builder, you can create
 * {@link Pattern} objects, which creates {@link Matcher} objects. For additional functionality, the wrapper
 * {@link ReadableRegexPattern} can be created instead of {@link Pattern}.
 */
public interface ReadableRegex {
    /**
     * @return Instance of the builder.
     */
    static ReadableRegex regex() {
        return new ReadableRegexImpl();
    }

    /**
     * @return Compiled regular expression into {@link ReadableRegexPattern} object.
     */
    ReadableRegexPattern build();

    /**
     * @return Compiled regular expression into {@link Pattern} object.
     */
    default Pattern buildJdkPattern() {
        return build().getUnderlyingPattern();
    }

    /**
     * Appends the regex. The value is not changed or sanitized in any way. This should only be used as a last resort
     * when other methods cannot satisfy the expression you are looking for.
     * @param regex The regular expression.
     * @return This builder.
     */
    ReadableRegex regexFromString(String regex);

    /**
     * Appends a literal expression. All metacharacters are escaped.
     * @param literalValue The value to add.
     * @return This builder.
     */
    ReadableRegex literal(String literalValue);

    /**
     * Adds a digit. This is the same as {@code [0-9]}.
     * @return This builder.
     */
    ReadableRegex digit();

    /**
     * Adds a whitespace. This is the same as {@code \s}.
     * @return This builder.
     */
    ReadableRegex whitespace();
}
