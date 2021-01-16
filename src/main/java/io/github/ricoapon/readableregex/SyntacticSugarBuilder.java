package io.github.ricoapon.readableregex;

import static io.github.ricoapon.readableregex.ReadableRegex.regex;

/**
 * Builder interface with methods to enhance user experience using shortcut methods.
 */
public interface SyntacticSugarBuilder<T extends ReadableRegex<T>> extends StandaloneBlockBuilder<T>, QuantifierBuilder<T>, GroupBuilder<T> {
    /*
    START of methods related to standalone blocks.
     */

    /**
     * Matches a word. This is the same as {@code \w+}
     * <p>
     * Syntactic sugar for "{@link #wordCharacter()}.{@link #oneOrMore()}".
     * @return This builder.
     */
    default T word() {
        return wordCharacter().oneOrMore();
    }

    /**
     * Matches anything. This is the same as {@code .*}. Note that you need to enable the flag {@link PatternFlag#DOT_ALL}
     * to also match new lines with this method.
     * <p>
     * Syntactic sugar for "{@link #anyCharacter()}.{@link #zeroOrMore()}".
     * @return This builder.
     */
    default T anything() {
        return anyCharacter().zeroOrMore();
    }

    /**
     * Adds a universal line break. This is the same as {@code \r\n|\n}.
     * @return This builder.
     */
    default T lineBreak() {
        return oneOf(regex("\\r\\n?"), regex("\\n"));
    }

    /*
    START of methods related to groups.
     */

    /**
     * Adds a regular expression inside a group.
     * <p>
     * Syntactic sugar for "{@link #startGroup()}.{@link #add(ReadableRegex)}.{@link #endGroup()}".
     * {@code .startGroup().add(regexBuilder).endGroup()}.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default T group(ReadableRegex<?> regexBuilder) {
        return startGroup().add(regexBuilder).endGroup();
    }

    /**
     * Adds a regular expression inside a group.
     * <p>
     * Syntactic sugar for "{@link #startGroup(String)}.{@link #add(ReadableRegex)}.{@link #endGroup()}".
     * @param groupName    The name of the group.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default T group(String groupName, ReadableRegex<?> regexBuilder) {
        return startGroup(groupName).add(regexBuilder).endGroup();
    }

    /**
     * Adds a regular expression inside a positive lookbehind block.
     * <p>
     * Syntactic sugar for "{@link #startPositiveLookbehind()}.{@link #add(ReadableRegex)}.{@link #endGroup()}".
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default T positiveLookbehind(ReadableRegex<?> regexBuilder) {
        return startPositiveLookbehind().add(regexBuilder).endGroup();
    }

    /**
     * Adds a regular expression inside a positive lookbehind block.
     * <p>
     * Syntactic sugar for "{@link #startNegativeLookbehind()}.{@link #add(ReadableRegex)}.{@link #endGroup()}".
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default T negativeLookbehind(ReadableRegex<?> regexBuilder) {
        return startNegativeLookbehind().add(regexBuilder).endGroup();
    }

    /**
     * Adds a regular expression inside a positive lookbehind block.
     * <p>
     * Syntactic sugar for "{@link #startPositiveLookahead()}.{@link #add(ReadableRegex)}.{@link #endGroup()}".
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default T positiveLookahead(ReadableRegex<?> regexBuilder) {
        return startPositiveLookahead().add(regexBuilder).endGroup();
    }

    /**
     * Adds a regular expression inside a positive lookbehind block.
     * <p>
     * Syntactic sugar for "{@link #startNegativeLookahead()}.{@link #add(ReadableRegex)}.{@link #endGroup()}".
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default T negativeLookahead(ReadableRegex<?> regexBuilder) {
        return startNegativeLookahead().add(regexBuilder).endGroup();
    }
}
