# Project Execution Plan  

This document describes the structured timeline for the completion of coursework in three sprints. The plan is in sync with the official deadlines for the sprints and has evenly distributed the workload among the four team members.

---

# Requirements Specification  

## Functional Requirements  

1. The system shall allow the user to search for a share using a valid stock ticker symbol.  
2. The system shall retrieve historical share price data from Yahoo Finance.  
3. The system shall allow the user to select a time range of up to two years.  
4. The system shall validate that the selected time range does not exceed two years.  
5. The system shall display historical price data in graphical format.  
6. The system shall allow the user to compare two shares on the same chart.  
7. The system shall clearly label each share within the comparison chart.  
8. The system shall store retrieved share data locally for offline access.  
9. The system shall retrieve stored data when the external data source is unavailable.  
10. The system shall provide feedback when an invalid ticker symbol is entered.  
11. The system shall provide feedback when external data retrieval fails.  
12. The system shall expose core use cases through REST endpoints.  
13. The system shall separate business logic from presentation logic.  
14. The system shall allow the user to refresh share data manually.  
15. The system shall log system level errors for debugging purposes.  

---

## Non Functional Requirements  

### Performance  
1. The system shall retrieve and display share data within 3 seconds under normal network conditions.  
2. The system shall handle at least two simultaneous share comparisons without performance degradation.  

### Scalability  
3. The architecture shall support extension to more than two share comparisons in future iterations.  
4. The system shall support modular expansion without modification of core business logic.  

### Maintainability  
5. The system shall follow Clean Architecture principles.  
6. The system shall maintain clear separation between domain, application, infrastructure, and presentation layers.  
7. The codebase shall be structured to allow independent testing of business logic.  

### Reliability  
8. The system shall provide offline access to previously retrieved share data.  
9. The system shall handle external API failures gracefully without crashing.  

### Usability  
10. The user interface shall be clear and intuitive.  
11. The system shall provide meaningful validation messages for incorrect input.  
12. Charts shall be clearly labelled and readable.  

### Security  
13. The system shall validate all user input before processing.  
14. The system shall protect API communication through secure protocols where applicable.  

### Portability  
15. The system shall run on standard modern web browsers.  
16. The system shall be deployable without requiring platform specific configuration.  

### Testability  
17. Core use cases shall be unit testable without requiring external API access.  
18. Interfaces shall support mocking for automated testing.

---

# Team Structure  

We chose to use a structured team approach in which each member is given specific responsibility for a particular aspect of the project, such as architecture design, infrastructure and data management, domain logic implementation, or presentation and integration,
because this targeted approach to role definition enables each member to gain a deeper level of technical expertise in their particular area, enables accountability for specific deliverables, eliminates overlap and confusion regarding task responsibility, 
and ultimately enables the entire system to be designed and built to a higher level of quality. (The member allocation will be decided after Phase 1)

**Member A – Architecture and Documentation Lead**  
Responsible for architectural modelling, UML diagrams, design justification, and report coordination.

**Member B – Infrastructure and Data Lead**  
Responsible for external data integration, persistence, offline functionality, and infrastructure layer implementation.

**Member C – Application and Domain Lead**  
Responsible for domain modelling, business logic implementation, validation, and unit testing.

**Member D – Presentation and Integration Lead**  
Responsible for user interface development, controller implementation, chart integration, and system integration testing.

---

# Phase 1  
## Sprint 1 – Introduction to Architectural Principles  
**Deadline:** 19 February 2026  

### Objective  
To establish the project infrastructure, set up development tools, and prepare initial project documentation to provide a clear foundation for the subsequent sprints.  

### Team Activities  
- Create GitHub repository and configure initial repository structure  
- Set up project management Kanban board  
- Create initial `README.md` file  
- Define and commit Code of Conduct  
- Write Project Execution Plan  
- Create component specification diagram  
- Set up Java project with initial classes and placeholder elements  

### Member Responsibilities  

**Luke**  
- Create GitHub repository  
- Configure Kanban board  
- Write initial `README.md`  

**Jack**  
- Draft and commit Code of Conduct  
- Write Project Execution Plan  

**Azlan**  
- Design and produce the Component Specification Diagram  

**Sameer**  
- Set up Java project  
- Add initial classes and placeholder elements for future development  

### Sprint 1 Deliverables  
- GitHub repository with project structure  
- Populated Kanban board  
- `README.md` file  
- Code of Conduct document  
- Project Execution Plan document  
- Component Specification Diagram  
- Java project initialized with placeholder elements 

---

# Phase 2  
## Sprint 2 – Architectural Modelling  
**Deadline:** 19 March 2026  

### Objective
Develop architecture from requirements using formal models  

### Member Responsibilities  

**Member A**  
- Create Business Concept Model  
- Create Use Case Diagram  
- Write detailed use case descriptions  

**Member B**  
- Define system interfaces and operations  
- Document interface contracts clearly  

**Member C**  
- Develop Business Type Model  
- Align domain classes with concept model  

**Member D**  
- Design Initial System Architecture diagram  
- Allocate interfaces to components  
- Identify components implementing multiple interfaces  

### Deliverables  
- Business Concept Model  
- Use Case Diagram and descriptions  
- System Interfaces documentation  
- Business Type Model  
- Initial System Architecture diagram  

---

# Phase 3  
## Sprint 2 – Clean Architecture Implementation  

### Member Responsibilities  

**Member B**  
- Implement `YahooFinanceService`  
- Integrate external API  
- Implement parsing and error handling  

**Member C**  
- Implement `FetchSharePricesUseCase`  
- Implement `CompareSharesUseCase`  
- Enforce two year validation rule  

**Member D**  
- Integrate controller with use cases  
- Display single share chart  

**Member A**  
- Review dependency structure  
- Ensure Clean Architecture compliance  
- Document architectural rationale  

### Sprint 2 Deliverables  
- External data retrieval operational  
- Local persistence functioning  
- Single share chart displayed  
- Clean dependency structure verified  
- Updated UML aligned with implementation  

---

# Phase 4  
## Sprint 3 – Compound Components and Architectural Styles  
**Deadline:** 23 April 2026

### Objective
Refactor architecture and apply domain independent styles  

### Member Responsibilities  

**Member A**  
- Redesign architecture diagram into compound components  
- Explicitly document applied styles such as Layered Architecture and Model View Controller  

**Member B**  
- Refactor infrastructure into cohesive Data Component  
- Implement Adapter pattern for external API  
- Implement offline fallback logic  

**Member C**  
- Refactor business logic into reusable Business Component  
- Write unit tests for application layer  

**Member D**  
- Refactor UI into clear MVC structure  
- Implement two share comparison feature  

### Deliverables  
- Compound component diagram  
- Refactored modular architecture  
- Working comparison feature  
- Unit tests committed  

---

# Phase 5  
## Sprint 3 – SOA Integration, Testing, and Finalisation    

### Member Responsibilities  

**Member C**  
- Expose use cases as REST endpoints  
- Ensure separation between service and domain logic  

**Member B**  
- Define JSON service contracts  
- Conduct integration tests for data layer  

**Member D**  
- Integrate REST endpoints with UI  
- Create functional UI test cases  

**Member A**  
- Document SOA principles applied  
- Compile structured test case table  
- Finalise and structure group report  

### Final Deliverables  
- Fully functioning system  
- Compound components implemented  
- Architectural styles applied clearly  
- REST services operational  
- Offline functionality validated  
- Documented test cases and results  
- Complete and well structured group report  
- Consistent GitHub contribution history  
