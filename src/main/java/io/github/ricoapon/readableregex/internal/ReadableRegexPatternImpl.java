package io.github.ricoapon.readableregex.internal;

import io.github.ricoapon.readableregex.PatternFlag;
import io.github.ricoapon.readableregex.ReadableRegexPattern;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ReadableRegexPattern}.
 */
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
    public Set<PatternFlag> enabledFlags() {
        return Arrays.stream(PatternFlag.values())
                .filter(flag -> (pattern.flags() & flag.getJdkPatternFlagCode()) != 0)
                .collect(Collectors.toSet());
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
