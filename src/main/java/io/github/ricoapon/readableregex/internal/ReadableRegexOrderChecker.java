package io.github.ricoapon.readableregex.internal;

import io.github.ricoapon.readableregex.PatternFlag;
import io.github.ricoapon.readableregex.ReadableRegex;
import io.github.ricoapon.readableregex.ReadableRegexPattern;

import static io.github.ricoapon.readableregex.internal.MethodOrderChecker.Method.*;

/**
 * Subclass of {@link ReadableRegexBuilder} to check whether methods are called in the right order using an instance
 * of {@link MethodOrderChecker}.
 */
public class ReadableRegexOrderChecker extends ReadableRegexBuilder {
    /** Object for maintaining the status of calling methods. */
    private final MethodOrderChecker methodOrderChecker;

    public ReadableRegexOrderChecker(MethodOrderChecker methodOrderChecker) {
        this.methodOrderChecker = methodOrderChecker;
    }

    @Override
    public ReadableRegexPattern buildWithFlags(PatternFlag... patternFlags) {
        methodOrderChecker.checkCallingMethod(FINISH);
        return super.buildWithFlags(patternFlags);
    }

    @Override
    public ReadableRegex regexFromString(String regex) {
        // We are not actually sure that the regex is a standalone block. If we don't do this however, it is never possible
        // to add a quantifier after this block. I leave the user responsible for the outcome.
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.regexFromString(regex);
    }

    @Override
    public ReadableRegex add(ReadableRegex regexBuilder) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.add(regexBuilder);
    }

    @Override
    public ReadableRegex literal(String literalValue) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.literal(literalValue);
    }

    @Override
    public ReadableRegex digit() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.digit();
    }

    @Override
    public ReadableRegex whitespace() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.whitespace();
    }

    @Override
    public ReadableRegex oneOrMore() {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.oneOrMore();
    }

    @Override
    public ReadableRegex optional() {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.optional();
    }

    @Override
    public ReadableRegex startGroup() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startGroup();
    }

    @Override
    public ReadableRegex startGroup(String groupName) {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startGroup(groupName);
    }

    @Override
    public ReadableRegex startPositiveLookbehind() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startPositiveLookbehind();
    }

    @Override
    public ReadableRegex startNegativeLookbehind() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startNegativeLookbehind();
    }

    @Override
    public ReadableRegex startPositiveLookahead() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startPositiveLookahead();
    }

    @Override
    public ReadableRegex startNegativeLookahead() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startNegativeLookahead();
    }

    @Override
    public ReadableRegex endGroup() {
        methodOrderChecker.checkCallingMethod(END_GROUP);
        return super.endGroup();
    }

    @Override
    public ReadableRegex group(ReadableRegex regexBuilder) {
        // Implementation calls other methods. The order is checked in those methods.
        return super.group(regexBuilder);
    }

    @Override
    public ReadableRegex group(String groupName, ReadableRegex regexBuilder) {
        // Implementation calls other methods. The order is checked in those methods.
        return super.group(groupName, regexBuilder);
    }
}
