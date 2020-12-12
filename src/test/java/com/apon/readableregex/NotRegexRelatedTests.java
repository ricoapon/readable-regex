package com.apon.readableregex;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class with all the tests that are not related to creating the regular expressions themselves.
 */
class NotRegexRelatedTests {
    @Test
    void underlyingPatternIsExposed() {
        assertThat(ReadableRegex.regex().buildJdkPattern().toString(), equalTo(""));
        assertThat(ReadableRegex.regex().build().getUnderlyingPattern().toString(), equalTo(""));
    }

    @Test
    void toStringReturnsPattern() {
        assertThat(ReadableRegex.regex().build().toString(), equalTo(""));
    }

    @Test
    void throwNpeWhenTextToMatchIsNull() {
        assertThrows(NullPointerException.class, () -> ReadableRegex.regex().build().matches(null));
    }
}
