package io.github.ricoapon.readableregex;

/**
 * Builder interface with methods to enhance user experience using shortcut methods.
 */
public interface SyntacticSugarBuilder extends StandaloneBlockBuilder, QuantifierBuilder {
    /**
     * Syntactic sugar for {@code .wordCharacter().oneOrMore()}.
     * @return This builder.
     */
    default ReadableRegex word() {
        return wordCharacter().oneOrMore();
    }
}
