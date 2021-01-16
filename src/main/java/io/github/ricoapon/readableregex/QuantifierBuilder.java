package io.github.ricoapon.readableregex;

/**
 * Builder interface with all the methods that add quantifiers to standalone blocks.
 */
public interface QuantifierBuilder<T extends ReadableRegex<T>> {
    /**
     * Makes the previous block repeat one or more times (greedy). This is the same as adding {@code +}.
     * @return This builder.
     */
    T oneOrMore();

    /**
     * Makes the previous block optional (greedy). This is the same as adding {@code ?}.
     * @return This builder.
     */
    T optional();

    /**
     * Makes the previous block repeat zero or more times (greedy). This is the same as adding {@code *}.
     * @return This builder.
     */
    T zeroOrMore();

    /**
     * Makes the previous block repeat exactly n times (greedy). This is the same as adding {@code {n}}.
     * @param n The number of times the block should repeat.
     * @return This builder.
     */
    T exactlyNTimes(int n);

    /**
     * Makes the previous block repeat at least n times (greedy). This is the same as adding {@code {n,}}.
     * @param n The minimum number of times the block should repeat.
     * @return This builder.
     */
    T atLeastNTimes(int n);

    /**
     * Makes the previous block repeat between n and m times (greedy). This is the same as adding {@code {n, m}}.
     * @param n The minimum number of times the block should repeat.
     * @param m The maximum number of times the block should repeat.
     * @return This builder.
     */
    T betweenNAndMTimes(int n, int m);

    /**
     * Makes the previous block repeat at most n times (greedy). This is the same as adding {@code {0, n}}.
     * @param n The maximum number of times the block should repeat.
     * @return This builder.
     */
    default T atMostNTimes(int n) {
        return betweenNAndMTimes(0, n);
    }

    /**
     * Makes the previous quantifier reluctant. This is the same as adding {@code ?}.
     * @return This builder.
     */
    T reluctant();

    /**
     * Makes the previous quantifier possessive. This is the same as adding {@code +}.
     * @return This builder.
     */
    T possessive();
}
