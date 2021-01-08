package io.github.ricoapon.readableregex;

import java.util.regex.Pattern;

/**
 * Enum representing all the possible flags of {@link java.util.regex.Pattern} that are useful for this library.
 * Flags can be enabled using the method {@link FinishBuilder#buildWithFlags(PatternFlag...)}.
 */
public enum PatternFlag {
    /** Enables case-insensitive matching. See {@link Pattern#CASE_INSENSITIVE} for more details. */
    CASE_INSENSITIVE(Pattern.CASE_INSENSITIVE),

    /** Enables multiline mode. See {@link Pattern#MULTILINE} for more details. */
    MULTILINE(Pattern.MULTILINE),

    /** Enables dotall mode. See {@link Pattern#DOTALL} for more details. */
    DOT_ALL(Pattern.DOTALL);

    /** The integer that is used for setting the flag on {@link java.util.regex.Pattern}. */
    private final int jdkPatternFlagCode;

    PatternFlag(int jdkPatternFlagCode) {
        this.jdkPatternFlagCode = jdkPatternFlagCode;
    }

    /**
     * @return The integer of the flag used by the JDK class {@link Pattern}.
     */
    public int getJdkPatternFlagCode() {
        return jdkPatternFlagCode;
    }
}
