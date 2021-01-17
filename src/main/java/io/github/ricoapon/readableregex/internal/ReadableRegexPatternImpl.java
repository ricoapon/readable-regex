package io.github.ricoapon.readableregex.internal;

import io.github.ricoapon.readableregex.PatternFlag;
import io.github.ricoapon.readableregex.ReadableRegexPattern;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ReadableRegexPattern}.
 */
public class ReadableRegexPatternImpl implements ReadableRegexPattern {
    private final Pattern pattern;

    /** Maps group index to the name. If the name is null, it means it is an unnamed group. */
    private final List<String> groups;

    public ReadableRegexPatternImpl(Pattern pattern, List<String> groups) {
        this.pattern = pattern;
        this.groups = Collections.unmodifiableList(groups);
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
    public List<String> groups() {
        return groups;
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
