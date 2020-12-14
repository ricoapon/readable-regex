package com.apon.readableregex.internal;

import com.apon.readableregex.IncorrectConstructionException;
import com.apon.readableregex.ReadableRegex;
import com.apon.readableregex.ReadableRegexPattern;

import java.util.Objects;
import java.util.regex.Pattern;

public class ReadableRegexImpl implements ReadableRegex {
    /** The internal regular expression. This field should only be modified using the {@link #regexFromString(String)} method. */
    private final StringBuilder regexBuilder = new StringBuilder();

    /** Indicates if the previous expression was a quantifier. Start with true, because you cannot start with a quantifier. */
    private boolean previousExpressionWasQuantifier = true;

    @Override
    public ReadableRegexPattern build() {
        Pattern pattern = Pattern.compile(regexBuilder.toString());
        return new ReadableRegexPatternImpl(pattern);
    }

    @Override
    public ReadableRegex regexFromString(String regex) {
        Objects.requireNonNull(regex);
        regexBuilder.append(regex);
        return this;
    }

    @Override
    public ReadableRegex add(ReadableRegex regexBuilder) {
        Objects.requireNonNull(regexBuilder);
        checkAndSetForStandaloneBlockExpression();
        String regexToInclude = regexBuilder.build().toString();

        // Wrap in an unnamed group, to make sure that quantifiers work on the entire block.
        return regexFromString("(?:" + regexToInclude + ")");
    }

    @Override
    public ReadableRegex literal(String literalValue) {
        Objects.requireNonNull(literalValue);
        checkAndSetForStandaloneBlockExpression();
        // Surround input with \Q\E to make sure that all the meta characters are escaped.
        // Wrap in an unnamed group, to make sure that quantifiers work on the entire block.
        return regexFromString("(?:\\Q" + literalValue + "\\E)");
    }

    @Override
    public ReadableRegex digit() {
        checkAndSetForStandaloneBlockExpression();
        return regexFromString("\\d");
    }

    @Override
    public ReadableRegex whitespace() {
        checkAndSetForStandaloneBlockExpression();
        return regexFromString("\\s");
    }

    @Override
    public ReadableRegex oneOrMore() {
        checkAndSetQuantifierExpression();
        return regexFromString("+");
    }

    @Override
    public ReadableRegex optional() {
        checkAndSetQuantifierExpression();
        return regexFromString("?");
    }

    /**
     * Do the needed checks for quantifier expression and indicate that the last executed expression is a quantifier expression.
     */
    private void checkAndSetQuantifierExpression() {
        if (previousExpressionWasQuantifier) {
            throw new IncorrectConstructionException("You cannot add a quantifier after a quantifier. Remove one of the incorrect quantifiers. " +
                    "Or, if you haven't done anything yet, you started with a quantifier. That is not possible.");
        }
        previousExpressionWasQuantifier = true;
    }

    /**
     * Do the needed checks for standalone block expression and indicate that the last executed expression is a standalone block expression.
     */
    private void checkAndSetForStandaloneBlockExpression() {
        previousExpressionWasQuantifier = false;
    }
}
