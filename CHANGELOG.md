# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/) and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- Added method for or constructions (corresponds to `|`). Example: `regex().oneOf(regex().literal("a"), regex().digit()).build()`.
- Added methods for ranges (corresponds to `[]` and `[^]`). The following four methods are shown in this example:
  `regex().range('a', 'z').notInRange('a', 'f', '0', '9').anyCharacterOf("abc").anyCharacterExcept("def")`

### Changed
- Spotbugs annotations is removed as dependency.

## [0.1]
### Added
- First setup with minimal features.

[Unreleased]: https://github.com/ricoapon/readable-regex/compare/v0.1...HEAD
[0.1]: https://github.com/ricoapon/readable-regex/releases/tag/v0.1
