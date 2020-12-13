package com.apon.readableregex;

/**
 * Builder interface with all the methods that adds quantifiers to standalone blocks.
 */
public interface QuantifierBuilder extends StandaloneBlockBuilder {
    /**
     * Makes the previous block repeat one or more times (greedy). This is the same as adding {@code +}.
     * @return This builder.
     */
    StandaloneBlockBuilder oneOrMore();
}
