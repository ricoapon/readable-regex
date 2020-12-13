package com.apon.readableregex.matchers;

import com.apon.readableregex.ReadableRegexPattern;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Matcher;

/**
 * Hamcrest matcher for checking whether the {@link ReadableRegexPattern} matches a given {@link String}.
 */
public class PatternMatchMatcher extends TypeSafeMatcher<ReadableRegexPattern> {
    private final String textToMatch;
    private final boolean mustMatchExactly;

    public PatternMatchMatcher(String textToMatch, boolean mustMatchExactly) {
        this.textToMatch = textToMatch;
        this.mustMatchExactly = mustMatchExactly;
    }

    public static PatternMatchMatcher matchesExactly(String textToMatch) {
        return new PatternMatchMatcher(textToMatch, true);
    }

    public static PatternMatchMatcher doesntMatchAnythingFrom(String textToMatch) {
        return new PatternMatchMatcher(textToMatch, false);
    }

    @Override
    protected boolean matchesSafely(ReadableRegexPattern item) {
        Matcher matcher = item.matches(textToMatch);
        if (mustMatchExactly) {
            return matcher.matches();
        }

        // For the doesntMatch method, we reverse the outcome.
        return !matcher.find();
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
