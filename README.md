# Windows Application Testing Framework

## Overview
This project provides an automated functional testing framework for a Windows desktop banking application that includes a WebView2 component. The framework integrates **WinAppDriver**, **Playwright**, and **TestNG** to facilitate seamless testing of both desktop and web elements within the application.

## Features
- **Automated Application Management**: Start, attach to, and close the Windows application.
- **Windows UI Automation**: Interact with desktop UI elements using WinAppDriver.
- **WebView2 Interaction**: Navigate and automate within embedded web components using Playwright.
- **Test Execution Management**: Utilize TestNG for structured test execution and reporting.
- **Dynamic Test Data Handling**: Fetch test data from Excel files.

## Tech Stack
- **Java**: Core programming language.
- **WinAppDriver**: Automates interactions with Windows desktop applications.
- **Playwright**: Automates web interactions within WebView2.
- **TestNG**: Manages test execution and reporting.
- **Selenium**: Handles Windows application element interactions.
- **Excel Utility**: Provides dynamic test data management.

## Project Structure
```
base/
│   ├── WindowsDriverManager.java   # Manages WinAppDriver interactions
│   ├── PlaywrightManager.java      # Manages Playwright browser automation
│   ├── BaseClass.java              # Handles test setup and teardown
│
pom/
│   ├── logIn/                      # Page Object Models for login workflows
│   ├── pws/                        # Page Object Models for WebView interactions
│
utils/
│   ├── ConfigProperties.java       # Manages configuration properties
│   ├── ExcelUtil.java              # Handles Excel-based test data
│   ├── Utils.java                  # Common utility methods
```


