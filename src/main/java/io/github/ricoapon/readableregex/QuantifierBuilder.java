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

    /**
     * Makes the previous block repeat zero or more times (greedy). This is the same as adding {@code *}.
     * @return This builder.
     */
    ReadableRegex zeroOrMore();

    /**
     * Makes the previous block repeat exactly n times (greedy). This is the same as adding {@code {n}}.
     * @param n The number of times the block should repeat.
     * @return This builder.
     */
    ReadableRegex exactlyNTimes(int n);

    /**
     * Makes the previous block repeat at least n times (greedy). This is the same as adding {@code {n,}}.
     * @param n The minimum number of times the block should repeat.
     * @return This builder.
     */
    ReadableRegex atLeastNTimes(int n);

    /**
     * Makes the previous block repeat between n and m times (greedy). This is the same as adding {@code {n, m}}.
     * @param n The minimum number of times the block should repeat.
     * @param m The maximum number of times the block should repeat.
     * @return This builder.
     */
    ReadableRegex betweenNAndMTimes(int n, int m);

    /**
     * Makes the previous block repeat at most n times (greedy). This is the same as adding {@code {0, n}}.
     * @param n The maximum number of times the block should repeat.
     * @return This builder.
     */
    default ReadableRegex atMostNTimes(int n) {
        return betweenNAndMTimes(0, n);
    }
}
