package io.github.ricoapon.readableregex.instantiation;

import javax.inject.Inject;

@SuppressWarnings({"MultipleInjectedConstructorsForClass", "unused"})
public class MultipleConstructorsWithMultipleInjectAnnotations {

    @Inject
    public MultipleConstructorsWithMultipleInjectAnnotations(int n) {

    }

    @Inject
    public MultipleConstructorsWithMultipleInjectAnnotations(int n, boolean uselessParam) {

    }
}
