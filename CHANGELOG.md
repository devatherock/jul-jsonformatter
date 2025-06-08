# Changelog

## [Unreleased]
### Added
- gradle task to release to maven central
- Spotless plugin
- Sonar to PR check

### Changed
- Used externalized `publish.gradle`
- Configure Renovate
- chore: Used custom ssh key to push to github
- fix(deps): update dependency com.google.code.gson:gson to v2.10.1
- chore(deps): update plugin com.github.kt3k.coveralls to v2.12.2
- Configure Mend Bolt for GitHub
- chore(deps): update dependency gradle to v7.6.4
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.10.2
- chore(deps): update plugin org.sonarqube to v5
- chore(deps): update plugin io.github.gradle-nexus.publish-plugin to v2
- chore(deps): update cimg/openjdk docker tag to v17.0.11
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.17.1
- fix(deps): update dependency com.google.code.gson:gson to v2.11.0
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.10.3
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.17.2
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.17.2
- chore(deps): update plugin org.sonarqube to v5.1.0.4882
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.11.0
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.11.1
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.18.0
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.11.2
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.11.3
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.18.1
- chore(deps): update plugin org.sonarqube to v6
- chore(deps): update plugin org.sonarqube to v6.0.1.5171
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.18.2
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.11.4
- chore(deps): update plugin com.diffplug.spotless to v7
- chore(deps): update plugin com.diffplug.spotless to v7.0.2
- fix(deps): update dependency com.google.code.gson:gson to v2.12.1
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.12.0
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.18.3
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.12.1
- chore(deps): update plugin org.sonarqube to v6.1.0.5360
- chore(deps): update plugin com.diffplug.spotless to v7.0.3
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.12.2
- fix(deps): update dependency com.google.code.gson:gson to v2.13.0
- fix(deps): update dependency com.google.code.gson:gson to v2.13.1
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.19.0
- chore(deps): update plugin org.sonarqube to v6.2.0.5505
- chore(deps): update plugin com.diffplug.spotless to v7.0.4
- chore(deps): update dependency gradle to v7.6.5
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.13.1

### Removed
- Dependency check plugin

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