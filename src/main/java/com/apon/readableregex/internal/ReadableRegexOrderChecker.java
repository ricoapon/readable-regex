package com.apon.readableregex.internal;

import com.apon.readableregex.IncorrectConstructionException;
import com.apon.readableregex.ReadableRegex;
import com.apon.readableregex.ReadableRegexPattern;

/**
 * Subclass of {@link ReadableRegexBuilder} to check whether methods are called in the right order. If any order constraint is
 * violated, an {@link IncorrectConstructionException} is thrown. If no violations are detected, the method is delegated to {@link ReadableRegexBuilder}.
 */
public class ReadableRegexOrderChecker extends ReadableRegexBuilder {
    /** Indicates if the previous expression was a quantifier. Start with true, because you cannot start with a quantifier. */
    private boolean previousExpressionWasQuantifier = true;

    /**
     * The method that is executed adds a quantifier.
     */
    private void quantifier() {
        if (previousExpressionWasQuantifier) {
            throw new IncorrectConstructionException("You cannot add a quantifier after a quantifier. Remove one of the incorrect quantifiers. " +
                    "Or, if you haven't done anything yet, you started with a quantifier. That is not possible.");
        }
        previousExpressionWasQuantifier = true;
    }

    /**
     * The method that is executed adds a standalone block.
     */
    private void standaloneBlock() {
        previousExpressionWasQuantifier = false;
    }

    @Override
    public ReadableRegexPattern build() {
        return super.build();
    }

    @Override
    public ReadableRegex regexFromString(String regex) {
        // We are not actually sure that the regex is a standalone block. If we don't do this however, it is never possible
        // to add a quantifier after this block. I leave the user responsible for the outcome.
        standaloneBlock();
        return super.regexFromString(regex);
    }

    @Override
    public ReadableRegex add(ReadableRegex regexBuilder) {
        standaloneBlock();
        return super.add(regexBuilder);
    }

    @Override
    public ReadableRegex literal(String literalValue) {
        standaloneBlock();
        return super.literal(literalValue);
    }

    @Override
    public ReadableRegex digit() {
        standaloneBlock();
        return super.digit();
    }

    @Override
    public ReadableRegex whitespace() {
        standaloneBlock();
        return super.whitespace();
    }

    @Override
    public ReadableRegex oneOrMore() {
        quantifier();
        return super.oneOrMore();
    }

    @Override
    public ReadableRegex optional() {
        quantifier();
        return super.optional();
    }
}
