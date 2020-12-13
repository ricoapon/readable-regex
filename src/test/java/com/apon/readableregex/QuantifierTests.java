package com.apon.readableregex;

import org.junit.jupiter.api.Test;

import static com.apon.readableregex.Constants.DIGITS;
import static com.apon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

/**
 * Tests related to methods that are inside {@link QuantifierBuilder}.
 */
class QuantifierTests {
    @Test
    void oneOrMoreMatchesCorrectly() {
        ReadableRegexPattern pattern = ReadableRegex.regex().digit().oneOrMore().build();

        assertThat(pattern, matchesExactly(DIGITS));
        assertThat(pattern, not(matchesExactly("")));
    }
}
