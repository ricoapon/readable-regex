package com.apon.readableregex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Wrapper of {@link Pattern} for additional useful methods.
 */
public interface ReadableRegexPattern {

    /**
     * Matches the regular expression to the text. See {@link Pattern#matcher(CharSequence)} for more information.
     * @param text The text to be matched.
     * @return {@link Matcher}
     */
    Matcher matches(String text);

    /**
     * @return The wrapped {@link Pattern} object.
     */
    Pattern getUnderlyingPattern();
}
