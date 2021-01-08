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

    /**
     * Constructor.
     * @param methodOrderChecker Object for checking that methods are called in the right order.
     */
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
    public ReadableRegex add(ReadableRegexPattern pattern) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.add(pattern);
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
    public ReadableRegex oneOf(ReadableRegex... regexBuilders) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.oneOf(regexBuilders);
    }

    @Override
    public ReadableRegex range(char... boundaries) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.range(boundaries);
    }

    @Override
    public ReadableRegex notInRange(char... boundaries) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.notInRange(boundaries);
    }

    @Override
    public ReadableRegex anyCharacterOf(String characters) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.anyCharacterOf(characters);
    }

    @Override
    public ReadableRegex anyCharacterExcept(String characters) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.anyCharacterExcept(characters);
    }

    @Override
    public ReadableRegex wordCharacter() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.wordCharacter();
    }

    @Override
    public ReadableRegex nonWordCharacter() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.nonWordCharacter();
    }

    @Override
    public ReadableRegex wordBoundary() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.wordBoundary();
    }

    @Override
    public ReadableRegex nonWordBoundary() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.nonWordBoundary();
    }

    @Override
    public ReadableRegex anyCharacter() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.anyCharacter();
    }

    @Override
    public ReadableRegex startOfLine() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.startOfLine();
    }

    @Override
    public ReadableRegex startOfInput() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.startOfInput();
    }

    @Override
    public ReadableRegex endOfLine() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.endOfLine();
    }

    @Override
    public ReadableRegex endOfInput() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.endOfInput();
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
    public ReadableRegex zeroOrMore() {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.zeroOrMore();
    }

    @Override
    public ReadableRegex exactlyNTimes(int n) {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.exactlyNTimes(n);
    }

    @Override
    public ReadableRegex atLeastNTimes(int n) {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.atLeastNTimes(n);
    }

    @Override
    public ReadableRegex betweenNAndMTimes(int n, int m) {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.betweenNAndMTimes(n, m);
    }

    @Override
    public ReadableRegex reluctant() {
        methodOrderChecker.checkCallingMethod(RELUCTANT_OR_POSSESSIVE);
        return super.reluctant();
    }

    @Override
    public ReadableRegex possessive() {
        methodOrderChecker.checkCallingMethod(RELUCTANT_OR_POSSESSIVE);
        return super.possessive();
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
    public ReadableRegex startUnnamedGroup() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startUnnamedGroup();
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
