package com.apon.readableregex.internal;

import com.apon.readableregex.ReadableRegex;
import com.apon.readableregex.ReadableRegexPattern;

import java.util.Objects;
import java.util.regex.Pattern;

public class ReadableRegexImpl implements ReadableRegex {
    /** The internal regular expression. This field should only be modified using the {@link #regexFromString(String)} method. */
    private final StringBuilder regexBuilder = new StringBuilder();

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
    public ReadableRegex literal(String literalValue) {
        // Surround input with \Q\E to make sure that all the meta characters are escaped.
        // Surround it with (?:) to make sure that
        return regexFromString("(?:\\Q" + literalValue + "\\E)");
    }

    @Override
    public ReadableRegex digit() {
        return regexFromString("\\d");
    }

    @Override
    public ReadableRegex whitespace() {
        return regexFromString("\\s");
    }

    @Override
    public ReadableRegex oneOrMore() {
        return regexFromString("+");
    }

    @Override
    public ReadableRegex optional() {
        return regexFromString("?");
    }
}
