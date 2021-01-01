package io.github.ricoapon.readableregex;

import io.github.ricoapon.readableregex.internal.MethodOrderChecker;
import io.github.ricoapon.readableregex.internal.ReadableRegexOrderChecker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to help construct readable regular expressions. The regular expressions are constructed using the Builder pattern.
 * <p>
 * The construction stays close to the way Java handles regular expression. Using this builder, you can create
 * {@link Pattern} objects, which creates {@link Matcher} objects. For additional functionality, the wrapper
 * {@link ReadableRegexPattern} can be created instead of {@link Pattern}.
 */
public interface ReadableRegex extends StandaloneBlockBuilder, QuantifierBuilder, FinishBuilder, GroupBuilder {
    /**
     * @return Instance of the builder.
     */
    static ReadableRegex regex() {
        return new ReadableRegexOrderChecker(new MethodOrderChecker());
    }

    /**
     * Syntactic sugar for {@code regex().regexFromString(regex)}.
     * @param regex The regular expression.
     * @return Instance of the builder initialized with the given regular expression.
     */
    static ReadableRegex regex(String regex) {
        return regex().regexFromString(regex);
    }
}
