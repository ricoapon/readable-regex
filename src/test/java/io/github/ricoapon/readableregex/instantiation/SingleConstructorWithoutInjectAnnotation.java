package io.github.ricoapon.readableregex.instantiation;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class SingleConstructorWithoutInjectAnnotation {
    private final int n;

    public SingleConstructorWithoutInjectAnnotation(int n) {
        this.n = n;
    }
}
