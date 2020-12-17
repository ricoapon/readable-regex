package com.apon.readableregex;

import java.util.regex.Pattern;

/**
 * Builder interface with all the methods that finish building the expression.
 */
public interface FinishBuilder {
    /**
     * @return Compiled regular expression into {@link ReadableRegexPattern} object.
     */
    default ReadableRegexPattern build() {
        return buildWithFlags();
    }

    /**
     * @param patternFlags The flags that are enabled for the regular expression.
     * @return Compiled regular expression into {@link ReadableRegexPattern} object.
     */
    ReadableRegexPattern buildWithFlags(PatternFlag... patternFlags);

    /**
     * @return Compiled regular expression into {@link Pattern} object.
     */
    default Pattern buildJdkPattern() {
        return build().getUnderlyingPattern();
    }
}
