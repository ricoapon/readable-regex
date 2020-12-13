package com.apon.readableregex;

import org.junit.jupiter.api.Test;

import static com.apon.readableregex.ReadableRegex.regex;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests related to methods that are inside {@link FinishBuilder}.
 */
class FinishTests {
    private final static String REGEX = "a1?";
    private final ReadableRegex readableRegex = regex().regexFromString(REGEX);

    @Test
    void underlyingPatternIsExposed() {
        assertThat(readableRegex.buildJdkPattern().toString(), equalTo(REGEX));
        assertThat(readableRegex.build().getUnderlyingPattern().toString(), equalTo(REGEX));
    }

    @Test
    void toStringReturnsPattern() {
        assertThat(readableRegex.build().toString(), equalTo(REGEX));
    }

    @Test
    void throwNpeWhenTextToMatchIsNull() {
        assertThrows(NullPointerException.class, () -> readableRegex.build().matches(null));
    }
}
