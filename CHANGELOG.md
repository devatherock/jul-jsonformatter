# Changelog
## 1.0.0
### 2018-08-19
- Initial version that uses specific JSON conversion library based on the specified formatter class
- Fixed compilation error in tests

### 2018-08-29
- Added gradle

### 2018-09-04
- Upgraded gradle to `4.7` to support Java 10

### 2018-09-05
- Added `git-sync` plugin to sync to a different repository

### 2018-09-28
- CircleCI config to build and test

### 2018-09-29
- Separate configurations to test `gson`, `json-simple` and `jackson`
- Fixed the date format pattern used in tests
- Used `DateTimeFormatter` instead of `FastDateFormat`
- `CustomJsonConverter` for when JSON creation library is present in the classpath
- Test summary in CircleCI
- Code coverage using `coveralls`
- sonarqube integration