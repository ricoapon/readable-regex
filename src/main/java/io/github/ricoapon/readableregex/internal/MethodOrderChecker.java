package io.github.ricoapon.readableregex.internal;

import io.github.ricoapon.readableregex.FinishBuilder;
import io.github.ricoapon.readableregex.GroupBuilder;
import io.github.ricoapon.readableregex.IncorrectConstructionException;
import io.github.ricoapon.readableregex.QuantifierBuilder;
import io.github.ricoapon.readableregex.ReadableRegex;
import io.github.ricoapon.readableregex.StandaloneBlockBuilder;

/**
 * Class for maintaining the status of a building a regular expression and checking whether executing methods of
 * {@link ReadableRegex} is valid.
 */
public class MethodOrderChecker {
    /** The types of methods that can be executed by {@link ReadableRegex}. */
    enum Method {
        /** Methods from the interface {@link StandaloneBlockBuilder}. */
        STANDALONE_BLOCK,
        /** Methods from the interface {@link QuantifierBuilder}. */
        QUANTIFIER,
        /** Methods from the interface {@link FinishBuilder}. */
        FINISH,
        /** Starting a group with a method from the interface {@link GroupBuilder}. */
        START_GROUP,
        /** Ending a group with the method {@link GroupBuilder#endGroup()}. */
        END_GROUP,
    }

    /** Indicates if a quantifier is allowed as the next method. Start with {@code false}, because you cannot start with a quantifier. */
    private boolean isQuantifierPossibleAfterThisMethod = false;

    /** Counts how many groups are started and are still left open. These must be closed before finishing. */
    private int nrOfGroupsStarted = 0;

    /**
     * Checks if a method can be called. If not, it will throw an {@link IncorrectConstructionException}.
     * @param method The method to execute.
     */
    public void checkCallingMethod(Method method) {
        if (method == Method.STANDALONE_BLOCK) {
            standaloneBlock();
        } else if (method == Method.QUANTIFIER) {
            quantifier();
        } else if (method == Method.FINISH) {
            finish();
        } else if (method == Method.START_GROUP) {
            startGroup();
        } else if (method == Method.END_GROUP) {
            endGroup();
        }
    }

    private void quantifier() {
        if (!isQuantifierPossibleAfterThisMethod) {
            throw new IncorrectConstructionException("You cannot add a quantifier after a quantifier. Remove one of the incorrect quantifiers. " +
                    "Or, if you haven't done anything yet, you started with a quantifier. That is not possible.");
        }

        isQuantifierPossibleAfterThisMethod = false;
    }

    private void standaloneBlock() {
        isQuantifierPossibleAfterThisMethod = true;
    }

    private void finish() {
        if (nrOfGroupsStarted != 0) {
            throw new IncorrectConstructionException("You forgot to close all the groups that have started. Please close them all using the endGroup() method.");
        }
    }

    private void startGroup() {
        nrOfGroupsStarted++;
        isQuantifierPossibleAfterThisMethod = false;
    }

    private void endGroup() {
        if (nrOfGroupsStarted == 0) {
            throw new IncorrectConstructionException("You cannot close a group, since none have started. " +
                    "Remove this method call or start a group.");
        }
        nrOfGroupsStarted--;
        isQuantifierPossibleAfterThisMethod = true;
    }
}
