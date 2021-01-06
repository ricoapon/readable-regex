package io.github.ricoapon.readableregex;

/**
 * Builder interface with all the methods related to groups.
 */
public interface GroupBuilder {
    /**
     * Starts a new capturing group. This is the same as {@code (}. You must call {@link #endGroup()} somewhere after this method.
     * @return This builder.
     */
    ReadableRegex startGroup();

    /**
     * Starts a new capturing group for a given name. This is the same as {@code (<name>}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @param groupName The name of the group.
     * @return This builder.
     */
    ReadableRegex startGroup(String groupName);

    /**
     * Starts a non-capturing group. This is the same as {@code (?:}. You must call {@link #endGroup()} somewhere after
     * this method.
     * @return This builder.
     */
    ReadableRegex startUnnamedGroup();

    /**
     * Starts a non-capturing group for positive lookbehind. This is the same as {@code (?<=}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @return This builder.
     */
    ReadableRegex startPositiveLookbehind();

    /**
     * Starts a non-capturing group for negative lookbehind. This is the same as {@code (?<!}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @return This builder.
     */
    ReadableRegex startNegativeLookbehind();

    /**
     * Starts a non-capturing group for positive lookahead. This is the same as {@code (?=}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @return This builder.
     */
    ReadableRegex startPositiveLookahead();

    /**
     * Starts a non-capturing group for negative lookahead. This is the same as {@code (?!}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @return This builder.
     */
    ReadableRegex startNegativeLookahead();

    /**
     * Ends the last group that started, this includes lookbehind. This is the same as {@code )}. This method cannot be
     * used before starting a group.
     * @return This builder.
     */
    ReadableRegex endGroup();

    /**
     * Syntactic sugar for {@code .startGroup().add(regexBuilder).endGroup()}.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    ReadableRegex group(ReadableRegex regexBuilder);

    /**
     * Syntactic sugar for {@code .startGroup(groupName).add(regexBuilder).endGroup()}.
     * @param groupName    The name of the group.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    ReadableRegex group(String groupName, ReadableRegex regexBuilder);

    /**
     * Syntactic sugar for {@code .startPositiveLookbehind().add(regexBuilder).endGroup()}.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default ReadableRegex positiveLookbehind(ReadableRegex regexBuilder) {
        return startPositiveLookbehind().add(regexBuilder).endGroup();
    }

    /**
     * Syntactic sugar for {@code .startNegativeLookbehind().add(regexBuilder).endGroup()}.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default ReadableRegex negativeLookbehind(ReadableRegex regexBuilder) {
        return startNegativeLookbehind().add(regexBuilder).endGroup();
    }

    /**
     * Syntactic sugar for {@code .startPositiveLookahead().add(regexBuilder).endGroup()}.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default ReadableRegex positiveLookahead(ReadableRegex regexBuilder) {
        return startPositiveLookahead().add(regexBuilder).endGroup();
    }

    /**
     * Syntactic sugar for {@code .startNegativeLookahead().add(regexBuilder).endGroup()}.
     * @param regexBuilder The regular expression.
     * @return This builder.
     */
    default ReadableRegex negativeLookahead(ReadableRegex regexBuilder) {
        return startNegativeLookahead().add(regexBuilder).endGroup();
    }
}
