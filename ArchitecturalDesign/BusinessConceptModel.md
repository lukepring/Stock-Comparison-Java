# Business Concept Model

## Overview
This diagram shows the main ideas behind the share price comparison app and how they connect. It focuses on what the system does from a user point of view rather than how it is built.

## Business Concept Model Diagram
<img width="933" height="638" alt="image" src="https://github.com/user-attachments/assets/61790d27-730b-4367-9333-5922f7b284ae" />

## Main Concepts
The centre of the model is the Comparison. This represents the main task in the system where a user compares share prices over time.

A User can create multiple comparisons which shows that users can analyse different shares whenever they want.

Each comparison uses a Date Range. This defines the time period for the data being viewed.

The comparison is carried out on one or two Shares. Each share belongs to a Company which represents the business that owns the stock.

Each share has a Price History which stores all price data over time. This is made up of multiple Price Records where each record represents the price on a specific day.

The results are shown using a Chart which displays the comparison in a clear visual way.

## Relationships
The diagram shows how these concepts are linked. A user requests a comparison, a comparison uses a date range and compares shares, and each share is connected to its company and price data. The chart then displays the final result.

## Summary
This model gives a clear view of the key concepts in the system and how they relate to each other. It helps explain the problem before moving on to the technical design in later stages.
