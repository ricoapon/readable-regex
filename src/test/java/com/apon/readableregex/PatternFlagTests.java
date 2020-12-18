package com.apon.readableregex;

import org.junit.jupiter.api.Test;

import static com.apon.readableregex.ReadableRegex.regex;
import static com.apon.readableregex.matchers.PatternMatchMatcher.doesntMatchAnythingFrom;
import static com.apon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static com.apon.readableregex.matchers.PatternMatchMatcher.matchesSomethingFrom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;

/**
 * Tests related to enabling specific pattern flags.
 */
public class PatternFlagTests {
    @Test
    void enabledFlagsAreReturned() {
        ReadableRegexPattern pattern = regex().regexFromString(".*").buildWithFlags(PatternFlag.CASE_INSENSITIVE, PatternFlag.DOT_ALL);

        assertThat(pattern.enabledFlags(), containsInAnyOrder(PatternFlag.CASE_INSENSITIVE, PatternFlag.DOT_ALL));
    }

    @Test
    void caseInsensitiveWorks() {
        // No flag means case sensitive matches.
        ReadableRegexPattern pattern = regex().literal("a").build();
        assertThat(pattern, matchesExactly("a"));
        assertThat(pattern, doesntMatchAnythingFrom("A"));

        pattern = regex().literal("a").buildWithFlags(PatternFlag.CASE_INSENSITIVE);
        assertThat(pattern, matchesExactly("a"));
        assertThat(pattern, matchesExactly("A"));
    }

    @Test
    void dotAllWorks() {
        // No flag means .* does not match new line symbols.
        ReadableRegexPattern pattern = regex().regexFromString(".*").build();
        assertThat(pattern, not(matchesExactly("a\na")));

        pattern = regex().regexFromString(".*").buildWithFlags(PatternFlag.DOT_ALL);
        assertThat(pattern, matchesExactly("a\na"));
    }

    @Test
    void multilineWorks() {
        // No flag means ^ matches the start of the entire input.
        ReadableRegexPattern pattern = regex().regexFromString("^a").build();
        assertThat(pattern, doesntMatchAnythingFrom("\na"));

        pattern = regex().regexFromString("^a").buildWithFlags(PatternFlag.MULTILINE);
        assertThat(pattern, matchesSomethingFrom("\na"));
    }
}
