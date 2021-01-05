package io.github.ricoapon.readableregex;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests related to methods that are inside {@link SyntacticSugarBuilder}.
 */
@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC", justification = "@Nested classes should be non-static, but SpotBugs wants them static." +
        "See https://github.com/spotbugs/spotbugs/issues/560 for the bug (open since 2018).")
public class SyntacticSugarTests {
    @Nested
    class Word {
        @Test
        void wordWorks() {
            String groupName = "result";
            ReadableRegexPattern pattern = regex().startGroup(groupName).word().endGroup().build();

            Matcher matcher = pattern.matches("abc de_f1 ghi|jkl");
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(groupName), equalTo("abc"));
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(groupName), equalTo("de_f1"));
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(groupName), equalTo("ghi"));
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(groupName), equalTo("jkl"));
        }
    }
}
