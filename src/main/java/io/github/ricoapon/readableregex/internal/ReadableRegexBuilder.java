package io.github.ricoapon.readableregex.internal;

import io.github.ricoapon.readableregex.PatternFlag;
import io.github.ricoapon.readableregex.ReadableRegex;
import io.github.ricoapon.readableregex.ReadableRegexPattern;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class ReadableRegexBuilder implements ReadableRegex {
    /** The internal regular expression. This field should only be modified using the {@link #_addRegex(String)} method. */
    private final StringBuilder regexBuilder = new StringBuilder();

    @SuppressWarnings("MagicConstant")
    @Override
    public ReadableRegexPattern buildWithFlags(PatternFlag... patternFlags) {
        int flags = Arrays.stream(patternFlags).map(PatternFlag::getJdkPatternFlagCode)
                .reduce(0, (integer, integer2) -> integer | integer2);
        Pattern pattern = Pattern.compile(regexBuilder.toString(), flags);
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
    public ReadableRegex oneOf(ReadableRegex... regexBuilders) {
        String middlePart = Arrays.stream(regexBuilders)
                .map(ReadableRegex::build)
                .map(ReadableRegexPattern::toString)
                .collect(Collectors.joining("|"));

        _addRegex("(?:");
        _addRegex(middlePart);
        return _addRegex(")");
    }

    @Override
    public ReadableRegex range(char... boundaries) {
        if (boundaries.length % 2 != 0) {
            throw new IllegalArgumentException("You have to supply an even amount of boundaries.");
        } else if (boundaries.length == 0) {
            throw new IllegalArgumentException("An empty range is pointless. Please supply boundaries!");
        }

        StringBuilder expression = new StringBuilder("[");
        for (int i = 0; i < boundaries.length; i += 2) {
            expression.append(boundaries[i])
                    .append('-')
                    .append(boundaries[i + 1]);
        }
        expression.append("]");

        return _addRegex(expression.toString());
    }

    @Override
    public ReadableRegex notInRange(char... boundaries) {
        if (boundaries.length % 2 != 0) {
            throw new IllegalArgumentException("You have to supply an even amount of boundaries.");
        } else if (boundaries.length == 0) {
            throw new IllegalArgumentException("An empty range is pointless. Please supply boundaries!");
        }

        StringBuilder expression = new StringBuilder("[^");
        for (int i = 0; i < boundaries.length; i += 2) {
            expression.append(boundaries[i])
                    .append('-')
                    .append(boundaries[i + 1]);
        }
        expression.append("]");

        return _addRegex(expression.toString());
    }

    @Override
    public ReadableRegex anyCharacterOf(String characters) {
        Objects.requireNonNull(characters);
        if (characters.length() == 0) {
            throw new IllegalArgumentException("An empty range is pointless. Please supply boundaries!");
        }

        return _addRegex("[" + characters + "]");
    }

    @Override
    public ReadableRegex anyCharacterExcept(String characters) {
        Objects.requireNonNull(characters);
        if (characters.length() == 0) {
            throw new IllegalArgumentException("An empty range is pointless. Please supply boundaries!");
        }

        return _addRegex("[^" + characters + "]");
    }

    @Override
    public ReadableRegex wordCharacter() {
        return _addRegex("\\w");
    }

    @Override
    public ReadableRegex nonWordCharacter() {
        return _addRegex("\\W");
    }

    @Override
    public ReadableRegex wordBoundary() {
        return _addRegex("\\b");
    }

    @Override
    public ReadableRegex nonWordBoundary() {
        return _addRegex("\\B");
    }

    @Override
    public ReadableRegex oneOrMore() {
        return _addRegex("+");
    }

    @Override
    public ReadableRegex optional() {
        return _addRegex("?");
    }

    @Override
    public ReadableRegex zeroOrMore() {
        return _addRegex("*");
    }

    private ReadableRegex _countRange(int n, Integer m) {
        if (n < 0 || m <= 0) {
            throw new IllegalArgumentException("The number of times the block should repeat must be larger than zero.");
        } else if (n > m) {
            throw new IllegalArgumentException("The ranges of the repeating block must be valid. Please make n smaller than m.");
        }

        if (Integer.MAX_VALUE == m) {
            return _addRegex("{" + n + ",}");
        }

        return _addRegex("{" + n + "," + m + "}");
    }

    @Override
    public ReadableRegex exactlyNTimes(int n) {
        return _countRange(n, n);
    }

    @Override
    public ReadableRegex atLeastNTimes(int n) {
        return _countRange(n, Integer.MAX_VALUE);
    }

    @Override
    public ReadableRegex betweenNAndMTimes(int n, int m) {
        return _countRange(n, m);
    }

    @Override
    public ReadableRegex startGroup() {
        return _addRegex("(");
    }

    @Override
    public ReadableRegex startGroup(String groupName) {
        Objects.requireNonNull(groupName);
        if (!Pattern.matches("[a-zA-Z][a-zA-Z0-9]*", groupName)) {
            throw new IllegalArgumentException("The group name '" + groupName + "' is not valid: it should start with a letter " +
                    "and only contain letters and digits.");
        }

        return _addRegex("(?<" + groupName + ">");
    }

    @Override
    public ReadableRegex startPositiveLookbehind() {
        return _addRegex("(?<=");
    }

    @Override
    public ReadableRegex startNegativeLookbehind() {
        return _addRegex("(?<!");
    }

    @Override
    public ReadableRegex startPositiveLookahead() {
        return _addRegex("(?=");
    }

    @Override
    public ReadableRegex startNegativeLookahead() {
        return _addRegex("(?!");
    }

    @Override
    public ReadableRegex endGroup() {
        return _addRegex(")");
    }

    @Override
    public ReadableRegex group(ReadableRegex regexBuilder) {
        return startGroup().add(regexBuilder).endGroup();
    }

    @Override
    public ReadableRegex group(String groupName, ReadableRegex regexBuilder) {
        return startGroup(groupName).add(regexBuilder).endGroup();
    }
}
