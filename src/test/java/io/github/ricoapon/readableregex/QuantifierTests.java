package io.github.ricoapon.readableregex;

import org.junit.jupiter.api.Test;

import static io.github.ricoapon.readableregex.Constants.DIGITS;
import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.doesntMatchExactly;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void zeroOrMoreMatchesCorrectly() {
        ReadableRegexPattern pattern = regex().digit().zeroOrMore().build();

        assertThat(pattern, matchesExactly(""));
        assertThat(pattern, matchesExactly("1"));
        assertThat(pattern, matchesExactly("11111"));
        assertThat(pattern, doesntMatchExactly("a"));
    }

    @Test
    void countQuantifiersMatchCorrectly() {
        ReadableRegexPattern pattern = regex()
                .literal("a").exactlyNTimes(2)
                .literal("b").atLeastNTimes(2)
                .literal("c").atMostNTimes(2)
                .literal("d").betweenNAndMTimes(1, 3)
                .build();

        assertThat(pattern, matchesExactly("aabbccdd"));
        assertThat(pattern, matchesExactly("aabbbddd"));
        assertThat(pattern, doesntMatchExactly("abbbddd"));
        assertThat(pattern, doesntMatchExactly("aaabbbddd"));
        assertThat(pattern, doesntMatchExactly("aabddd"));
        assertThat(pattern, doesntMatchExactly("aabbcccddd"));
        assertThat(pattern, doesntMatchExactly("aabbccc"));
        assertThat(pattern, doesntMatchExactly("aabbcccdddd"));
    }

    @Test
    void countQuantifiersThrowIaeForInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> regex().digit().exactlyNTimes(-1));
        assertThrows(IllegalArgumentException.class, () -> regex().digit().exactlyNTimes(0));
        regex().digit().exactlyNTimes(1);

        assertThrows(IllegalArgumentException.class, () -> regex().digit().atLeastNTimes(-1));
        regex().digit().atLeastNTimes(0);
        regex().digit().atLeastNTimes(1);

        assertThrows(IllegalArgumentException.class, () -> regex().digit().betweenNAndMTimes(10, 1));
        assertThrows(IllegalArgumentException.class, () -> regex().digit().betweenNAndMTimes(-4, -5));
        assertThrows(IllegalArgumentException.class, () -> regex().digit().betweenNAndMTimes(-1, 4));
        regex().digit().betweenNAndMTimes(0, 4);

        assertThrows(IllegalArgumentException.class, () -> regex().digit().atMostNTimes(-1));
        assertThrows(IllegalArgumentException.class, () -> regex().digit().atMostNTimes(0));
        regex().digit().atMostNTimes(1);
    }
}
