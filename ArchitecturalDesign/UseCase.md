# Use Case Model

## Overview
This use case model describes how the user interacts with the Share Price Comparison Web App. It focuses on the main tasks the user can perform and how the system responds to those actions. The diagram identifies the key use cases and shows how some actions depend on others.

## Use Case Diagram
<img width="818" height="611" alt="image" src="https://github.com/user-attachments/assets/61e5d7bf-6b55-4a5e-9ea8-6ab9c8ea5014" />

## Main Use Cases
The user can retrieve share price data, compare share prices, and view share price charts. These represent the main goals of the user when using the application.

The system also carries out supporting actions such as storing price data and retrieving cached data. These actions are not directly triggered by the user but are required for the system to function properly and to support offline use.

## Use Case Description: Compare Share Prices

### Actor
User

### Description
This use case allows the user to compare the share prices of one or two companies over a selected date range. The system retrieves the relevant data and presents it in a chart so that the user can easily compare performance over time.

### Precondition
The user has access to the application and enters valid share symbols and a date range within the allowed limit.

### Main Flow
1. The user enters one or two share symbols.
2. The system validates the input.
3. The user selects a date range.
4. The system retrieves share price data for the selected shares from an external source.
5. The system stores the retrieved data locally for future use.
6. The system processes the data to prepare it for comparison.
7. The system generates a chart based on the processed data.
8. The system displays the chart to the user.

### Alternative Flow
If there is no internet connection, the system retrieves previously stored data instead of fetching new data.

If the user enters invalid share symbols or an invalid date range, the system displays an error message and requests valid input.

### Postcondition
The user is presented with a chart showing the comparison of share prices over the selected time period, and the data may be stored for later use.
