package com.apon.readableregex;

import org.junit.jupiter.api.Test;

import static com.apon.readableregex.matchers.PatternMatchMatcher.matchesExactly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

/**
 * Tests related to {@link ReadableRegex#literal(String)}.
 */
class LiteralTest {
    @Test
    void literalCharactersAreEscaped() {
        ReadableRegexPattern pattern = ReadableRegex.regex()
                .literal("a.()[]\\/|?.+*")
                .build();

        assertThat(pattern, matchesExactly("a.()[]\\/|?.+*"));
    }

    @Test
    void literalCanBeCombinedWithMetaCharacters() {
        ReadableRegexPattern pattern = ReadableRegex.regex()
                .literal(".").digit().whitespace().literal("*")
                .build();

        assertThat(pattern, matchesExactly(".1 *"));
        assertThat(pattern, matchesExactly(".2\t*"));
        assertThat(pattern, not(matchesExactly("a1 *")));
    }
}
