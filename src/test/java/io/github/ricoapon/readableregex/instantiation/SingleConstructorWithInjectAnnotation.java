package io.github.ricoapon.readableregex.instantiation;

import javax.inject.Inject;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class SingleConstructorWithInjectAnnotation {
    private final int n;

    @Inject
    public SingleConstructorWithInjectAnnotation(int n) {
        this.n = n;
    }
}
