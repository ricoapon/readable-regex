package io.github.ricoapon.readableregex;

/**
 * Builder interface with all the methods that create standalone blocks in regular expressions. A standalone block
 * is something can be followed by a quantifier and is matched in its entirety. So for example: {@code \s} or
 * {@code (?:\Qa.c\E)}. But {@code ab} is not a standalone block, since adding the optional quantifier {@code ?}, for example, would make
 * the expression {@code ab?}. This is different from {@code (ab)?}).
 */
public interface StandaloneBlockBuilder<T extends ReadableRegex<T>> {
    /**
     * Appends the regular expression. The value is not changed or sanitized in any way.
     * <p>
     * This should only be used as a last resort when other methods cannot satisfy the expression you are looking for.
     * To avoid issues with other methods, make sure to encapsulate your regex with an unnamed group.
     * @param regex The regular expression.
     * @return This builder.
     */
    T regexFromString(String regex);

    /**
     * See {@link #add(ReadableRegexPattern)}.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default T add(ReadableRegex<?> regexBuilder) {
        return add(regexBuilder.build());
    }

    /**
     * Appends the regular expression created using another builder instance to this builder. The regular expression
     * is surrounded in a non-capturing group {@code (?:...)}.
     * @param pattern The pattern.
     * @return This builder.
     */
    T add(ReadableRegexPattern pattern);

    /**
     * Appends a literal expression. All metacharacters are escaped.
     * @param literalValue The value to add.
     * @return This builder.
     */
    T literal(String literalValue);

    /**
     * Adds a digit. This is the same as {@code [0-9]}.
     * @return This builder.
     */
    T digit();

    /**
     * Adds a whitespace. This is the same as {@code \s}.
     * @return This builder.
     */
    T whitespace();

    /**
     * Adds a tab. This is the same as {@code \t}.
     * @return This builder.
     */
    T tab();

    /**
     * Adds either or block. This is the same as {@code (?:X|Y)}, where {@code X} and {@code Y} are given regular expressions.
     * @param regexBuilders Regular expressions for which one needs to match.
     * @return This builder.
     */
    T oneOf(ReadableRegex<?>... regexBuilders);

    /**
     * Adds a specified range. This is the same as {@code [a-z]}.
     * <p>
     * Example: {@code range('a', 'f', '0', '9')} comes down to {@code [a-f0-9]}.
     * @param boundaries All the boundaries. You must supply an even amount of arguments.
     * @return This builder.
     */
    T range(char... boundaries);

    /**
     * Adds a negated specified range. This is the same as {@code [^a-z]}.
     * <p>
     * Example: {@code notInRange('a', 'f', '0', '9')} comes down to {@code [^a-f0-9]}.
     * @param boundaries All the boundaries. You must supply an even amount of arguments.
     * @return This builder.
     */
    T notInRange(char... boundaries);

    /**
     * Adds a range with the specified characters. This is the same as {@code [...]}.
     * <p>
     * Example: {@code anyCharacterOf("abc")} comes down to {@code [abc]}.
     * @param characters The characters to match.
     * @return This builder.
     */
    T anyCharacterOf(String characters);

    /**
     * Adds a negated range with the specified characters. This is the same as {@code [^...]}.
     * <p>
     * Example: {@code anyCharacterExcept("abc")} comes down to {@code [^abc]}.
     * @param characters The characters to match.
     * @return This builder.
     */
    T anyCharacterExcept(String characters);

    /**
     * Adds a word character. This is the same as {@code \w}.
     * @return This builder.
     */
    T wordCharacter();

    /**
     * Adds a non word character. This is the same as {@code \W}.
     * @return This builder.
     */
    T nonWordCharacter();

    /**
     * Adds a word boundary. This is the same as {@code \b}.
     * @return This builder.
     */
    T wordBoundary();

    /**
     * Adds a non word boundary. This is the same as {@code \B}.
     * @return This builder.
     */
    T nonWordBoundary();

    /**
     * Adds any character. This is the same as {@code .}.
     * <p>
     * Note that you have to enable {@link PatternFlag#DOT_ALL} to match line terminators.
     * @return This builder.
     */
    T anyCharacter();

    /**
     * Adds a start of line anchor. This is the same as {@code ^}.
     * <p>
     * Note that this only works if the flag {@link PatternFlag#MULTILINE} is enabled. This is done automatically when
     * using this method. If you want to match the start of the input instead, please use {@link #startOfInput()}.
     * @return This builder.
     */
    T startOfLine();

    /**
     * Adds a start of input anchor. This is the same as {@code \A}.
     * @return This builder.
     */
    T startOfInput();

    /**
     * Adds an end of line anchor. This is the same as {@code $}.
     * <p>
     * Note that this only works if the flag {@link PatternFlag#MULTILINE} is enabled. This is done automatically when
     * using this method. If you want to match the end of the input instead, please use {@link #endOfInput()}.
     * @return This builder.
     */
    T endOfLine();

    /**
     * Adds end of input anchor. This is the same as {@code \z}.
     * @return This builder.
     */
    T endOfInput();
}
