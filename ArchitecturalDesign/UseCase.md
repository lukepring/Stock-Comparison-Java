# Use Case Model

## Overview
The use case diagram represents how a user interacts with the Share Price Comparison App and outlines the main functionality provided by the system. It identifies the different actions available to the user and shows how the system supports these actions through internal processes such as data retrieval, caching, and storage.

## Use Case Diagram
<img width="965" height="741" alt="image" src="https://github.com/user-attachments/assets/775edbc1-c4d8-40f6-819e-95faf2d3ab10" />

## Actor
The system has one primary actor, which is the user. The user interacts with the application to search for share data, view prices, and perform comparisons.

## Main Use Cases
The diagram includes several key use cases that represent the core functionality of the system.

The user can search for a share symbol and select a date range to define the period of interest. The user can also select and convert currency, allowing share prices to be viewed in different formats depending on preference.

The system allows the user to view current share prices as well as historical data. This enables the user to analyse trends over time. In addition, the user can compare share prices between different companies and view the results in a chart format.

The user is also able to update share prices, ensuring that the data being viewed is accurate and up to date.

## Supporting System Use Cases
Some use cases represent internal system behaviour rather than direct user interaction.

Retrieve Share Price Data is used by multiple user actions such as viewing prices, comparing shares, and updating data. This shows that many features rely on a common data retrieval process.

Store Price Data ensures that retrieved data is saved locally so that it can be accessed later. This supports offline functionality and improves performance.

Retrieve Cached Data is used when displaying charts, allowing the system to reuse previously stored data instead of always making external requests.

## Sequence of Interaction
A typical interaction begins when the user searches for a share symbol and selects a date range. The system then retrieves the relevant share price data.

Once the data has been retrieved, it can be stored locally. The user can then choose to view current prices, view historical trends, or compare different shares.

If the user chooses to view a chart, the system may retrieve cached data to improve performance. The user can also update share prices at any time, which triggers another data retrieval process.

Currency selection and conversion can be applied at different stages, allowing the displayed data to be adapted to the user’s preference.

## Summary
Overall, the use case diagram demonstrates the main capabilities of the system and how user actions are supported by internal processes. It shows a clear separation between user-driven functionality and system-level operations, while also highlighting how different features depend on shared data retrieval and storage mechanisms.
