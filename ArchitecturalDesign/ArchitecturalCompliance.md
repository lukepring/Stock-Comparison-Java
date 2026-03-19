# Architecture Compliance

## Overview
The system has been designed using a layered structure based on Clean Architecture principles. This approach was chosen to keep the application organised and to make it easier to maintain and extend as new features are added in later stages of development.

## Layered Structure
The architecture is divided into four main layers: presentation, application, domain, and infrastructure. The presentation layer is responsible for handling user interaction through the Web UI and passing requests to the controller. The application layer manages the main flow of the system, including retrieving share price data, processing it, and preparing it for comparison. The domain layer represents the core concepts of the system and remains independent from technical concerns. The infrastructure layer handles external operations such as accessing market data and storing cached data locally.

## Dependency Direction
The system follows the principle that dependencies should flow inward. This means that outer layers depend on inner layers, but inner layers do not depend on outer layers. As a result, the core logic of the system remains independent from frameworks, APIs, and storage mechanisms. This makes the design more stable, as changes in external components do not directly affect the core of the application.

## Use of Interfaces
Interfaces are used to separate the application logic from the infrastructure layer. The application layer defines the required behaviour through interfaces, while the infrastructure layer provides the concrete implementations. For example, data retrieval and caching are handled through interfaces, allowing different implementations to be used without changing the main logic of the system.

## Separation of Concerns
The design ensures that responsibilities are clearly separated. Business logic is contained within the service layer and is not mixed with API calls or database operations. This helps to avoid tightly coupled code and makes the system easier to understand and maintain.

## Summary
Overall, the system follows Clean Architecture principles by organising components into clear layers, controlling the direction of dependencies, and using interfaces to reduce coupling. This results in a design that is flexible, maintainable, and suitable for future development.
