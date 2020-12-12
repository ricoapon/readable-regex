package com.apon.readableregex;

import org.junit.jupiter.api.Test;

import static com.apon.readableregex.Constants.*;
import static com.apon.readableregex.matchers.PatternMatchMatcher.doesntMatch;
import static com.apon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests related to {@link ReadableRegex#digit()}.
 */
public class DigitTest {
    @Test
    void digitOnlyMatchesDigits() {
        ReadableRegexPattern pattern = ReadableRegex.regex().digit().build();

        assertThat(pattern, matchesExactly(DIGITS.substring(0, 1)));
        assertThat(pattern, doesntMatch(WORD_CHARACTERS));
        assertThat(pattern, doesntMatch(NON_LETTERS));
        assertThat(pattern, doesntMatch(WHITESPACES));
    }
}
