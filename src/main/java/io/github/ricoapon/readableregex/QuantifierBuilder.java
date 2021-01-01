package io.github.ricoapon.readableregex;

/**
 * Builder interface with all the methods that adds quantifiers to standalone blocks.
 */
public interface QuantifierBuilder {
    /**
     * Makes the previous block repeat one or more times (greedy). This is the same as adding {@code +}.
     * @return This builder.
     */
    ReadableRegex oneOrMore();

    /**
     * Makes the previous block optional (greedy). This is the same as adding {@code ?}.
     * @return This builder.
     */
    ReadableRegex optional();
}
