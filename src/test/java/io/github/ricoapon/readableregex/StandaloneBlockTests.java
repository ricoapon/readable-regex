package io.github.ricoapon.readableregex;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.ricoapon.readableregex.Constants.*;
import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests related to methods that are inside {@link StandaloneBlockBuilder}.
 */
@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC", justification = "@Nested classes should be non-static, but SpotBugs wants them static." +
        "See https://github.com/spotbugs/spotbugs/issues/560 for the bug (open since 2018).")
class StandaloneBlockTests {
    @Nested
    class RegexFromString {
        @Test
        void nullAsArgumentThrowsNpe() {
            assertThrows(NullPointerException.class, () -> regex().regexFromString(null));
        }

        @Test
        void inputIsNotSurroundedWithUnnamedGroup() {
            ReadableRegexPattern pattern = regex().regexFromString("ab").oneOrMore().build();

            assertThat(pattern, matchesExactly("abbbb"));
            assertThat(pattern, doesntMatchExactly("aaabbb"));
            assertThat(pattern, doesntMatchAnythingFrom("a"));
        }
    }

    @Nested
    class Add {
        @SuppressWarnings("ConstantConditions")
        @Test
        void nullAsArgumentThrowsNpe() {
            assertThrows(NullPointerException.class, () -> regex().add((ReadableRegex) null));
            assertThrows(NullPointerException.class, () -> regex().add((ReadableRegexPattern) null));
        }

        @Test
        void inputIsCapturedInUnnamedGroup() {
            ReadableRegexPattern pattern = regex().add(regex().literal("a").digit()).oneOrMore().build();

            assertThat(pattern, matchesExactly("a1a2a3"));
            assertThat(pattern, doesntMatchExactly("a111"));

            // Same test, but now applying "build()".
            pattern = regex().add(regex().literal("a").digit().build()).oneOrMore().build();

            assertThat(pattern, matchesExactly("a1a2a3"));
            assertThat(pattern, doesntMatchExactly("a111"));
        }
    }

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
            assertThat(pattern, doesntMatchExactly("a1 *"));
        }

        @Test
        void literalsAreStandaloneBlocks() {
            ReadableRegexPattern pattern = regex().digit().literal("a").oneOrMore().build();

            assertThat(pattern, matchesExactly("1aaaa"));
            assertThat(pattern, doesntMatchExactly("1a1a"));
        }
    }

    @Nested
    class Whitespace {
        @Test
        void whitespaceOnlyMatchesWhitespaces() {
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
    }

    @Nested
    class Tab {
        @Test
        void tabOnlyMatchesTab() {
            ReadableRegexPattern pattern = regex().tab().build();

            assertThat(pattern, matchesExactly("\t"));
            assertThat(pattern, doesntMatchAnythingFrom(WHITESPACES.replaceAll("\t", "")));
        }
    }

    @Nested
    class OneOf {
        @Test
        void oneOfCanBeUsedWithZeroOrOneArguments() {
            ReadableRegexPattern pattern = regex().oneOf().oneOf(regex().literal("b")).build();

            assertThat(pattern, matchesExactly("b"));
        }

        @Test
        void oneOfCanBeUsedWithMultipleArguments() {
            ReadableRegexPattern pattern = regex().oneOf(regex().literal("b"), regex().literal("c")).build();

            assertThat(pattern, matchesExactly("b"));
            assertThat(pattern, matchesExactly("c"));
            assertThat(pattern, doesntMatchAnythingFrom(""));
        }

        @Test
        void oneOfAreStandaloneBlocks() {
            ReadableRegexPattern pattern = regex().literal("a").oneOf(regex().literal("b")).optional().build();

            assertThat(pattern, matchesExactly("a"));
            assertThat(pattern, matchesExactly("ab"));
            assertThat(pattern, doesntMatchAnythingFrom(""));
        }
    }

    @Nested
    class RangeAndNotInRange {
        @Test
        void throwIaeWhenZeroOrOddNumberOfArgumentsIsSupplied() {
            assertThrows(IllegalArgumentException.class, () -> regex().range('a'));
            assertThrows(IllegalArgumentException.class, () -> regex().range());
            assertThrows(IllegalArgumentException.class, () -> regex().notInRange('a'));
            assertThrows(IllegalArgumentException.class, () -> regex().notInRange());
        }

        @Test
        void canBeUsedWithMultipleArguments() {
            ReadableRegexPattern pattern = regex().range('a', 'e', 'x', 'z').notInRange('a', 'z', 'A', 'Z', '0', '9').build();

            assertThat(pattern, matchesExactly("b|"));
            assertThat(pattern, matchesExactly("y~"));
            assertThat(pattern, doesntMatchAnythingFrom("f|"));
            assertThat(pattern, doesntMatchAnythingFrom("yZ"));
        }
    }

    @Nested
    class AnyCharacterOfAndExcept {
        @Test
        void throwNpeWhenArgumentIsNullOrZeroLengthString() {
            assertThrows(NullPointerException.class, () -> regex().anyCharacterOf(null));
            assertThrows(IllegalArgumentException.class, () -> regex().anyCharacterOf(""));
            assertThrows(NullPointerException.class, () -> regex().anyCharacterExcept(null));
            assertThrows(IllegalArgumentException.class, () -> regex().anyCharacterExcept(""));
        }

        @Test
        void characterRangesAreMatched() {
            ReadableRegexPattern pattern = regex().anyCharacterOf("aeiou")
                    .anyCharacterExcept("abc")
                    .build();

            assertThat(pattern, matchesExactly("ix"));
            assertThat(pattern, doesntMatchAnythingFrom("ia"));
            assertThat(pattern, doesntMatchAnythingFrom("xx"));
        }
    }

    @Nested
    class WordCharacter {
        @Test
        void matchCorrectCharacter() {
            ReadableRegexPattern pattern = regex().wordCharacter().nonWordCharacter().build();

            assertThat(pattern, matchesExactly("a."));
            assertThat(pattern, doesntMatchAnythingFrom("ab"));
            assertThat(pattern, doesntMatchAnythingFrom(".a"));
        }
    }

    @Nested
    class WordBoundary {
        @Test
        void wordBoundaryMatchesCorrectly() {
            ReadableRegexPattern pattern = regex().literal("abc").wordBoundary().literal(" ").build();

            assertThat(pattern, matchesExactly("abc "));
        }

        @Test
        void nonWordBoundaryMatchesCorrectly() {
            ReadableRegexPattern pattern = regex().literal("a").nonWordBoundary().literal("b").build();

            assertThat(pattern, matchesExactly("ab"));
        }
    }

    /**
     * No tests are needed for the method {@link StandaloneBlockBuilder#anyCharacter()}, because this is already covered by other tests:
     * <ul>
     *     <li>{@link PatternFlagTests#dotAllWorks()}</li>
     *     <li>{@link SyntacticSugarTests#anythingWorks()}</li>
     * </ul>
     */
    @Nested
    class AnyCharacter {
    }

    @Nested
    class StartOfLine {
        @Test
        void quantifierAfterWorks() {
            ReadableRegexPattern pattern = regex().startOfLine().exactlyNTimes(1).literal("a").build();

            assertThat(pattern, matchesExactly("a"));
            assertThat(pattern, matchesSomethingFrom("\na"));
            assertThat(pattern, doesntMatchAnythingFrom("ba"));
        }

        @Test
        void methodEnablesMultilineFlag() {
            ReadableRegexPattern pattern = regex().startOfLine().build();
            ReadableRegexPattern pattern2 = regex().startOfLine().buildWithFlags(PatternFlag.MULTILINE);

            assertThat(pattern.enabledFlags(), contains(PatternFlag.MULTILINE));
            assertThat(pattern2.enabledFlags(), contains(PatternFlag.MULTILINE));
        }
    }

    @Nested
    class StartOfInput {
        @Test
        void worksCorrectlyWithStartOfLine() {
            ReadableRegexPattern pattern = regex().startOfInput().regexFromString("\n").startOfLine().literal("a").build();

            assertThat(pattern, matchesExactly("\na"));
            assertThat(pattern, doesntMatchAnythingFrom("\n\na"));
        }
    }

    @Nested
    class EndOfLine {
        @Test
        void quantifierAfterWorks() {
            ReadableRegexPattern pattern = regex().literal("a").endOfLine().exactlyNTimes(1).build();

            assertThat(pattern, matchesExactly("a"));
            assertThat(pattern, matchesSomethingFrom("a\n"));
            assertThat(pattern, doesntMatchAnythingFrom("ab"));
        }

        @Test
        void methodEnablesMultilineFlag() {
            ReadableRegexPattern pattern = regex().endOfLine().build();
            ReadableRegexPattern pattern2 = regex().endOfLine().buildWithFlags(PatternFlag.MULTILINE);

            assertThat(pattern.enabledFlags(), contains(PatternFlag.MULTILINE));
            assertThat(pattern2.enabledFlags(), contains(PatternFlag.MULTILINE));
        }
    }

    @Nested
    class EndOfInput {
        @Test
        void worksCorrectlyWithEndOfLine() {
            ReadableRegexPattern pattern = regex().literal("a").endOfLine().regexFromString("\n").endOfInput().build();

            assertThat(pattern, matchesExactly("a\n"));
            assertThat(pattern, doesntMatchAnythingFrom("a\n\n"));
        }
    }
}
