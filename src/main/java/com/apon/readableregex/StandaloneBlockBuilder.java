package com.apon.readableregex;

/**
 * Builder interface with all the methods that create standalone blocks in regular expressions. A standalone block
 * is something can be followed by a quantifier and is matched in its entirety. So for example: {@code \s} or
 * {@code (?:\Qa.c\E)}. Incorrect is {@code ab}, since adding the optional quantifier {@code ?} for example, would make
 * the regex {@code ab?} which is different from {@code (?:ab)?}).
 */
public interface StandaloneBlockBuilder {
    /**
     * Appends the regex. The value is not changed or sanitized in any way.
     * <p>
     * This should only be used as a last resort when other methods cannot satisfy the expression you are looking for.
     * To avoid issues with other methods, make sure to encapsulate your regex with an unnamed group.
     * @param regex The regular expression.
     * @return This builder.
     */
    ReadableRegex regexFromString(String regex);

    /**
     * Appends the regular expression created using another builder instance to this builder. The regular expression
     * is surrounded in a non-capturing group {@code (?: ... )}.
     * @param regexBuilder The other builder instance.
     * @return This builder.
     */
    ReadableRegex add(ReadableRegex regexBuilder);

    /**
     * Appends a literal expression. All metacharacters are escaped.
     * @param literalValue The value to add.
     * @return This builder.
     */
    ReadableRegex literal(String literalValue);

    /**
     * Adds a digit. This is the same as {@code [0-9]}.
     * @return This builder.
     */
    ReadableRegex digit();

    /**
     * Adds a whitespace. This is the same as {@code \s}.
     * @return This builder.
     */
    ReadableRegex whitespace();
}
