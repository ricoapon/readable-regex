package io.github.ricoapon.readableregex;

import io.github.ricoapon.readableregex.internal.MethodOrderChecker;
import io.github.ricoapon.readableregex.internal.ReadableRegexOrderChecker;

/**
 * This class can be extended to create your own builder. Example code:
 * <pre>{@code // You have to extend from ExtendableReadableRegex, where you fill in your own class as generic type.
 * public class MyExtension extends ExtendableReadableRegex<TestExtension> {
 *
 *     // It is highly advised to create your own static method "regex()". This way you can easily instantiate
 *     // your class and in your existing code you only have to change your import statement.
 *     public static TestExtension regex() {
 *         return new TestExtension();
 *     }
 *
 *     // In your own extension you can add any method you like.
 *     public TestExtension digitWhitespaceDigit() {
 *         // For the implementation of your extension, you can only use the publicly available methods. All variables
 *         // and other methods are made private in the instance.
 *         // If you want to add arbitrary expressions, you can always use the method "regexFromString(...)".
 *         return digit().whitespace().digit();
 *     }
 *
 *     // You can also override existing methods! To make sure that the code doesn't break, please always end
 *     // with calling the super method.
 *     @Override
 *     public ReadableRegexPattern buildWithFlags(PatternFlag... patternFlags) {
 *         return super.buildWithFlags(PatternFlag.DOT_ALL);
 *     }
 * }}</pre>
 *
 * This can now be used as follows:
 * <pre>{@code ReadableRegexPattern pattern = TestExtension.regex().digitWhitespaceDigit().build();
 *
 * assertThat(pattern.matchesTextExactly("1 3"), equalTo(true));
 * assertThat(pattern.enabledFlags(), contains(PatternFlag.DOT_ALL)); }</pre>
 * @param <T> The new type that is used as builder.
 */
public class ExtendableReadableRegex<T extends ReadableRegex<T>> extends ReadableRegexOrderChecker<T> {
    /**
     * Constructor.
     */
    public ExtendableReadableRegex() {
        super(new MethodOrderChecker());
    }
}
