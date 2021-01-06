# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/) and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
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

[Unreleased]: https://github.com/ricoapon/readable-regex/compare/v0.1...HEAD
[0.1]: https://github.com/ricoapon/readable-regex/releases/tag/v0.1
