# Changelog

## [Unreleased]
### Added
- gradle task to release to maven central
- Spotless plugin

### Changed
- Used externalized `publish.gradle`
- Configure Renovate
- chore: Used custom ssh key to push to github
- fix(deps): update dependency com.google.code.gson:gson to v2.10.1
- chore(deps): update plugin com.github.kt3k.coveralls to v2.12.2
- chore(deps): update plugin io.github.gradle-nexus.publish-plugin to v1.3.0
- chore(deps): update plugin org.owasp.dependencycheck to v8.2.1
- chore(deps): update plugin org.sonarqube to v4
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.15.0
- Upgraded to gradle 7 and Java 17
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.15.1
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.15.1
- chore(deps): update plugin com.diffplug.spotless to v6.19.0
- chore(deps): update plugin org.sonarqube to v4.1.0.3113
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.15.2
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.15.2
- chore(deps): update plugin org.sonarqube to v4.2.0.3129
- chore(deps): update plugin org.sonarqube to v4.2.1.3168
- chore(deps): update devatherock/simple-slack docker tag to v1
- Configure Mend Bolt for GitHub
- chore(deps): update plugin org.owasp.dependencycheck to v8.3.1
- chore(deps): update dependency gradle to v7.6.2
- chore(deps): update plugin com.diffplug.spotless to v6.20.0
- chore(deps): update plugin org.sonarqube to v4.3.0.3225
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.10.0
- chore(deps): update plugin org.owasp.dependencycheck to v8.4.0
- chore(deps): update plugin org.sonarqube to v4.3.1.3277
- chore(deps): update plugin com.diffplug.spotless to v6.21.0
- chore(deps): update plugin com.diffplug.spotless to v6.22.0
- chore(deps): update dependency gradle to v7.6.3
- chore(deps): update plugin org.owasp.dependencycheck to v8.4.2
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.15.3
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.15.3
- chore(deps): update plugin org.sonarqube to v4.4.1.3373
- fix(deps): update dependency org.junit.vintage:junit-vintage-engine to v5.10.1
- chore(deps): update plugin org.owasp.dependencycheck to v8.4.3
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.16.0
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.16.0
- chore(deps): update plugin org.owasp.dependencycheck to v9
- chore(deps): update plugin org.owasp.dependencycheck to v9.0.2
- chore(deps): update plugin com.diffplug.spotless to v6.23.2
- chore(deps): update plugin com.diffplug.spotless to v6.23.3
- chore(deps): update plugin org.owasp.dependencycheck to v9.0.6
- chore(deps): update plugin org.owasp.dependencycheck to v9.0.7
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.16.1
- fix(deps): update dependency com.fasterxml.jackson.core:jackson-databind to v2.16.1
- chore(deps): update plugin org.owasp.dependencycheck to v9.0.8
- chore(deps): update plugin org.owasp.dependencycheck to v9.0.9
- chore(deps): update plugin com.diffplug.spotless to v6.24.0
- chore(deps): update plugin com.diffplug.spotless to v6.25.0
- chore(deps): update cimg/openjdk docker tag to v17.0.10

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