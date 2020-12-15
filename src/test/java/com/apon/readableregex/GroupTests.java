package com.apon.readableregex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Matcher;

import static com.apon.readableregex.ReadableRegex.regex;
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

}
