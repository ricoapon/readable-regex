package com.apon.readableregex.internal;

import com.apon.readableregex.ReadableRegex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.apon.readableregex.internal.MethodOrderChecker.Method.QUANTIFIER;
import static com.apon.readableregex.internal.MethodOrderChecker.Method.STANDALONE_BLOCK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ReadableRegexOrderCheckerTest {

    /** Dummy implementation of {@link MethodOrderChecker} to check {@link MethodOrderChecker#checkCallingMethod(Method)}. */
    private static class DummyOrderChecker extends MethodOrderChecker {
        public Method calledMethod;
        @Override
        public void checkCallingMethod(Method method) {
            calledMethod = method;
        }
    }

    private ReadableRegexOrderChecker readableRegexOrderChecker;
    private DummyOrderChecker dummyOrderChecker;

    @BeforeEach
    void setUp() {
        dummyOrderChecker = new DummyOrderChecker();

        // Call digit, so that quantifiers can be called directly after. Does not matter for others.
        readableRegexOrderChecker = (ReadableRegexOrderChecker) new ReadableRegexOrderChecker(dummyOrderChecker).digit();
    }

    @Test
    void regexFromString_StandaloneBlock() {
        readableRegexOrderChecker.regexFromString("");
        assertThat(dummyOrderChecker.calledMethod, equalTo(STANDALONE_BLOCK));
    }

    @Test
    void add_StandaloneBlock() {
        readableRegexOrderChecker.add(ReadableRegex.regex());
        assertThat(dummyOrderChecker.calledMethod, equalTo(STANDALONE_BLOCK));
    }

    @Test
    void literal_StandaloneBlock() {
        readableRegexOrderChecker.literal("");
        assertThat(dummyOrderChecker.calledMethod, equalTo(STANDALONE_BLOCK));
    }

    @Test
    void digit_StandaloneBlock() {
        readableRegexOrderChecker.digit();
        assertThat(dummyOrderChecker.calledMethod, equalTo(STANDALONE_BLOCK));
    }

    @Test
    void whitespace_StandaloneBlock() {
        readableRegexOrderChecker.whitespace();
        assertThat(dummyOrderChecker.calledMethod, equalTo(STANDALONE_BLOCK));
    }

    @Test
    void oneOrMore_Quantifier() {
        readableRegexOrderChecker.oneOrMore();
        assertThat(dummyOrderChecker.calledMethod, equalTo(QUANTIFIER));
    }

    @Test
    void optional_Quantifier() {
        readableRegexOrderChecker.optional();
        assertThat(dummyOrderChecker.calledMethod, equalTo(QUANTIFIER));
    }
}
