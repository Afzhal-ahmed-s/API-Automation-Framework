# API-Automation-Framework

## Overview
This project is an API Automation Framework built using Java, Maven, TestNG, and Log4j2. It is designed for robust, maintainable, and scalable API testing, supporting data-driven and individual test execution, detailed reporting, and advanced logging.

## Features
- **API Testing**: Automates REST API tests with support for various HTTP methods and payloads.
- **Data-Driven Testing**: Uses external data sources (e.g., Excel) for parameterized test execution.
- **TestNG Integration**: Organizes tests via suites and groups, supports parallel execution, and provides retry logic for flaky tests.
- **Logging**: Uses Log4j2 for detailed, timestamped logs with both continuous and per-run log files.
- **Reporting**: Generates HTML and XML reports for test results.
- **Configuration Management**: Centralized configuration via properties files.

## Project Structure
```
API-Automation-Framework/
├── pom.xml                # Maven project file
├── testng.xml             # TestNG suite configuration
├── src/
│   ├── main/java/         # Main Java source code
│   ├── main/resources/    # Main resources (e.g., log4j2.xml)
│   ├── test/java/         # Test Java code (API tests, utilities)
│   └── test/resources/    # Test resources (log4j2.xml, routes.properties)
├── logs/                  # Log files (automation.log, timestamped logs)
├── reports/               # Test reports (HTML, XML)
├── testData/              # Test data files (e.g., UserData.xlsx)
└── target/                # Maven build output
```

## Getting Started
### Prerequisites
- Java 17+
- Maven 3.6.3+

### Setup
1. **Clone the repository**
   ```bash
   git clone <repo-url>
   cd API-Automation-Framework
   ```
2. **Install dependencies**
   Maven will handle dependencies via `pom.xml`.
   ```bash
   mvn clean install
   ```
3. **Configure environment variables (if needed)**
   Ensure Maven and Java are in your `PATH` and set `M2_HOME` if required.

### Running Tests
- **Default suite**:
  ```bash
  mvn test
  ```
- **Custom TestNG suite**:
  ```bash
  mvn test -DsuiteXmlFile=testng.xml
  ```

### Logging
- Logs are written to `logs/automation.log` (continuous) and timestamped log files per run.
- Log configuration is managed via `log4j2.xml` in `src/main/resources` and `src/test/resources`.

### Reports
- HTML and XML reports are generated in the `reports/` and `target/surefire-reports/` directories after each run.

## Configuration
- **Routes and Endpoints**: `src/test/resources/routes.properties`
- **Logging**: `src/main/resources/log4j2.xml`, `src/test/resources/log4j2.xml`
- **Test Data**: `testData/UserData.xlsx`

## Customization
- Add new API tests in `src/test/java/`
- Update or add data files in `testData/`
- Modify logging/reporting via Log4j2 and TestNG XML files

## Troubleshooting
- Ensure Java and Maven are correctly installed and configured in your environment.
- Check logs in the `logs/` directory for errors.
- Review reports in `reports/` and `target/surefire-reports/` for test results.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.

## License
Apache License Version 2.0
