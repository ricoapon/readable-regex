package com.apon.readableregex.internal;

import com.apon.readableregex.IncorrectConstructionException;

/**
 * Class for maintaining the status of a building a regular expression and checking whether executing methods of
 * {@link com.apon.readableregex.ReadableRegex} is valid.
 */
public class MethodOrderChecker {
    /** The types of methods that can be executed by {@link com.apon.readableregex.ReadableRegex}. */
    enum Method {
        /** Methods from the interface {@link com.apon.readableregex.StandaloneBlockBuilder}. */
        STANDALONE_BLOCK,
        /** Methods from the interface {@link com.apon.readableregex.QuantifierBuilder}. */
        QUANTIFIER
    }

    /** Indicates if the previous expression was a quantifier. Start with true, because you cannot start with a quantifier. */
    private boolean previousExpressionWasQuantifier = true;

    /**
     * Checks if a method can be called. If not, it will throw an {@link IncorrectConstructionException}.
     * @param method The method to execute.
     */
    public void checkCallingMethod(Method method) {
        switch (method) {
            case STANDALONE_BLOCK: standaloneBlock(); break;
            case QUANTIFIER: quantifier(); break;
        }
    }

    private void quantifier() {
        if (previousExpressionWasQuantifier) {
            throw new IncorrectConstructionException("You cannot add a quantifier after a quantifier. Remove one of the incorrect quantifiers. " +
                    "Or, if you haven't done anything yet, you started with a quantifier. That is not possible.");
        }
        previousExpressionWasQuantifier = true;
    }

    private void standaloneBlock() {
        previousExpressionWasQuantifier = false;
    }
}
