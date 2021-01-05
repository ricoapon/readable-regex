package io.github.ricoapon.readableregex;

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

    /**
     * Adds either or group. This is the same as {@code (?:X|Y)}, where {@code X} and {@code Y} are given regular expressions.
     * @param regexBuilders Regular expressions for which one needs to match.
     * @return This builder.
     */
    ReadableRegex oneOf(ReadableRegex... regexBuilders);

    /**
     * Adds a specified range. This is the same as {@code [a-z]}.
     * <p>
     * Example: {@code range('a', 'f', '0', '9')} comes down to {@code [a-f0-9]}.
     * @param boundaries All the boundaries. You must supply an even amount of arguments.
     * @return This builder.
     */
    ReadableRegex range(char... boundaries);

    /**
     * Adds a negated specified range. This is the same as {@code [^a-z]}.
     * <p>
     * Example: {@code notInRange('a', 'f', '0', '9')} comes down to {@code [^a-f0-9]}.
     * @param boundaries All the boundaries. You must supply an even amount of arguments.
     * @return This builder.
     */
    ReadableRegex notInRange(char... boundaries);

    /**
     * Adds a range with the specified characters. This is the same as {@code [...]}.
     * <p>
     * Example: {@code anyCharacterOf("abc")} comes down to {@code [abc]}.
     * @param characters The characters to match.
     * @return This builder.
     */
    ReadableRegex anyCharacterOf(String characters);

    /**
     * Adds a negated range with the specified characters. This is the same as {@code [^...]}.
     * <p>
     * Example: {@code anyCharacterExcept("abc")} comes down to {@code [^abc]}.
     * @param characters The characters to match.
     * @return This builder.
     */
    ReadableRegex anyCharacterExcept(String characters);

    /**
     * Adds a word character. This is the same as {@code \w}.
     * @return This builder.
     */
    ReadableRegex wordCharacter();

    /**
     * Adds a non word character. This is the same as {@code \W}.
     * @return This builder.
     */
    ReadableRegex nonWordCharacter();

    /**
     * Adds a word boundary. This is the same as {@code \b}.
     * @return This builder.
     */
    ReadableRegex wordBoundary();

    /**
     * Adds a non word boundary. This is the same as {@code \B}.
     * @return This builder.
     */
    ReadableRegex nonWordBoundary();
}
