package io.github.ricoapon.readableregex;

/**
 * Builder interface with all the methods related to groups.
 */
public interface GroupBuilder<T extends ReadableRegex<T>> {
    /**
     * Starts a new capturing group. This is the same as {@code (}. You must call {@link #endGroup()} somewhere after this method.
     * @return This builder.
     */
    T startGroup();

    /**
     * Starts a new capturing group with a given name. This is the same as {@code (<name>}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @param groupName The name of the group.
     * @return This builder.
     */
    T startGroup(String groupName);

    /**
     * Starts a non-capturing group. This is the same as {@code (?:}. You must call {@link #endGroup()} somewhere after
     * this method.
     * @return This builder.
     */
    T startUnnamedGroup();

    /**
     * Starts a non-capturing group for positive lookbehind. This is the same as {@code (?<=}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @return This builder.
     */
    T startPositiveLookbehind();

    /**
     * Starts a non-capturing group for negative lookbehind. This is the same as {@code (?<!}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @return This builder.
     */
    T startNegativeLookbehind();

    /**
     * Starts a non-capturing group for positive lookahead. This is the same as {@code (?=}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @return This builder.
     */
    T startPositiveLookahead();

    /**
     * Starts a non-capturing group for negative lookahead. This is the same as {@code (?!}. You must call {@link #endGroup()}
     * somewhere after this method.
     * @return This builder.
     */
    T startNegativeLookahead();

    /**
     * Ends the last group that started. This is the same as {@code )}. This method cannot be used before starting a group.
     * @return This builder.
     */
    T endGroup();
}
