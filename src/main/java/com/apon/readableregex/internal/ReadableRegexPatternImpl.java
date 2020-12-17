package com.apon.readableregex.internal;

import com.apon.readableregex.PatternFlag;
import com.apon.readableregex.ReadableRegexPattern;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadableRegexPatternImpl implements ReadableRegexPattern {
    private final Pattern pattern;
    private final Set<PatternFlag> patternFlags;

    ReadableRegexPatternImpl(Pattern pattern, Set<PatternFlag> patternFlags) {
        this.pattern = pattern;
        this.patternFlags = Collections.unmodifiableSet(patternFlags);
    }

    @Override
    public Matcher matches(String text) {
        return pattern.matcher(text);
    }

    @Override
    public Set<PatternFlag> enabledFlags() {
        return patternFlags;
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
