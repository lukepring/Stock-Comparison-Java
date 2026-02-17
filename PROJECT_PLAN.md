# Project Execution Plan  

This document describes the structured timeline for the completion of coursework in three sprints. The plan is in sync with the official deadlines for the sprints and has evenly distributed the workload among the four team members.

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
