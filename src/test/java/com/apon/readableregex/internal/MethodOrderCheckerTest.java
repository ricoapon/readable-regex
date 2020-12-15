package com.apon.readableregex.internal;

import com.apon.readableregex.IncorrectConstructionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MethodOrderCheckerTest {
    private MethodOrderChecker methodOrderChecker;

    @BeforeEach
    void setUp() {
        methodOrderChecker = new MethodOrderChecker();
    }

    @Test
    void quantifierCanNotBeDoneAtTheStart() {
        assertThrows(IncorrectConstructionException.class, () -> methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.QUANTIFIER));
    }

    @Test
    void quantifierIsPossibleAfterStandaloneBlockButNotAfterQualifier() {
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.STANDALONE_BLOCK);
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.QUANTIFIER);
        assertThrows(IncorrectConstructionException.class, () -> methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.QUANTIFIER));
    }
}
