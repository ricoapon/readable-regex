# Readable Regex [![Maven Central](https://img.shields.io/maven-central/v/io.github.ricoapon/readable-regex)](https://search.maven.org/artifact/io.github.ricoapon/readable-regex) [![javadoc](https://javadoc.io/badge2/io.github.ricoapon/readable-regex/javadoc.svg?color=blue)](https://javadoc.io/doc/io.github.ricoapon/readable-regex) [![Build Status](https://travis-ci.com/ricoapon/readable-regex.svg?branch=master)](https://travis-ci.com/ricoapon/readable-regex) [![codecov](https://codecov.io/gh/ricoapon/readable-regex/branch/master/graph/badge.svg?token=O236UO0ZNZ)](https://codecov.io/gh/ricoapon/readable-regex)

With this library, you can create regular expressions in a readable way!

## Table of contents
1. [About the library](#about-the-library)
1. [User guide](#user-guide)
1. [Contributing](#contributing)
1. [Local development](#local-development)

## About the library
This library uses the builder pattern to create regular expressions. Using methods with understandable names to create
your expression, should be more readable and therefore easier to maintain!

### Regular expression engine
This library uses the engine implemented in the JDK. All the details and specifics of the engine can be found in the
JavaDoc of the class [Pattern](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html).

### Replacement of JavaVerbalExpressions
[JavaVerbalExpressions](https://github.com/VerbalExpressions/JavaVerbalExpressions) is another library created for Java
to construct regular expressions using a Builder pattern. I liked this library, but there were a few caveats:
* It seems that it is not maintained anymore.
* It misses some functionality (for example, lookahead).
* It is not written with Java in mind (the idea is ported to all languages).

This library is created to be a better version of JavaVerbalExpressions.

### Readability over performance
This library is focussed fully on readability and correctness. Very often performance of regular expressions is not
important. However, in some cases (especially with large input or with catastrophic backtracking) it can be very
troublesome. There is a lot of information online on how to make your regular expressions as fast as possible. However,
changing the builder to get good performing regular expressions may not be readable. If you are reliant on good
performing expressions, this library may not be the best choice.

## User guide
Note: [Hamcrest](http://hamcrest.org/) is used for all the examples to show the expected outcome. If you want the examples
to compile in your own project, you should include this library.

### Examples
Let's try a basic URL matching pattern:
```
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
```

With the library, you can create the JDK Matcher object when matching a text. Using this object, you can do the usual
things like getting the value of groups.
```
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

// If you need details about the groups afterwards, this is not possible using the JDK Pattern.
// However, using this library, this is now possible:
assertThat(pattern.groups(), contains(null, "secondWord"));

// If you have given the group a name, you can also find it based on the name.
assertThat(matcher.group("secondWord"), equalTo("def"));
```

The useful thing about this library is that you can include pattern inside other patterns!
```
// It does not matter if you have already built the pattern, you can include it anyway.
ReadableRegex<?> digits = regex().startGroup().digit().oneOrMore().endGroup().whitespace();
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
```

Some random stuff you can do:
```
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

assertThat(pattern.matchesTextExactly("abc a1a2a3 b2"), equalTo(true));
assertThat(pattern.matchesTextExactly("1 a3a6a9 "), equalTo(true));
```

### Quantifiers
All the quantifiers are greedy by default. If you want to make them reluctant or possessive, you can use the methods
`reluctant()` and `possessive()` after the quantifier.

If you want to know the differences between these types of quantifiers, read about it in
[this post](https://stackoverflow.com/questions/5319840/greedy-vs-reluctant-vs-possessive-quantifiers).

```
ReadableRegexPattern greedyPattern = regex().anything().literal("foo").build();
ReadableRegexPattern reluctantPattern = regex().anything().reluctant().literal("foo").build();
ReadableRegexPattern possessivePattern = regex().anything().possessive().literal("foo").build();

String text = "xfooxxxxxxfoo";
assertThat(greedyPattern.matchesText(text), equalTo(true));

Matcher matcher = reluctantPattern.matches(text);
assertThat(matcher.find(), equalTo(true));
assertThat(matcher.group(), equalTo("xfoo"));
assertThat(matcher.find(), equalTo(true));
assertThat(matcher.group(), equalTo("xxxxxxfoo"));

matcher = possessivePattern.matches(text);
assertThat(matcher.find(), equalTo(false));
```

### Working around the limits of the library
Not everything will be supported by the library. Sometimes you may want something very specific. There are a few methods
to help you with that.

The following example creates the regular expression `[a-z&&[^p]]`, which matches all lower-case letters except `p`.
```
ReadableRegexPattern pattern1 = regex()
        .regexFromString("[a-z&&[^p]]") // With this method, you can add any kind of expression.
        .build();

// Or you could use the overloaded variant of the regex method, which is the same:
ReadableRegexPattern pattern2 = regex("[a-z&&[^p]]").build();

assertThat(pattern1.matchesTextExactly("p"), equalTo(false));
assertThat(pattern1.matchesTextExactly("c"), equalTo(true));
assertThat(pattern2.matchesTextExactly("p"), equalTo(false));
assertThat(pattern2.matchesTextExactly("c"), equalTo(true));
```

Note that the ReadableRegexPattern class is basically a wrapper of a JDK Pattern object. So if you need specific methods,
you can use the underlying object:
```
ReadableRegexPattern pattern = regex().literal(".").build(); // Don't forget that literal escapes any meta character like dot!

Pattern jdkPattern = pattern.getUnderlyingPattern();
assertThat(jdkPattern.split("a.b.c"), equalTo(new String[]{"a", "b", "c"}));
```

### Extending the builder
You can fully customize the builder to your own needs! It is possible to add new methods and override existing methods.
The code below is an example on how to create your own extension:
```
// You have to extend from ExtendableReadableRegex, where you fill in your own class as generic type.
public class TestExtension extends ExtendableReadableRegex<TestExtension> {

    // It is highly advised to create your own static method "regex()". This way you can easily instantiate
    // your class and in your existing code you only have to change your import statement.
    public static TestExtension regex() {
        return new TestExtension();
    }

    // In your own extension you can add any method you like.
    public TestExtension digitWhitespaceDigit() {
        // For the implementation of your extension, you can only use the publicly available methods. All variables
        // and other methods are made private in the instance.
        // If you want to add arbitrary expressions, you can always use the method "regexFromString(...)".
        return digit().whitespace().digit();
    }

    // You can also override existing methods! To make sure that the code doesn't break, please always end
    // with calling the super method.
    @Override
    public ReadableRegexPattern buildWithFlags(PatternFlag... patternFlags) {
        return super.buildWithFlags(PatternFlag.DOT_ALL);
    }
}
```
This can now be used:
```
ReadableRegexPattern pattern = TestExtension.regex().digitWhitespaceDigit().build();

assertThat(pattern.matchesTextExactly("1 3"), equalTo(true));
assertThat(pattern.enabledFlags(), contains(PatternFlag.DOT_ALL));
```

### Javadoc
If you are looking for in-depth information about all the available methods, take a look at the Javadoc.
You can find the latest version [here](https://javadoc.io/doc/io.github.ricoapon/readable-regex/latest/index.html).

## Contributing
If you have any suggestions, submit an issue right here in the GitHub project! Any bugs, features or random thoughts
are appreciated :)

## Local development
### Checks
All additional plugins to check the code base should run when calling the following gradle command:
```
gradle checks
```
This will trigger [SpotBugs](https://spotbugs.github.io/), [Checkstyle](https://checkstyle.sourceforge.io/), [JaCoCo](https://www.jacoco.org/jacoco/) and [Pitest](https://pitest.org/).
If the checks succeed, the test coverage is printed. Of course, the test coverage should be 100%.

If you want to run one of the components individually (because this could be faster), you can use:
````
gradle spotbugs
gradle checkstyle
gradle jacoco
gradle pitest
gradle printTestPercentages
````
The reports are available in HTML form and are located in `build/reports`.

### Publishing new releases
Every release should correspond to a tag in git. This tag should be manually added.
Uploading new releases to Maven Central can be done using the following command:
````
gradle publish -PcustomVersion=X
````
If no version is supplied, the default `head-SNAPSHOT` is used.

Note that at this time, only the creator of this library (Rico Apon) can upload new releases.
