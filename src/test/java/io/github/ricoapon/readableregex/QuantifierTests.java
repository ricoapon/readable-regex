package io.github.ricoapon.readableregex;

import org.junit.jupiter.api.Test;

import static io.github.ricoapon.readableregex.Constants.DIGITS;
import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.doesntMatchExactly;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests related to methods that are inside {@link QuantifierBuilder}.
 */
class QuantifierTests {
    @Test
    void oneOrMoreMatchesCorrectly() {
        ReadableRegexPattern pattern = regex().digit().oneOrMore().build();

        assertThat(pattern, matchesExactly(DIGITS));
        assertThat(pattern, doesntMatchExactly(""));
    }

    @Test
    void optionalMatchesCorrectly() {
        ReadableRegexPattern pattern = regex().literal("a").digit().optional().build();

        assertThat(pattern, matchesExactly("a1"));
        assertThat(pattern, matchesExactly("a"));
        assertThat(pattern, doesntMatchExactly(""));
    }
}
