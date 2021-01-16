package io.github.ricoapon.readableregex;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Test;

import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests related to methods that are inside {@link FinishBuilder}.
 */
@SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT", justification = "Lambda inside throwNpeWhenTextToMatchIsNull " +
        "triggers SpotBugs, but we know we don't use the return value. We expect to throw an NPE. " +
        "For some reason, this only has to be ignored after commit f1a8d20a9611d1f940823e16721d6efaeb4d16ed. Before the " +
        "(unrelated!) code was committed, this did not happen.")
class FinishTests {
    private final static String REGEX = "a1?";
    private final ReadableRegex<?> readableRegex = regex(REGEX);

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
