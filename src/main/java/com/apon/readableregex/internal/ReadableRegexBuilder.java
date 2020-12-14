package com.apon.readableregex.internal;

import com.apon.readableregex.ReadableRegex;
import com.apon.readableregex.ReadableRegexPattern;

import java.util.Objects;
import java.util.regex.Pattern;

public abstract class ReadableRegexBuilder implements ReadableRegex {
    /** The internal regular expression. This field should only be modified using the {@link #_addRegex(String)} method. */
    private final StringBuilder regexBuilder = new StringBuilder();

    @Override
    public ReadableRegexPattern build() {
        Pattern pattern = Pattern.compile(regexBuilder.toString());
        return new ReadableRegexPatternImpl(pattern);
    }

    /**
     * Adds the regular expression to {@link #regexBuilder}.
     * @param regex The regular expression.
     * @return This builder.
     */
    private ReadableRegex _addRegex(String regex) {
        Objects.requireNonNull(regex);
        regexBuilder.append(regex);
        return this;
    }

    @Override
    public ReadableRegex regexFromString(String regex) {
        return _addRegex(regex);
    }

    @Override
    public ReadableRegex add(ReadableRegex regexBuilder) {
        Objects.requireNonNull(regexBuilder);
        String regexToInclude = regexBuilder.build().toString();

        // Wrap in an unnamed group, to make sure that quantifiers work on the entire block.
        return _addRegex("(?:" + regexToInclude + ")");
    }

    @Override
    public ReadableRegex literal(String literalValue) {
        Objects.requireNonNull(literalValue);
        // Surround input with \Q\E to make sure that all the meta characters are escaped.
        // Wrap in an unnamed group, to make sure that quantifiers work on the entire block.
        return _addRegex("(?:\\Q" + literalValue + "\\E)");
    }

    @Override
    public ReadableRegex digit() {
        return _addRegex("\\d");
    }

    @Override
    public ReadableRegex whitespace() {
        return _addRegex("\\s");
    }

    @Override
    public ReadableRegex oneOrMore() {
        return _addRegex("+");
    }

    @Override
    public ReadableRegex optional() {
        return _addRegex("?");
    }
}
