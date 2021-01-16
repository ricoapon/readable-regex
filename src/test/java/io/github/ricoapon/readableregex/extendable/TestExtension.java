package io.github.ricoapon.readableregex.extendable;

import io.github.ricoapon.readableregex.ExtendableReadableRegex;
import io.github.ricoapon.readableregex.PatternFlag;
import io.github.ricoapon.readableregex.ReadableRegexPattern;

/**
 * Dummy extension that adds a new method and overrides an existing method.
 */
public class TestExtension extends ExtendableReadableRegex<TestExtension> {
    public static TestExtension regex() {
        return new TestExtension();
    }

    public TestExtension digitWhitespaceDigit() {
        return digit().whitespace().digit();
    }

    @Override
    public ReadableRegexPattern buildWithFlags(PatternFlag... patternFlags) {
        // Always build with exactly one flag: DOT_ALL.
        return super.buildWithFlags(PatternFlag.DOT_ALL);
    }
}
