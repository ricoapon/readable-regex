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
public interface ReadableRegex extends StandaloneBlockBuilder, QuantifierBuilder, FinishBuilder {
    /**
     * @return Instance of the builder.
     */
    static ReadableRegex regex() {
        return new ReadableRegexImpl();
    }
}
