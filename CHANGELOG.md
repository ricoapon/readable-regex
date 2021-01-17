# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/) and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- Groups that have been added are now recorded in the builder. The following two methods are added to retrieve this information:
  `ReadableRegexPattern#groups()` and `ReadableRegexPattern#nrOfGroups()`.

## [0.4.0]
### Added
- It is now possible to extend the builder. You can add new methods or overwrite existing methods. See the README for a code example.<br/>
  Note that all usages of `ReadableRegex` should be replaced with `ReadableRegex< ? >`, since the class now has a generic type.
  This is not a breaking change, since adding `< ? >` is not required to compile. It may give warning in some IDEs.

## [0.3.0]
### Added
- Added methods for start/end of line/input: `startOfLine()`, `startOfInput()`, `endOfLine()` and `endOfInput()`.
- Added methods for a tab and line break: `tab()` and `lineBreak()`.

### Changed
- Moved syntactic sugar methods from the interface GroupBuilder into SyntacticSugarBuilder.
- Improved the Javadoc as a whole: added missing comments and rewritten comments to make it more clear.

## [0.2.0]
### Added
- Added method for or constructions (corresponds to `|`). Example: `regex().oneOf(regex().literal("a"), regex().digit()).build()`.
- Added methods for ranges (corresponds to `[]` and `[^]`): `range(char... boundaries)`, `notInRange(char... boundaries)`,
  `anyCharacterOf(String characters)` and `anyCharacterExcept(String characters)`.
- Added methods for word characters and boundaries: `wordCharacter()`, `nonWordCharacter()`, `wordBoundary()` and `nonWordBoundary()`.
  Also, added the method `word()` which searches for words.
- All greedy quantifiers are now available.
- Added methods using dot (`.`): `anyCharacter()` and `anything()`.
- It is now possible to make existing quantifiers reluctant or possessive by appending the method `reluctant()` or `possessive()`
  after the quantifier.
- Added shortcut method for matching text: `pattern.matchesTextExactly(String text)`.
- Makes it possible to start unnamed groups: `startUnnamedGroup()`.

### Changed
- Spotbugs annotations is removed as dependency.

## [0.1]
### Added
- First setup with minimal features.

[Unreleased]: https://github.com/ricoapon/readable-regex/compare/v0.4.0...HEAD
[0.4.0]: https://github.com/ricoapon/readable-regex/releases/tag/v0.4.0
[0.3.0]: https://github.com/ricoapon/readable-regex/releases/tag/v0.3.0
[0.2.0]: https://github.com/ricoapon/readable-regex/releases/tag/v0.2.0
[0.1]: https://github.com/ricoapon/readable-regex/releases/tag/v0.1
