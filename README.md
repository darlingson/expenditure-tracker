# Expenditure Tracker App

## Overview

The Expenditure Tracker App is an Android application designed to help users manage their expenses efficiently. Users can enter expenses, set a monthly budget, and view weekly and monthly spending summaries.

## Features

- **Enter Expenses**
    - Manual entry of expense details (amount, category, date, etc.).
    - Scan receipts to automatically extract expense details.
- **Monthly Budget**
    - Set and update a monthly budget.
    - Track remaining budget based on entered expenses.
- **Spending Overview**
    - Weekly and monthly spending summaries.
    - Visualize spending data using charts.

## Screens

- **Home Screen**
    - Overview of total spending, monthly budget, and remaining budget.

- **Expenditures Screen**
  - List of all expenses.
  - Filter expenses by category.
  - Option to delete expenses.
  - Option to edit expenses.
  - List of expenses from previous months.
  - Form to manually add expenses.
  - Option to scan receipts for automatic entry.

- **Budgets Screen**
  - previous budgets.
  - Form to set or update the monthly budget.

- **Reports Screen**
    - Weekly and monthly spending summaries with visual charts.

## Tech Stack

- **Kotlin**: Programming language for Android development.
- **Jetpack Compose**: Modern toolkit for building native UI.
- **ML Kit**: For text recognition from scanned receipts.

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/darlingson/expenditure-tracker.git
    ```
2. Open the project in Android Studio.
3. Build and run the project on an emulator or a physical device.

## Usage

1. **Home Screen**: View your total spending, monthly budget, and remaining budget. Navigate to other screens.
2. **Expenditures**: Navigate to the expenditures entry screen to manually add an expense or scan a receipt.
3. **Budgets**: Navigate to the budget screen to set or update your monthly budget.
4. **View Reports**: Navigate to the reports screen to see a summary of your weekly and monthly spending.

## Contributing

1. Fork the repository.
2. Create a new branch:
    ```bash
    git checkout -b feature/your-feature-name
    ```
3. Make your changes and commit them:
    ```bash
    git commit -m 'Add some feature'
    ```
4. Push to the branch:
    ```bash
    git push origin feature/your-feature-name
    ```
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the UI components.
- [ML Kit](https://developers.google.com/ml-kit) for text recognition.
- Inspiration from various budgeting apps.

