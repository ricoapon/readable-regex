package io.github.ricoapon.readableregex;

import io.github.ricoapon.readableregex.internal.MethodOrderChecker;
import io.github.ricoapon.readableregex.internal.ReadableRegexOrderChecker;

/**
 * Interface which extends all the other interfaces for constructing readable regular expressions using the builder pattern.
 * <p>
 * Using this builder, you can create {@link ReadableRegexPattern} objects, which can be used to match the pattern against text.
 */
public interface ReadableRegex<T extends ReadableRegex<T>> extends SyntacticSugarBuilder<T>, StandaloneBlockBuilder<T>, QuantifierBuilder<T>, FinishBuilder, GroupBuilder<T> {
    /**
     * This method is the starting point for all the builder methods.
     * @return Instance of the builder.
     */
    static ReadableRegex<?> regex() {
        return new ReadableRegexOrderChecker<>(new MethodOrderChecker());
    }

    /**
     * Starts the builder initialized with a regular expression.
     * <p>
     * Syntactic sugar for "{@link #regex()}.{@link #regexFromString(String)}".
     * @param regex The regular expression.
     * @return Instance of the builder initialized with the given regular expression.
     */
    static ReadableRegex<?> regex(String regex) {
        return regex().regexFromString(regex);
    }
}
