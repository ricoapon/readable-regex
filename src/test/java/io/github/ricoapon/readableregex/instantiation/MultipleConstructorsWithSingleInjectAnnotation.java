package io.github.ricoapon.readableregex.instantiation;

import javax.inject.Inject;

@SuppressWarnings("unused")
public class MultipleConstructorsWithSingleInjectAnnotation {
    private final int n;

    @Inject
    public MultipleConstructorsWithSingleInjectAnnotation(int n) {
        this.n = n;
    }

    public MultipleConstructorsWithSingleInjectAnnotation(int n, boolean uselessParam) {
        this.n = n;
    }
}
