package io.github.ricoapon.readableregex.extendable;

import io.github.ricoapon.readableregex.PatternFlag;
import io.github.ricoapon.readableregex.ReadableRegexPattern;
import org.junit.jupiter.api.Test;

import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.doesntMatchAnythingFrom;
import static io.github.ricoapon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Tests related to extending the builder using {@link io.github.ricoapon.readableregex.ExtendableReadableRegex}.
 */
class ExtendableReadableRegexTest {
    @Test
    void methodCanBeAdded() {
        ReadableRegexPattern pattern = TestExtension.regex().literal("a").digitWhitespaceDigit().literal("a").build();

        assertThat(pattern, matchesExactly("a1 4a"));
        assertThat(pattern, doesntMatchAnythingFrom("1 1"));
        assertThat(pattern, doesntMatchAnythingFrom("a11a"));
    }

    @Test
    void methodCanBeOverwritten() {
        ReadableRegexPattern pattern = TestExtension.regex().build();

        assertThat(pattern.enabledFlags(), contains(PatternFlag.DOT_ALL));
    }
}
