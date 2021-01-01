package io.github.ricoapon.readableregex;

/**
 * Class with useful constants that can be used in test cases.
 */
public class Constants {
    public final static String A_TO_Z_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public final static String A_TO_Z_UPPERCASE = A_TO_Z_LOWERCASE.toUpperCase();
    public final static String WORD_CHARACTERS = A_TO_Z_LOWERCASE + A_TO_Z_UPPERCASE + "_";
    public static final String DIGITS = "0123456789";
    public static final String WHITESPACES = " \t\n\f\r";
    public static final String NON_LETTERS = ";'[]{}|?/";
}
