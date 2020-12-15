package com.apon.readableregex.internal;

import com.apon.readableregex.ReadableRegex;
import com.apon.readableregex.ReadableRegexPattern;

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
    public ReadableRegexPattern build() {
        return super.build();
    }

    @Override
    public ReadableRegex regexFromString(String regex) {
        // We are not actually sure that the regex is a standalone block. If we don't do this however, it is never possible
        // to add a quantifier after this block. I leave the user responsible for the outcome.
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.STANDALONE_BLOCK);
        return super.regexFromString(regex);
    }

    @Override
    public ReadableRegex add(ReadableRegex regexBuilder) {
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.STANDALONE_BLOCK);
        return super.add(regexBuilder);
    }

    @Override
    public ReadableRegex literal(String literalValue) {
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.STANDALONE_BLOCK);
        return super.literal(literalValue);
    }

    @Override
    public ReadableRegex digit() {
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.STANDALONE_BLOCK);
        return super.digit();
    }

    @Override
    public ReadableRegex whitespace() {
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.STANDALONE_BLOCK);
        return super.whitespace();
    }

    @Override
    public ReadableRegex oneOrMore() {
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.QUANTIFIER);
        return super.oneOrMore();
    }

    @Override
    public ReadableRegex optional() {
        methodOrderChecker.checkCallingMethod(MethodOrderChecker.Method.QUANTIFIER);
        return super.optional();
    }

    @Override
    public ReadableRegex startGroup() {
        return super.startGroup();
    }

    @Override
    public ReadableRegex startGroup(String groupName) {
        return super.startGroup(groupName);
    }

    @Override
    public ReadableRegex endGroup() {
        return super.endGroup();
    }

    @Override
    public ReadableRegex group(ReadableRegex regexBuilder) {
        return super.group(regexBuilder);
    }

    @Override
    public ReadableRegex group(String groupName, ReadableRegex regexBuilder) {
        return super.group(groupName, regexBuilder);
    }
}
