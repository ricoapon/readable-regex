package com.apon.readableregex.internal;

import com.apon.readableregex.QuantifierBuilder;
import com.apon.readableregex.ReadableRegex;
import com.apon.readableregex.ReadableRegexPattern;

import java.util.Objects;
import java.util.regex.Pattern;

public class ReadableRegexImpl implements ReadableRegex, QuantifierBuilder {
    /** The internal regular expression. This field should only be modified using the {@link #regexFromString(String)} method. */
    private final StringBuilder regexBuilder = new StringBuilder();

    @Override
    public ReadableRegexPattern build() {
        Pattern pattern = Pattern.compile(regexBuilder.toString());
        return new ReadableRegexPatternImpl(pattern);
    }

    @Override
    public QuantifierBuilder regexFromString(String regex) {
        Objects.requireNonNull(regex);
        regexBuilder.append(regex);
        return this;
    }

    @Override
    public QuantifierBuilder literal(String literalValue) {
        // Surround input with \Q\E to make sure that all the meta characters are escaped.
        // Surround it with (?:) to make sure that
        return regexFromString("(?:\\Q" + literalValue + "\\E)");
    }

    @Override
    public QuantifierBuilder digit() {
        return regexFromString("\\d");
    }

    @Override
    public QuantifierBuilder whitespace() {
        return regexFromString("\\s");
    }
}
