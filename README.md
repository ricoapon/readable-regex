# Readable Regex [![Build Status](https://travis-ci.com/GreenT13/readable-regex.svg?branch=master)](https://travis-ci.com/GreenT13/readable-regex)

# Local development
All additional plugins to check the code base should run when calling the following gradle command:
```
gradle checks
```
This will trigger [SpotBugs](https://spotbugs.github.io/), [Checkstyle](https://checkstyle.sourceforge.io/) and  [JaCoCo](https://www.jacoco.org/jacoco/).
If you want to run this individually (because this is faster), you can use:
````
gradle spotbugs
gradle checkstyle
gradle jacoco
````
All the HTML reports can be viewed in `build/reports`.
