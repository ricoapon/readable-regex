package io.github.ricoapon.readableregex;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.ricoapon.readableregex.matchers.PatternMatchMatcher;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * All the code in the README should be identical to the code in this file. This way, we make sure that the code in the
 * README compiles and works as expected.
 * <p>
 * Note that we cannot use the class {@link PatternMatchMatcher}. It should be possible to compile all the examples in
 * any project that is using Hamcrest as well.
 * <p>
 * Also note that we cannot use these tests for coverage itself. These examples are ONLY meant for the README. The tests
 * will overlap with other tests, that is ok.
 */
@SuppressFBWarnings(value = "SIC_INNER_SHOULD_BE_STATIC", justification = "@Nested classes should be non-static, but SpotBugs wants them static." +
        "See https://github.com/spotbugs/spotbugs/issues/560 for the bug (open since 2018).")
class ReadmeTests {
    @Nested
    class Examples {
        @Test
        void example1() {
            ReadableRegexPattern pattern =
                    regex() // Always start with the regex method to start the builder.
                            .literal("http") // Literals are escaped automatically, no need to do this yourself.
                            .literal("s").optional() // You can follow up with optional to make the "s" optional.
                            .literal("://")
                            .anyCharacterExcept(" ").zeroOrMore() // This comes down to [^ ]*.
                            .build(); // Create the pattern with the final method.

            // The matchesText will return a boolean whether we have an *exact* match or not!
            assertThat(pattern.matchesTextExactly("https://www.github.com"), equalTo(true));

            // toString() method will return the underlying pattern. Not really readable though, that is why we have this library!
            assertThat(pattern.toString(), equalTo("(?:\\Qhttp\\E)(?:\\Qs\\E)?(?:\\Q://\\E)[^ ]*"));
        }

        @Test
        void example2() {
            ReadableRegexPattern pattern = regex()
                    .startGroup() // You can use this method to start capturing the expression inside a group.
                    .word()
                    .endGroup() // This ends the last group.
                    .whitespace()
                    .startGroup("secondWord") // You can also give names to your group.
                    .word()
                    .endGroup()
                    .build();

            Matcher matcher = pattern.matches("abc def");
            assertThat(matcher.matches(), equalTo(true));

            // Groups can always be found based on the order they are used.
            assertThat(matcher.group(1), equalTo("abc"));
            assertThat(matcher.group(2), equalTo("def"));

            // If you have given the group a name, you can also find it based on the name.
            assertThat(matcher.group("secondWord"), equalTo("def"));
        }

        @Test
        void example3() {
            // It does not matter if you have already built the pattern, you can include it anyway.
            ReadableRegex digits = regex().startGroup().digit().oneOrMore().endGroup().whitespace();
            ReadableRegexPattern word = regex().startGroup().word().endGroup().whitespace().build();

            ReadableRegexPattern pattern = regex()
                    .add(digits)
                    .add(digits)
                    .add(word)
                    .add(digits)
                    .literal("END")
                    .build();

            Matcher matcher = pattern.matches("12\t11\thello\t0000\tEND");
            assertThat(matcher.matches(), equalTo(true));
            // Note that captures are always a String!
            assertThat(matcher.group(1), equalTo("12"));
            assertThat(matcher.group(2), equalTo("11"));
            assertThat(matcher.group(3), equalTo("hello"));
            assertThat(matcher.group(4), equalTo("0000"));
        }

        @Test
        void example4() {
            ReadableRegexPattern pattern = regex()
                    .oneOf(regex().literal("abc"), regex().digit()) // The oneOf method represents "or".
                    .whitespace()
                    // If we want to add a quantifier over a larger expression, we can encapsulate it with the add method,
                    // which encloses the expression in an unnamed group.
                    .add(regex().literal("a").digit()).exactlyNTimes(3)
                    .whitespace()
                    // Alternatively, you can use the startUnnamedGroup() for this to avoid nested structures.
                    .startUnnamedGroup().literal("b").digit().endGroup().atMostNTimes(2)
                    .build();

            System.out.println(pattern.toString());
            assertThat(pattern.matchesTextExactly("abc a1a2a3 b2"), equalTo(true));
            assertThat(pattern.matchesTextExactly("1 a3a6a9 "), equalTo(true));
        }
    }

    @Nested
    class Quantifiers {
        @Test
        void example1() {
            ReadableRegexPattern greedyPattern = regex().anything().literal("foo").build();
            ReadableRegexPattern reluctantPattern = regex().anything().reluctant().literal("foo").build();
            ReadableRegexPattern possessivePattern = regex().anything().possessive().literal("foo").build();

            String text = "xfooxxxxxxfoo";
            assertThat(greedyPattern.matchesTextExactly(text), equalTo(true));

            Matcher matcher = reluctantPattern.matches(text);
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(), equalTo("xfoo"));
            assertThat(matcher.find(), equalTo(true));
            assertThat(matcher.group(), equalTo("xxxxxxfoo"));

            matcher = possessivePattern.matches(text);
            assertThat(matcher.find(), equalTo(false));
        }
    }

    @Nested
    class WorkingAroundTheLimitsOfTheLibrary {
        @Test
        void example1() {
            ReadableRegexPattern pattern1 = regex()
                    .regexFromString("[a-z&&[^p]]") // With this method, you can add any kind of expression.
                    .build();

            // Or you could use the overloaded variant of the regex method, which is the same:
            ReadableRegexPattern pattern2 = regex("[a-z&&[^p]]").build();

            assertThat(pattern1.matchesTextExactly("p"), equalTo(false));
            assertThat(pattern1.matchesTextExactly("c"), equalTo(true));
            assertThat(pattern2.matchesTextExactly("p"), equalTo(false));
            assertThat(pattern2.matchesTextExactly("c"), equalTo(true));
        }

        @Test
        void example2() {
            ReadableRegexPattern pattern = regex().literal(".").build();

            Pattern jdkPattern = pattern.getUnderlyingPattern();
            assertThat(jdkPattern.split("a.b.c"), equalTo(new String[]{"a", "b", "c"}));
        }
    }
}
