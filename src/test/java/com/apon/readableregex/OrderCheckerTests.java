package com.apon.readableregex;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.apon.readableregex.ReadableRegex.regex;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests related to check whether order of methods are viable or not.
 */
@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC", justification = "@Nested classes should be non-static, but SpotBugs wants them static." +
        "See https://github.com/spotbugs/spotbugs/issues/560 for the bug (open since 2018).")
class OrderCheckerTests {
    @Nested
    class Quantifiers {
        @Test
        void cannotStartWithQuantifier() {
            assertThrows(IncorrectConstructionException.class, () -> regex().oneOrMore());
            assertThrows(IncorrectConstructionException.class, () -> regex().optional());
        }

        @Test
        void cannotUseQualifierAfterQualifier() {
            assertThrows(IncorrectConstructionException.class, () -> regex().digit().oneOrMore().optional());
            assertThrows(IncorrectConstructionException.class, () -> regex().digit().optional().oneOrMore());
        }
    }
}
