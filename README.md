# Readable Regex [![Build Status](https://travis-ci.com/GreenT13/readable-regex.svg?branch=master)](https://travis-ci.com/GreenT13/readable-regex) [![codecov](https://codecov.io/gh/GreenT13/readable-regex/branch/master/graph/badge.svg?token=O236UO0ZNZ)](https://codecov.io/gh/GreenT13/readable-regex)

With this library, you can create regular expressions in a readable way!

# Readability over performance
This library is focussed fully on readability and correctness. Regular expressions can be tricky with performance.
There is a lot of information online on how to make your regular expressions perform. Changing the builder to get
good performing regular expressions may not be readable. If you are reliant on good performing expressions, it may not
be the best choice to use this library.

# Regular expression engine
This library uses the engine implemented in the JDK. All the details and specifics of the engine can be found in the JavaDoc
of the class [Pattern](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html).

# Replacement of JavaVerbalExpressions
[JavaVerbalExpressions](https://github.com/VerbalExpressions/JavaVerbalExpressions) is another library created for Java to construct regular expressions using a Builder pattern.
I liked this library, but there were a few caveats:
* It is not maintained anymore.
* It misses some functionality (for example, lookahead).
* It is not written with Java in mind (the idea is ported to all languages).

This library is created to be a better version of JavaVerbalExpressions.

# Local development
All additional plugins to check the code base should run when calling the following gradle command:
```
gradle checks
```
This will trigger [SpotBugs](https://spotbugs.github.io/), [Checkstyle](https://checkstyle.sourceforge.io/), [JaCoCo](https://www.jacoco.org/jacoco/) and [Pitest](https://pitest.org/).
If you want to run this individually (because this is faster), you can use:
````
gradle spotbugs
gradle checkstyle
gradle jacoco
gradle pitest
````
All the HTML reports can be viewed in `build/reports`.
