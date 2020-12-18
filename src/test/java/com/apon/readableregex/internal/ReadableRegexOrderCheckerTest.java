package com.apon.readableregex.internal;

import com.apon.readableregex.PatternFlag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.apon.readableregex.ReadableRegex.regex;
import static com.apon.readableregex.internal.MethodOrderChecker.Method.*;
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
    void build_Finish() {
        readableRegexOrderChecker.build();
        assertThat(dummyOrderChecker.calledMethod, equalTo(FINISH));
    }

    @Test
    void buildWithFlags_Finish() {
        readableRegexOrderChecker.buildWithFlags(PatternFlag.CASE_INSENSITIVE, PatternFlag.MULTILINE);
        assertThat(dummyOrderChecker.calledMethod, equalTo(FINISH));
    }

    @Test
    void regexFromString_StandaloneBlock() {
        readableRegexOrderChecker.regexFromString("");
        assertThat(dummyOrderChecker.calledMethod, equalTo(STANDALONE_BLOCK));
    }

    @Test
    void add_StandaloneBlock() {
        readableRegexOrderChecker.add(regex());
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

    @Test
    void startGroup_StartGroup() {
        readableRegexOrderChecker.startGroup();
        assertThat(dummyOrderChecker.calledMethod, equalTo(START_GROUP));

        readableRegexOrderChecker.startGroup("a");
        assertThat(dummyOrderChecker.calledMethod, equalTo(START_GROUP));
    }

    @Test
    void startLook_StartGroup() {
        readableRegexOrderChecker.startPositiveLookbehind();
        assertThat(dummyOrderChecker.calledMethod, equalTo(START_GROUP));

        readableRegexOrderChecker.startNegativeLookbehind();
        assertThat(dummyOrderChecker.calledMethod, equalTo(START_GROUP));
    }

    @Test
    void endGroup_EndGroup() {
        readableRegexOrderChecker.startGroup().endGroup();
        assertThat(dummyOrderChecker.calledMethod, equalTo(END_GROUP));
    }

    @Test
    void group_StandaloneBlock() {
        readableRegexOrderChecker.group(regex());
        assertThat(dummyOrderChecker.calledMethod, equalTo(END_GROUP));

        readableRegexOrderChecker.group("a", regex());
        assertThat(dummyOrderChecker.calledMethod, equalTo(END_GROUP));
    }
}
