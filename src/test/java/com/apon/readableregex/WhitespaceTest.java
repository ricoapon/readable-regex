package com.apon.readableregex;

import org.junit.jupiter.api.Test;

import static com.apon.readableregex.Constants.*;
import static com.apon.readableregex.Constants.WHITESPACES;
import static com.apon.readableregex.matchers.PatternMatchMatcher.doesntMatch;
import static com.apon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static org.hamcrest.MatcherAssert.assertThat;

public class WhitespaceTest {
    @Test
    void digitOnlyMatchesDigits() {
        ReadableRegexPattern pattern = ReadableRegex.regex().whitespace().build();

        assertThat(pattern, matchesExactly(WHITESPACES.substring(0, 1)));
        assertThat(pattern, doesntMatch(WORD_CHARACTERS));
        assertThat(pattern, doesntMatch(NON_LETTERS));
        assertThat(pattern, doesntMatch(DIGITS));
    }

}
