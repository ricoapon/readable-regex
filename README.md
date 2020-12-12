# Readable Regex [![Build Status](https://travis-ci.com/GreenT13/readable-regex.svg?branch=master)](https://travis-ci.com/GreenT13/readable-regex)

# Local development
All additional plugins to check the code base should run when calling the following gradle command:
```
gradle checks
```
This will trigger [SpotBugs](https://spotbugs.github.io/). If you want to run this individually (because this is faster), you can use:
````
gradle spotbugs
````
The HTML reports are located in a subdirectory in `build/reports`.
