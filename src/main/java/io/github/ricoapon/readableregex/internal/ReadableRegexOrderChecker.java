package io.github.ricoapon.readableregex.internal;

import io.github.ricoapon.readableregex.PatternFlag;
import io.github.ricoapon.readableregex.ReadableRegex;
import io.github.ricoapon.readableregex.ReadableRegexPattern;

import static io.github.ricoapon.readableregex.internal.MethodOrderChecker.Method.*;

/**
 * Subclass of {@link ReadableRegexBuilder} to check whether methods are called in the right order using an instance
 * of {@link MethodOrderChecker}.
 */
public class ReadableRegexOrderChecker<T extends ReadableRegex<T>> extends ReadableRegexBuilder<T> {
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
    public T regexFromString(String regex) {
        // We are not actually sure that the regex is a standalone block. If we don't do this however, it is never possible
        // to add a quantifier after this block. I leave the user responsible for the outcome.
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.regexFromString(regex);
    }

    @Override
    public T add(ReadableRegexPattern pattern) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.add(pattern);
    }

    @Override
    public T literal(String literalValue) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.literal(literalValue);
    }

    @Override
    public T digit() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.digit();
    }

    @Override
    public T whitespace() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.whitespace();
    }

    @Override
    public T tab() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.tab();
    }

    @Override
    public T oneOf(ReadableRegex<?>... regexBuilders) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.oneOf(regexBuilders);
    }

    @Override
    public T range(char... boundaries) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.range(boundaries);
    }

    @Override
    public T notInRange(char... boundaries) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.notInRange(boundaries);
    }

    @Override
    public T anyCharacterOf(String characters) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.anyCharacterOf(characters);
    }

    @Override
    public T anyCharacterExcept(String characters) {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.anyCharacterExcept(characters);
    }

    @Override
    public T wordCharacter() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.wordCharacter();
    }

    @Override
    public T nonWordCharacter() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.nonWordCharacter();
    }

    @Override
    public T wordBoundary() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.wordBoundary();
    }

    @Override
    public T nonWordBoundary() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.nonWordBoundary();
    }

    @Override
    public T anyCharacter() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.anyCharacter();
    }

    @Override
    public T startOfLine() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.startOfLine();
    }

    @Override
    public T startOfInput() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.startOfInput();
    }

    @Override
    public T endOfLine() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.endOfLine();
    }

    @Override
    public T endOfInput() {
        methodOrderChecker.checkCallingMethod(STANDALONE_BLOCK);
        return super.endOfInput();
    }

    @Override
    public T oneOrMore() {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.oneOrMore();
    }

    @Override
    public T optional() {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.optional();
    }

    @Override
    public T zeroOrMore() {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.zeroOrMore();
    }

    @Override
    public T exactlyNTimes(int n) {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.exactlyNTimes(n);
    }

    @Override
    public T atLeastNTimes(int n) {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.atLeastNTimes(n);
    }

    @Override
    public T betweenNAndMTimes(int n, int m) {
        methodOrderChecker.checkCallingMethod(QUANTIFIER);
        return super.betweenNAndMTimes(n, m);
    }

    @Override
    public T reluctant() {
        methodOrderChecker.checkCallingMethod(RELUCTANT_OR_POSSESSIVE);
        return super.reluctant();
    }

    @Override
    public T possessive() {
        methodOrderChecker.checkCallingMethod(RELUCTANT_OR_POSSESSIVE);
        return super.possessive();
    }

    @Override
    public T startGroup() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startGroup();
    }

    @Override
    public T startGroup(String groupName) {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startGroup(groupName);
    }

    @Override
    public T startUnnamedGroup() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startUnnamedGroup();
    }

    @Override
    public T startPositiveLookbehind() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startPositiveLookbehind();
    }

    @Override
    public T startNegativeLookbehind() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startNegativeLookbehind();
    }

    @Override
    public T startPositiveLookahead() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startPositiveLookahead();
    }

    @Override
    public T startNegativeLookahead() {
        methodOrderChecker.checkCallingMethod(START_GROUP);
        return super.startNegativeLookahead();
    }

    @Override
    public T endGroup() {
        methodOrderChecker.checkCallingMethod(END_GROUP);
        return super.endGroup();
    }
}
