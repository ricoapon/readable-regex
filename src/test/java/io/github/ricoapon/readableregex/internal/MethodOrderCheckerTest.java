package io.github.ricoapon.readableregex.internal;

import io.github.ricoapon.readableregex.IncorrectConstructionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.ricoapon.readableregex.internal.MethodOrderChecker.Method.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MethodOrderCheckerTest {
    private MethodOrderChecker methodOrderChecker;

    @BeforeEach
    void setUp() {
        methodOrderChecker = new MethodOrderChecker();
    }

    @Test
    void testFor100PercentCodeCoverage() {
        // We know there are a fixed number of enums. This means that the if-statements will never reach the branch
        // false-false...-false. However, if we input null, we do reach this case. Ugly way to achieve this, but it works.
        // Also note that we cannot use a switch statement anymore, since that doesn't allow null.
        methodOrderChecker.checkCallingMethod(null);
    }

    @Test
    void quantifierCanNotBeDoneAtTheStart() {
        assertThrows(IncorrectConstructionException.class, () -> methodOrderChecker.checkCallingMethod(QUANTIFIER));
    }

    @Test
    void quantifierIsPossibleAfterStandaloneBlockButNotAfterQualifier() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        assertThrows(IncorrectConstructionException.class, () -> methodOrderChecker.checkCallingMethod(QUANTIFIER));
    }

    @Test
    void cannotCloseGroupIfNoneStarted() {
        assertThrows(IncorrectConstructionException.class, () -> methodOrderChecker.checkCallingMethod(END_GROUP));
    }

    @Test
    void cannotFinishWhenGroupsAreOpen() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        assertThrows(IncorrectConstructionException.class, () -> methodOrderChecker.checkCallingMethod(FINISH));
    }

    @Test
    void quantifierIsNotPossibleAfterStartingGroup() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        assertThrows(IncorrectConstructionException.class, () -> methodOrderChecker.checkCallingMethod(QUANTIFIER));
    }

    @Test
    void quantifierIsPossibleAfterEndingGroup() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        methodOrderChecker.checkCallingMethod(END_GROUP);
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        // Test will fail automatically if no exception is thrown.
    }
}
