# Architectural Design




## Component Specification Diagram

<img width="1024" height="718" alt="image" src="https://github.com/user-attachments/assets/d615d32f-bca0-4af4-9c38-fca50d31acd8" />



## Architectural Concepts and Their Application

The architecture of the Share Price Comparison Web Application follows a layered architectural structure consisting of Presentation, Application, and Infrastructure layers. This structure is intentionally reflected in the component specification diagram, where each layer is clearly separated and responsibilities are distributed logically.

At the top of the diagram, the Presentation layer contains the Web UI and the PriceController. The Web UI represents the user interface through which stock symbols and date ranges are submitted. The PriceController acts as the entry point into the system, receiving HTTP requests and forwarding them to the Application layer. This separation ensures that user interaction logic remains independent from business processing logic.

The Application layer forms the core of the system. The PriceComparisonService coordinates the primary use case of the application, which includes retrieving price data and comparing up to two symbols. It enforces the business rules shown in the diagram, including the restriction that the date range must not exceed two years. It also implements the offline first behaviour by determining whether to retrieve data from the external provider or load it from the local cache. The ChartBuilder component, also within the Application layer, transforms retrieved price data into a structured dataset suitable for graphical representation. Separating chart preparation from price retrieval improves cohesion and keeps responsibilities focused.

The Infrastructure layer contains the MarketDataClient and the LocalCacheStore. The MarketDataClient connects to the External Market Data API using HTTPS, as shown in the diagram. The LocalCacheStore manages persistence through SQLite or JSON based storage, represented by the Local Storage component. By isolating these external interactions within the Infrastructure layer, the architecture reduces coupling between business logic and technical implementation details. If the data provider or storage mechanism changes, only this layer requires modification.

The direction of dependencies in the diagram demonstrates a controlled flow from Presentation to Application to Infrastructure. Higher layers depend on lower layers, but infrastructure components do not depend on application logic. This ensures maintainability, testability, and extensibility. Each component in the diagram has a clearly defined responsibility, reflecting high cohesion and structured separation of concerns.

Overall, the architecture directly mirrors the diagram structure and demonstrates a practical implementation of layered design principles.

---------------------------------------
## How the Architecture Supports Requirements and Project Goals

The architecture shown in the component specification diagram directly supports the functional requirements and broader project objectives.

The requirement to retrieve daily share prices within a selected date range is implemented through the interaction between the PriceController and the PriceComparisonService. The service validates the date range to ensure it does not exceed two years, as indicated in the diagram. This ensures that constraints are enforced consistently at the business logic level.

The ability to compare up to two symbols is handled within the PriceComparisonService. By centralising comparison logic within this component, the architecture ensures that all comparison related behaviour is managed in one location. This simplifies future expansion and improves reliability.

Offline functionality is achieved through the interaction between the PriceComparisonService, the MarketDataClient, and the LocalCacheStore. When the system is online, the service retrieves data from the external API through the MarketDataClient and then stores it locally using the LocalCacheStore. When offline, the service loads previously stored data from local storage. This workflow, illustrated in the diagram, ensures that the system remains partially usable without network connectivity.

Chart visualisation is supported through the ChartBuilder component. After price data is retrieved or loaded from cache, it is passed to ChartBuilder to produce a dataset suitable for rendering in the user interface. This separation keeps data processing distinct from presentation logic.

From a broader perspective, the architecture supports scalability and maintainability. The clear separation between layers ensures that changes to the user interface, business logic, or external integrations can be made independently. The diagram reflects a structured and well reasoned system design that aligns with both immediate requirements and long term project goals.
