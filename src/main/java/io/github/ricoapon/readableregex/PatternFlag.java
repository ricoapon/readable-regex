package io.github.ricoapon.readableregex;

import java.util.regex.Pattern;

/**
 * Enum representing all the possible flags of {@link java.util.regex.Pattern} that are useful for this library.
 */
public enum PatternFlag {
    /** See {@link Pattern#CASE_INSENSITIVE}. */
    CASE_INSENSITIVE(Pattern.CASE_INSENSITIVE),

    /** See {@link Pattern#MULTILINE}. */
    MULTILINE(Pattern.MULTILINE),

    /** See {@link Pattern#DOTALL}. */
    DOT_ALL(Pattern.DOTALL);

    /** The integer that is used for setting the flag on {@link java.util.regex.Pattern}. */
    private final int jdkPatternFlagCode;

    PatternFlag(int jdkPatternFlagCode) {
        this.jdkPatternFlagCode = jdkPatternFlagCode;
    }

    public int getJdkPatternFlagCode() {
        return jdkPatternFlagCode;
    }
}
