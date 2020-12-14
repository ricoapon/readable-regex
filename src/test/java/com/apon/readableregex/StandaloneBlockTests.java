package com.apon.readableregex;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.apon.readableregex.Constants.*;
import static com.apon.readableregex.Constants.WHITESPACES;
import static com.apon.readableregex.ReadableRegex.regex;
import static com.apon.readableregex.matchers.PatternMatchMatcher.doesntMatchAnythingFrom;
import static com.apon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests related to methods that are inside {@link StandaloneBlockBuilder}.
 */
@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC", justification = "@Nested classes should be non-static, but SpotBugs wants them static." +
        "See https://github.com/spotbugs/spotbugs/issues/560 for the bug (open since 2018).")
class StandaloneBlockTests {
    @Nested
    class Digits {
        @Test
        void digitOnlyMatchesDigits() {
            ReadableRegexPattern pattern = regex().digit().build();

            // Matches exactly every character inside DIGITS.
            for (String digit : DIGITS.split("")) {
                assertThat(pattern, matchesExactly(digit));
            }
            assertThat(pattern, doesntMatchAnythingFrom(WORD_CHARACTERS));
            assertThat(pattern, doesntMatchAnythingFrom(NON_LETTERS));
            assertThat(pattern, doesntMatchAnythingFrom(WHITESPACES));
        }

        @Test
        void digitsAreStandaloneBlocks() {
            ReadableRegexPattern pattern = regex().literal("a").digit().oneOrMore().build();

            assertThat(pattern, matchesExactly("a" + DIGITS));
            assertThat(pattern, not(matchesExactly("a1a1")));
        }
    }

    @Nested
    class Literals {
        @Test
        void nullAsArgumentThrowsNpe() {
            assertThrows(NullPointerException.class, () -> regex().literal(null));
        }

        @Test
        void literalCharactersAreEscaped() {
            ReadableRegexPattern pattern = regex()
                    .literal("a.()[]\\/|?.+*")
                    .build();

            assertThat(pattern, matchesExactly("a.()[]\\/|?.+*"));
        }

        @Test
        void literalCanBeCombinedWithMetaCharacters() {
            ReadableRegexPattern pattern = regex()
                    .literal(".").digit().whitespace().literal("*")
                    .build();

            assertThat(pattern, matchesExactly(".1 *"));
            assertThat(pattern, matchesExactly(".2\t*"));
            assertThat(pattern, not(matchesExactly("a1 *")));
        }

        @Test
        void literalsAreStandaloneBlocks() {
            ReadableRegexPattern pattern = regex().digit().literal("a").oneOrMore().build();

            assertThat(pattern, matchesExactly("1aaaa"));
            assertThat(pattern, not(matchesExactly("1a1a")));
        }
    }

    @Nested
    class Whitespaces {
        @Test
        void whitespacesOnlyMatchWhitespaces() {
            ReadableRegexPattern pattern = regex().whitespace().build();

            // Matches exactly every character inside WHITESPACES.
            for (String digit : WHITESPACES.split("")) {
                assertThat(pattern, matchesExactly(digit));
            }
            assertThat(pattern, matchesExactly(WHITESPACES.substring(0, 1)));
            assertThat(pattern, doesntMatchAnythingFrom(WORD_CHARACTERS));
            assertThat(pattern, doesntMatchAnythingFrom(NON_LETTERS));
            assertThat(pattern, doesntMatchAnythingFrom(DIGITS));
        }

        @Test
        void whitespacesAreStandaloneBlocks() {
            ReadableRegexPattern pattern = regex().literal("a").whitespace().oneOrMore().build();

            assertThat(pattern, matchesExactly("a" + WHITESPACES));
            assertThat(pattern, not(matchesExactly("a a ")));
        }
    }
}
