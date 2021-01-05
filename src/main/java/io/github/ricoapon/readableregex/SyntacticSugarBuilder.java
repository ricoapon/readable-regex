package io.github.ricoapon.readableregex;

/**
 * Builder interface with methods to enhance user experience using shortcut methods.
 */
public interface SyntacticSugarBuilder extends StandaloneBlockBuilder, QuantifierBuilder {
    /**
     * Matches a word. This is the same as {@code \w+}
     * <p>
     * Syntactic sugar for {@code .wordCharacter().oneOrMore()}.
     * @return This builder.
     */
    default ReadableRegex word() {
        return wordCharacter().oneOrMore();
    }

    /**
     * Matches anything. This is the same as {@code .*}.
     * <p>
     * Syntactic sugar for {@code .anyCharacter().zeroOrMore()}.
     * @return This builder.
     */
    default ReadableRegex anything() {
        return anyCharacter().zeroOrMore();
    }
}
