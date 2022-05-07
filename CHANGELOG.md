# Changelog

## [Unreleased]
### Added
- gradle task to release to maven central

### Changed
- Used externalized `publish.gradle`
- Configure Renovate
- chore: Used custom ssh key to push to github
- chore(deps): update plugin org.owasp.dependencycheck to v7.1.0.1

## [1.2.0] - 2022-05-05
### Added
- Dependency check plugin

### Changed
- Publication of artifacts to Sonatype Nexus due to bintray sunset
- Upgraded dependency versions
- Made `publish.gradle` generic so that it can be moved to `gradle-includes`
- Refactored CI pipeline with proper gradle caching

## [1.1.0] - 2020-10-06
### Added
- Ability to customize JSON field keys(#7)

## [1.0.1] - 2018-09-30
### Added
- Initial version that uses specific JSON conversion library based on the specified formatter class
- Gradle
- CircleCI config to build and test
- Separate configurations to test `gson`, `json-simple` and `jackson`
- `CustomJsonConverter` for when JSON creation library is present in the classpath
- Test summary in CircleCI
- Code coverage using `coveralls`
- sonarqube integration
- Usage details to readme
- `LICENSE.txt`
- Gradle configuration to upload to `bintray`

### Changed
- Fixed compilation error in tests
- Upgraded gradle to `4.7` to support Java 10
- Fixed the date format pattern used in tests
- Used `DateTimeFormatter` instead of `FastDateFormat`
- Improved test coverage
- Fixed a few sonar violations
- Package names to `io.github.devatherock`