package com.apon.readableregex.matchers;

import com.apon.readableregex.ReadableRegexPattern;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Matcher;

/**
 * Hamcrest matcher for checking whether the {@link ReadableRegexPattern} matches a given {@link String}.
 */
public class PatternMatchMatcher extends TypeSafeMatcher<ReadableRegexPattern> {
    enum MatchStrategy {
        MATCH_EXACTLY, NOT_MATCH_EXACTLY, MATCH_SOMETHING, NOT_MATCH_ANYTHING
    }
    private final String textToMatch;
    private final MatchStrategy matchStrategy;

    public PatternMatchMatcher(String textToMatch, MatchStrategy matchStrategy) {
        this.textToMatch = textToMatch;
        this.matchStrategy = matchStrategy;
    }

    public static PatternMatchMatcher matchesExactly(String textToMatch) {
        return new PatternMatchMatcher(textToMatch, MatchStrategy.MATCH_EXACTLY);
    }

    public static PatternMatchMatcher doesntMatchExactly(String textToMatch) {
        return new PatternMatchMatcher(textToMatch, MatchStrategy.NOT_MATCH_EXACTLY);
    }

    public static PatternMatchMatcher doesntMatchAnythingFrom(String textToMatch) {
        return new PatternMatchMatcher(textToMatch, MatchStrategy.NOT_MATCH_ANYTHING);
    }

    public static PatternMatchMatcher matchesSomethingFrom(String textToMatch) {
        return new PatternMatchMatcher(textToMatch, MatchStrategy.MATCH_SOMETHING);
    }

    @Override
    protected boolean matchesSafely(ReadableRegexPattern item) {
        Matcher matcher = item.matches(textToMatch);
        if (MatchStrategy.MATCH_EXACTLY.equals(matchStrategy)) {
            return matcher.matches();
        } else if (MatchStrategy.NOT_MATCH_EXACTLY.equals(matchStrategy)) {
            return !matcher.matches();
        } else if (MatchStrategy.MATCH_SOMETHING.equals(matchStrategy)) {
            return matcher.find();
        } else if (MatchStrategy.NOT_MATCH_ANYTHING.equals(matchStrategy)) {
            return !matcher.find();
        }

        throw new RuntimeException("Match strategy was not set correctly. Fix the static construction methods.");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Regex should match '").appendText(textToMatch).appendText("'");
    }

    @Override
    protected void describeMismatchSafely(ReadableRegexPattern item, Description mismatchDescription) {
        mismatchDescription.appendText("'").appendText(item.toString()).appendText("'")
                .appendText(" doesn't match");
    }
}
