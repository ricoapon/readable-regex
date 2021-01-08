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
     * Starts a new capturing group with a given name. This is the same as {@code (<name>}. You must call {@link #endGroup()}
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
     * Ends the last group that started. This is the same as {@code )}. This method cannot be used before starting a group.
     * @return This builder.
     */
    ReadableRegex endGroup();
}
