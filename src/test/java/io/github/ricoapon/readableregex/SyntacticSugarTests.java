package io.github.ricoapon.readableregex;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests related to methods that are inside {@link SyntacticSugarBuilder}.
 */
@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC", justification = "@Nested classes should be non-static, but SpotBugs wants them static." +
        "See https://github.com/spotbugs/spotbugs/issues/560 for the bug (open since 2018).")
class SyntacticSugarTests {
    @Nested
    class StandaloneBlock {
        @Test
        void wordWorks() {
            String groupName = "result";
            ReadableRegexPattern pattern = regex().startGroup(groupName).word().endGroup().build();

            Matcher matcher = pattern.matches("abc de_f1 ghi|jkl");
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(groupName), equalTo("abc"));
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(groupName), equalTo("de_f1"));
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(groupName), equalTo("ghi"));
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(groupName), equalTo("jkl"));
        }

        @Test
        void anythingWorks() {
            ReadableRegexPattern pattern = regex().anything().build();

            assertThat(pattern, matchesExactly(""));
            assertThat(pattern, matchesExactly(Constants.WORD_CHARACTERS));
            assertThat(pattern, matchesExactly(Constants.DIGITS));
            assertThat(pattern, matchesExactly(Constants.NON_LETTERS));
        }
    }

    @Nested
    class Group {
        @Test
        void groupWorks() {
            ReadableRegexPattern pattern = regex().digit().group("firstGroupName", regex().digit().group(regex().digit())).build();
            Matcher matcher = pattern.matches("123");

            assertThat(matcher.matches(), equalTo(true));
            assertThat(matcher.group("firstGroupName"), equalTo("23"));
            assertThat(matcher.group(2), equalTo("3"));
        }

        @Test
        void lookbehindWorks() {
            ReadableRegexPattern pattern = regex().positiveLookbehind(regex().digit()).whitespace().build();
            assertThat(pattern, matchesSomethingFrom("1 "));
            assertThat(pattern, doesntMatchAnythingFrom(" "));

            pattern = regex().negativeLookbehind(regex().digit()).whitespace().build();
            assertThat(pattern, doesntMatchAnythingFrom("1 "));
            assertThat(pattern, matchesSomethingFrom(" "));
        }

        @Test
        void lookaheadWorks() {
            ReadableRegexPattern pattern = regex().whitespace().positiveLookahead(regex().digit()).build();
            assertThat(pattern, matchesSomethingFrom(" 1"));
            assertThat(pattern, doesntMatchAnythingFrom(" "));

            pattern = regex().whitespace().negativeLookahead(regex().digit()).build();
            assertThat(pattern, doesntMatchAnythingFrom(" 1"));
            assertThat(pattern, matchesSomethingFrom(" "));
        }
    }
}
