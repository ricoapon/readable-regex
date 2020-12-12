package com.apon.readableregex.internal;

import com.apon.readableregex.ReadableRegexPattern;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadableRegexPatternImpl implements ReadableRegexPattern {
    private final Pattern pattern;

    ReadableRegexPatternImpl(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher matches(String text) {
        return pattern.matcher(text);
    }

    @Override
    public Pattern getUnderlyingPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return pattern.toString();
    }
}
