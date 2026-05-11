# Hackathon_TripCost

A Java-based test automation project built for testing trip cost and travel-related functionalities. This project leverages modern test automation frameworks and tools to ensure robust quality assurance of travel booking and cost calculation features.

## 📋 Project Overview

Hackathon_TripCost is a comprehensive test automation suite designed to validate trip cost calculations, bookings, and related travel functionalities. The project uses industry-standard tools and best practices for automated testing.

## 🛠️ Tech Stack

- **Language**: Java 21
- **Build Tool**: Maven
- **Test Framework**: TestNG 7.9.0
- **Web Automation**: Selenium 4.18.1
- **Browser Driver Management**: WebDriverManager 5.7.0
- **Data Handling**: Apache POI 5.2.5 (Excel support)

## 📦 Dependencies

The project uses the following key dependencies:

### Core Testing Dependencies
- **Selenium WebDriver** (4.18.1): Web UI automation and testing
- **TestNG** (7.9.0): Test execution framework with annotations and assertions
- **WebDriverManager** (5.7.0): Automatic browser driver management

### Data-Driven Testing
- **Apache POI** (5.2.5): Excel file handling for data-driven tests
  - poi: Core POI library
  - poi-ooxml: Support for XLSX files

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 21 or higher
- Maven 3.6 or higher
- Git

### Installation

1. Clone the repository:
```bash
git clone https://github.com/sayansonu7/Hackathon_TripCost.git
cd Hackathon_TripCost
```

2. Install dependencies:
```bash
mvn clean install
```

3. Build the project:
```bash
mvn compile
```

## 🧪 Running Tests

Execute all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=TestClassName
```

Run tests with specific XML configuration:
```bash
mvn test -DsuiteXmlFile=testing.xml
```

## 📁 Project Structure

```
Hackathon_TripCost/
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       └── java/
├── pom.xml                 # Maven configuration
├── testing.xml            # TestNG configuration file
├── .gitignore
└── README.md
```

## 🎯 Features

- **Automated Test Execution**: TestNG framework for structured test execution
- **Data-Driven Testing**: Excel-based test data management using Apache POI
- **Cross-Browser Testing**: Selenium WebDriver for multi-browser support
- **Automatic Driver Management**: WebDriverManager handles browser driver setup automatically
- **Test Configuration**: XML-based test configuration and test suites

## 📊 Test Configuration

The `testing.xml` file contains TestNG configuration for organizing and executing test cases. Customize this file to define test suites, groups, and execution order.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the MIT License (if applicable).

## 👤 Author

**Sayan Sonu**
- GitHub: [@sayansonu7](https://github.com/sayansonu7)

## 📧 Contact

For questions or suggestions, feel free to open an issue on the GitHub repository.

## 🔗 Useful Links

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/)
- [Apache POI Documentation](https://poi.apache.org/)
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)

---

**Last Updated**: May 11, 2026
