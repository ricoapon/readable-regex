package io.github.ricoapon.readableregex;

import org.junit.jupiter.api.Test;

import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ReadableRegexPatternTest {
    @Test
    void matchesExactlyWorks() {
        ReadableRegexPattern pattern = regex().digit().build();

        assertThat(pattern.matchesTextExactly("1"), equalTo(true));
        assertThat(pattern.matchesTextExactly("a"), equalTo(false));
    }
}
