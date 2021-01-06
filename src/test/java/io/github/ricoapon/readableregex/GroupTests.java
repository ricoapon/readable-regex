package io.github.ricoapon.readableregex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Matcher;

import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.doesntMatchAnythingFrom;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.matchesSomethingFrom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests related to methods inside {@link GroupBuilder}.
 */
class GroupTests {
    @Test
    void nestedGroupsAreCaptured() {
        // Test using start/end syntax.
        ReadableRegexPattern pattern = regex().digit().startGroup().digit().startGroup().digit().endGroup().endGroup().build();
        Matcher matcher = pattern.matches("123");

        assertThat(matcher.matches(), equalTo(true));
        assertThat(matcher.group(1), equalTo("23"));
        assertThat(matcher.group(2), equalTo("3"));

        // Test using group(regexBuilder) syntax.
        pattern = regex().digit().group(regex().digit().group(regex().digit())).build();
        matcher = pattern.matches("123");

        assertThat(matcher.matches(), equalTo(true));
        assertThat(matcher.group(1), equalTo("23"));
        assertThat(matcher.group(2), equalTo("3"));
    }

    @Test
    void nestedNamedGroupsAreCaptured() {
        // Test using start/end syntax.
        String firstGroupName = "first";
        String secondGroupName = "second";
        ReadableRegexPattern pattern = regex().digit().startGroup(firstGroupName).digit().startGroup(secondGroupName).digit().endGroup().endGroup().build();
        Matcher matcher = pattern.matches("123");

        assertThat(matcher.matches(), equalTo(true));
        assertThat(matcher.group(firstGroupName), equalTo("23"));
        assertThat(matcher.group(secondGroupName), equalTo("3"));

        // Test using group(regexBuilder) syntax.
        pattern = regex().digit().group(firstGroupName, regex().digit().group(secondGroupName, regex().digit())).build();
        matcher = pattern.matches("123");

        assertThat(matcher.matches(), equalTo(true));
        assertThat(matcher.group(firstGroupName), equalTo("23"));
        assertThat(matcher.group(secondGroupName), equalTo("3"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0a", "a[", "a_", ""})
    void invalidGroupNameThrowsIllegalArgumentException(String groupName) {
        assertThrows(IllegalArgumentException.class, () -> regex().startGroup(groupName));
    }

    @Test
    void unnamedGroupsDontCapture() {
        ReadableRegexPattern pattern = regex().startUnnamedGroup().digit().endGroup()
                .group(regex().digit()).build();

        Matcher matcher = pattern.matches("12");
        assertThat(matcher.matches(), equalTo(true));
        assertThat(matcher.group(1), equalTo("2"));
    }

    @Test
    void lookbehindsWork() {
        // Test using start/end syntax.
        ReadableRegexPattern pattern = regex().startPositiveLookbehind().digit().endGroup().whitespace().build();
        assertThat(pattern, matchesSomethingFrom("1 "));
        assertThat(pattern, doesntMatchAnythingFrom(" "));

        // Test using positiveLookbehind(regexBuilder) syntax.
        pattern = regex().positiveLookbehind(regex().digit()).whitespace().build();
        assertThat(pattern, matchesSomethingFrom("1 "));
        assertThat(pattern, doesntMatchAnythingFrom(" "));

        // Test using start/end syntax.
        pattern = regex().startNegativeLookbehind().digit().endGroup().whitespace().build();
        assertThat(pattern, doesntMatchAnythingFrom("1 "));
        assertThat(pattern, matchesSomethingFrom(" "));

        // Test using negativeLookbehind(regexBuilder) syntax.
        pattern = regex().negativeLookbehind(regex().digit()).whitespace().build();
        assertThat(pattern, doesntMatchAnythingFrom("1 "));
        assertThat(pattern, matchesSomethingFrom(" "));
    }

    @Test
    void lookaheadsWork() {
        // Test using start/end syntax.
        ReadableRegexPattern pattern = regex().whitespace().startPositiveLookahead().digit().endGroup().build();
        assertThat(pattern, matchesSomethingFrom(" 1"));
        assertThat(pattern, doesntMatchAnythingFrom(" "));

        // Test using positiveLookahead(regexBuilder) syntax.
        pattern = regex().whitespace().positiveLookahead(regex().digit()).build();
        assertThat(pattern, matchesSomethingFrom(" 1"));
        assertThat(pattern, doesntMatchAnythingFrom(" "));

        // Test using start/end syntax.
        pattern = regex().whitespace().startNegativeLookahead().digit().endGroup().build();
        assertThat(pattern, doesntMatchAnythingFrom(" 1"));
        assertThat(pattern, matchesSomethingFrom(" "));

        // Test using negativeLookahead(regexBuilder) syntax.
        pattern = regex().whitespace().negativeLookahead(regex().digit()).build();
        assertThat(pattern, doesntMatchAnythingFrom(" 1"));
        assertThat(pattern, matchesSomethingFrom(" "));
    }
}
