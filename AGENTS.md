# AGENTS.md

# Java Desktop Applications — Engineering Guidelines

## 1. Project Philosophy and Scope

These guidelines define the engineering standards for Java desktop applications, particularly those built with Java Swing.

Projects may have different origins, purposes and levels of complexity. Some may be legacy applications, university projects, personal tools or applications that have been progressively modernized.

The objective is to maintain software that is:

- Functionally correct and consistent.
- Reliable and predictable.
- Readable and maintainable.
- Properly structured without unnecessary architectural complexity.
- Safe when handling data, resources and user input.

### Engineering Philosophy

**Prioritize correctness, simplicity and maintainability over architectural sophistication.**

Follow these principles:

- Apply sound software engineering practices regardless of project size.
- Prefer simple solutions over unnecessarily complex abstractions.
- Preserve existing functionality unless a change is explicitly requested.
- Improve existing code incrementally.
- Avoid unnecessary frameworks, dependencies and architectural layers.
- Do not implement hypothetical future requirements.
- Do not introduce complexity simply to demonstrate a design pattern or engineering principle.

A small application is not an excuse for poor code quality.

However, engineering quality must not be confused with architectural complexity.

---

## 2. Priority Order

When evaluating implementation decisions or resolving conflicting requirements, follow this priority order:

1. **Security and sensitive data:** Protect data, credentials and application resources.
2. **Correctness:** Existing behavior must not change unless explicitly requested.
3. **Scope:** Modify only what is necessary to fulfill the requirement.
4. **Reuse:** Prefer existing, reliable components over creating new ones.
5. **Design quality:** Apply SOLID, Clean Code and appropriate design patterns.

Architectural improvements must never justify breaking existing functionality or expanding the scope unnecessarily.

---

## 3. Stack and Project Context

Before modifying the application, identify and understand its actual technology stack.

Each repository should maintain the following information:

- Java version:
- Build system: Maven / Gradle / Ant / Other
- UI technology: Java Swing
- Persistence mechanism: H2 / MySQL / PostgreSQL / Files / Other
- Logging framework:
- Testing framework:
- Dependency injection mechanism, if any:
- Main application entry point:
- Build command:
- Test command:
- Relevant project documentation:

### Stack Guidelines

- Respect the Java version configured in the project.
- Follow existing dependency and framework conventions.
- Do not introduce new libraries, frameworks or architectural components without a clear technical justification.
- Do not add dependencies without explicit approval.
- Prefer standard Java APIs when they adequately solve the problem.
- Consider dependency licensing, maintenance and compatibility before proposing new libraries.
- Do not replace an existing technology simply because another alternative is available.

---

## 4. Before Writing Code

Before implementing any change:

1. Inspect the relevant source files and their dependencies.
2. Understand the existing execution flow and the callers of the affected methods.
3. Search for existing implementations that solve the same problem, by name and behavior.
4. Identify reusable components, utilities, services and existing business rules.
5. Understand the impact on existing functionality and persisted data.
6. Identify potential side effects and regression risks.
7. Propose a concise implementation plan when the change involves multiple components or significant design decisions.

Do not assume that the class being modified contains the complete implementation of the affected functionality.

Do not modify code based solely on assumptions about its behavior.

### Approval Before Implementation

Request confirmation before proceeding when:

- A change requires significant architectural restructuring.
- Existing persisted data or schemas may be destructively modified.
- A change introduces a new framework, library or major dependency.
- The requirement is ambiguous and different interpretations could produce materially different behavior.
- The change has significant impact beyond the requested functionality.

Routine changes should not require unnecessary approval.

---

## 5. Reuse and Legacy Code

Existing code should be evaluated before introducing new implementations.

### Reuse Guidelines

Reuse means calling existing functionality, not copying and duplicating its implementation.

Prefer the following order:

1. Use an existing component as is.
2. Extend an existing component without breaking its callers.
3. Create a new component when existing functionality cannot reasonably fulfill the requirement.

Additional rules:

- Avoid duplicated business logic.
- Follow the local coding style when modifying existing files.
- Do not reuse components known to be defective or deprecated without evaluating their limitations.
- Do not create parallel implementations of existing functionality.
- Do not refactor unrelated code.
- Document suggested improvements separately when they fall outside the requested scope.

### Legacy Code Guidelines

When working with legacy code:

- Understand existing behavior before modifying it.
- Preserve working functionality.
- Prefer incremental improvements over extensive rewrites.
- Introduce the smallest reasonable abstraction or interface needed to improve testability.
- Avoid redesigning entire classes when a localized change is sufficient.
- Consider characterization tests before changing existing, untested behavior.
- Clearly distinguish between existing defects and regressions introduced by new changes.

The objective is to improve the application progressively without destabilizing it.

---

## 6. Software Engineering Principles

All new code and modifications must follow these principles whenever applicable.

### 6.1 SOLID

- **Single Responsibility Principle (SRP):** Each class and method should have a clear, cohesive responsibility.
- **Open/Closed Principle (OCP):** Prefer designs that allow reasonable extension without repeatedly modifying stable, unrelated code.
- **Liskov Substitution Principle (LSP):** Implementations must respect the contracts of their parent types and interfaces.
- **Interface Segregation Principle (ISP):** Avoid unnecessarily large interfaces and forcefully coupled contracts.
- **Dependency Inversion Principle (DIP):** High-level business logic should not depend unnecessarily on low-level implementation details.

Do not introduce interfaces or abstractions without a concrete reason.

### 6.2 Clean Code

- Use meaningful and descriptive names.
- Keep methods focused and reasonably small.
- Avoid duplicated logic.
- Avoid deeply nested conditionals when simpler structures are possible.
- Prefer early returns when they improve readability.
- Use constants instead of unexplained magic numbers or strings.
- Avoid unnecessary comments.
- Comments should explain intent, decisions or non-obvious behavior.
- Avoid excessive method parameters.
- Use parameter objects or Builders when they genuinely improve readability.
- Keep business rules separate from presentation logic.
- Remove dead code and unused imports when safely identified.

### 6.3 KISS and YAGNI

- Keep implementations as simple as possible without compromising correctness.
- Do not implement hypothetical future requirements.
- Avoid premature optimization.
- Do not generalize solutions for problems that do not exist.
- Prefer straightforward implementations over elaborate abstractions when both provide equivalent functionality.

---

## 7. Design Patterns

The following ten design patterns are part of the preferred engineering toolbox.

Their application is conditional, not mandatory.

**Do not force a design pattern into the code simply because it appears in this list.**

| Pattern | Potential application |
|---|---|
| Singleton | Controlled access to a unique application resource or shared component. |
| Factory Method | Centralized creation of objects when construction decisions vary. |
| Abstract Factory | Creation of related families of objects when multiple implementations are genuinely required. |
| Builder | Construction of complex objects with multiple optional parameters or configurations. |
| Prototype | Cloning objects when creating instances from existing configurations is beneficial. |
| Adapter | Integration of incompatible interfaces without modifying their original implementations. |
| Facade | Simplification of access to a subsystem with multiple internal components. |
| Decorator | Extension of object behavior without modifying its original implementation or creating excessive subclasses. |
| Observer | Notification of interested components when application state or data changes. |
| Strategy | Encapsulation of interchangeable business rules or algorithms. |

### Pattern Implementation Guidelines

- Select patterns based on an actual design problem.
- Prefer the simplest applicable pattern.
- Avoid combining patterns unnecessarily.
- Do not introduce interfaces solely to satisfy a pattern.
- Document non-obvious architectural decisions.
- Avoid Singleton as a default mechanism for sharing dependencies or application state.
- Remember that Swing already provides event-driven mechanisms that can satisfy Observer-like requirements.
- Prefer existing Java and Swing capabilities when they already provide the required behavior.

The objective is to improve cohesion, reduce coupling and make future modifications easier.

---

## 8. Java and Object-Oriented Programming

- Follow established Java conventions and the language version configured in the project.
- Encapsulate internal state.
- Avoid exposing mutable implementation details unnecessarily.
- Prefer composition over inheritance when appropriate.
- Use interfaces to define meaningful contracts.
- Apply appropriate access modifiers.
- Avoid static mutable variables unless there is a clear justification.
- Use enums for finite sets of related constants.
- Use collections and generics appropriately.
- Avoid raw types and unchecked casts whenever possible.
- Override equals() and hashCode() consistently when object equality requires it.
- Implement toString() when it provides useful debugging information.
- Avoid unnecessary object creation in frequently executed code.
- Avoid tightly coupling business logic to concrete implementations.

### Dependency Management

- Prefer constructor injection when dependency injection is used.
- Avoid instantiating infrastructure dependencies directly inside business logic.
- Use dependency injection only when it provides a meaningful benefit.
- Do not introduce a dependency injection framework solely for the sake of using one.

---

## 9. Null Safety and Defensive Programming

**Prevent NullPointerExceptions (NPE) and other avoidable runtime failures.**

This is a fundamental quality requirement.

- Never assume that a method, query or UI component will return a non-null value unless its contract guarantees it.
- Validate inputs at appropriate boundaries.
- Establish clear expectations for constructor parameters and method arguments.
- Initialize collections and prefer empty collections over null collections when appropriate.
- Avoid returning null when an empty result or meaningful alternative is more appropriate.
- Check database and file-processing results before accessing their properties.
- Handle potentially absent UI selections and optional fields explicitly.
- Use Objects.requireNonNull() when enforcing mandatory dependencies or constructor arguments.
- Use Optional where it improves the expression of an optional return value.
- Do not use Optional indiscriminately, particularly as a replacement for ordinary field validation.
- Avoid excessive defensive checks that obscure the normal execution path.

Defensive programming must protect real boundaries and assumptions, not turn every method into a collection of redundant null checks.

---

## 10. Java Swing and Desktop Application Guidelines

The application uses Java Swing. Its desktop architecture and event-driven nature must be respected.

### 10.1 Separation of Concerns

Keep the following responsibilities reasonably separated:

- **Presentation:** Windows, dialogs, forms, Swing components and visual behavior.
- **Application/Business Logic:** Use cases, validations and business rules.
- **Persistence:** Database access, file operations and data storage.
- **Domain:** Core entities and business concepts.

Use the existing project architecture as the starting point.

Do not introduce additional layers unless they provide a concrete benefit.

Avoid placing database operations, file processing or complex business rules directly inside Swing event listeners.

### 10.2 Event Dispatch Thread (EDT)

- Swing UI creation and updates must happen on the Event Dispatch Thread.
- Use SwingUtilities.invokeLater() when scheduling UI operations from outside the EDT.
- Never execute long-running database operations, network requests or other blocking tasks on the EDT.
- Use SwingWorker or an appropriate background execution mechanism when operations may block the interface.
- Update Swing components on the EDT after background operations complete.
- Avoid unnecessary synchronization on UI components.
- Consider cancellation and error handling for background operations.

### 10.3 UI Consistency

- Preserve the application's existing visual style and interaction conventions.
- Reuse existing components and common UI behavior where appropriate.
- Validate user input before executing operations.
- Provide meaningful feedback for successful operations and failures.
- Avoid silently swallowing exceptions.
- Prevent duplicate submissions where repeated actions could cause inconsistent data.
- Avoid blocking the interface during long-running operations.
- Ensure that UI state remains consistent with the underlying application state.

---

## 11. Data Persistence and Data Integrity

Applications may use different persistence mechanisms, including relational databases, embedded databases, NoSQL databases, flat files or other storage mechanisms.

The agent must respect the persistence technology and existing implementation of each project.

Regardless of the persistence mechanism, follow these principles.

### 11.1 Data Integrity

- Preserve data consistency across operations.
- Validate data before persisting it.
- Avoid partial updates or inconsistent states.
- Ensure that operations involving multiple changes are atomic whenever the underlying technology supports it.
- Respect existing data structures, schemas and formats.
- Consider backward compatibility when modifying persisted data.
- Never discard or overwrite existing data unintentionally.
- Ensure that application state remains consistent with persisted data.

### 11.2 Resource Management

- Release resources reliably after use.
- Close connections, statements, result sets, streams, readers and writers appropriately.
- Prefer try-with-resources for Java resources that implement AutoCloseable.
- Avoid resource leaks and unnecessarily long-lived connections or file handles.
- Manage concurrent access to shared resources when necessary.

### 11.3 Database Operations

When working with relational or other database systems:

- Separate persistence responsibilities from business logic and presentation.
- Use parameterized queries or prepared statements instead of concatenating user-provided values into SQL.
- Use transactions when multiple operations must succeed or fail together.
- Handle connection failures, timeouts and persistence exceptions explicitly.
- Avoid unnecessary queries and duplicated database access logic.
- Consider the performance implications of queries and data retrieval.
- Never assume that a query will always return records.
- Respect the existing database schema and data compatibility.

### 11.4 File-Based Persistence

When working with flat files or other file-based storage:

- Handle file and directory availability explicitly.
- Validate file contents before processing them.
- Handle malformed, incomplete or unexpected data gracefully.
- Avoid corrupting existing files during write operations.
- Consider temporary files and atomic replacement when appropriate.
- Use explicit character encodings, preferably UTF-8.
- Handle concurrent access when multiple operations may read or modify the same file.

### 11.5 General Guidelines

- Do not introduce a new persistence technology without a clear requirement.
- Do not change schemas, file formats or persistence contracts without evaluating their impact.
- Keep persistence implementation details encapsulated.
- Do not silently ignore persistence failures.
- Never report a successful operation when data was not actually persisted.
- Respect the guarantees and limitations of the underlying persistence mechanism.

The objective is reliable data storage, predictable behavior and preservation of data integrity, regardless of the underlying persistence technology.

---

## 12. Exception Handling and Logging

### 12.1 Exception Handling

- Never use empty catch blocks.
- Never catch generic Exception unless there is a justified boundary-level reason.
- Catch the most specific exception that can actually be handled.
- Do not use exceptions as a replacement for ordinary conditional logic or expected business outcomes.
- Preserve the original exception as the cause when wrapping exceptions.
- Do not silently ignore unexpected failures.
- Handle exceptions at the appropriate responsibility boundary.
- Provide meaningful error information for troubleshooting.
- Do not expose unnecessary technical details to end users.
- Ensure that failed operations do not leave the application in an inconsistent state.

Expected business outcomes, such as validation failures or missing records, should be represented explicitly rather than unnecessarily using exceptions.

### 12.2 Logging

Use the logging mechanism already established in the project.

Prefer structured logging when supported by the existing framework.

Use the following levels according to their intended purpose:

- **ERROR:** An operation failed and requires attention. Include the exception when available.
- **WARN:** An unexpected situation occurred, but the application recovered or continued with degraded behavior.
- **INFO:** Relevant application or business events.
- **DEBUG:** Diagnostic information useful during development and troubleshooting.

Additional guidelines:

- Do not log sensitive information.
- Include relevant identifiers or transaction references when available.
- Avoid excessive logging inside frequently executed operations.
- Do not log the same exception repeatedly at multiple layers without justification.
- Never use logging as a replacement for proper exception handling.
- Avoid introducing a logging framework unnecessarily.

---

## 13. Security and Sensitive Data

Security must be considered even in small desktop applications.

- Never hardcode passwords, API keys, tokens or other secrets.
- Do not commit credentials or sensitive configuration files.
- Avoid exposing sensitive information in logs, exception messages or UI error dialogs.
- Validate user input at application boundaries.
- Do not trust data loaded from external files, databases or services without appropriate validation.
- Use parameterized SQL queries.
- Avoid insecure deserialization mechanisms.
- Protect sensitive files and configuration according to the operating system and deployment environment.
- Apply appropriate access controls when the application handles restricted information.
- Avoid unnecessary storage of sensitive information.

The objective is to prevent accidental data exposure and avoidable security vulnerabilities.

---

## 14. Testing and Quality Assurance

The objective is to prevent regressions and ensure that implemented functionality behaves correctly.

### 14.1 Testing Priorities

Prioritize testing around:

- Business rules and validations.
- Database operations and persistence.
- File processing and data integrity.
- Object construction and state consistency.
- Important application workflows.
- Edge cases, especially null or missing data.
- Changes that affect existing functionality.
- Error handling and failure scenarios.

### 14.2 Testing Guidelines

- Write unit tests for business logic whenever practical.
- Test persistence operations against an appropriate test database or isolated test environment.
- Avoid tests that depend unnecessarily on production or local application data.
- Prefer deterministic tests.
- Test both successful and failure scenarios.
- Include relevant edge cases.
- Avoid tests without meaningful assertions.
- Avoid tests that only verify that a mock was called when the resulting behavior can be asserted.
- Do not introduce complex testing infrastructure for simple functionality.
- Execute the available build and test commands after making changes.
- Report tests that could not be executed instead of claiming that the implementation is verified.

### 14.3 Legacy Code Testing

Before modifying existing, untested functionality, consider adding characterization tests to capture its current behavior.

This helps distinguish intentional changes from accidental regressions.

### 14.4 Test Data

- Use synthetic test data.
- Never use real sensitive customer or personal information in test fixtures.
- Keep tests independent of uncontrolled external state.

Testing effort should be proportional to the complexity and risk of the change.

---

## 15. Definition of Done

A change is considered complete when:

- [ ] The requested functionality works as expected.
- [ ] Existing functionality remains intact unless explicitly changed.
- [ ] The implementation follows SOLID and Clean Code principles where applicable.
- [ ] The solution does not introduce unnecessary architectural complexity.
- [ ] Nullability and error conditions have been considered.
- [ ] Resources are managed correctly.
- [ ] Persistence operations preserve data integrity.
- [ ] No avoidable runtime failures are introduced.
- [ ] Relevant tests have been executed and pass, or limitations are explicitly reported.
- [ ] The application builds successfully without new unexplained warnings.
- [ ] No sensitive information has been exposed.
- [ ] The implementation has been reviewed for readability and maintainability.

After implementation, provide a concise summary containing:

1. What changed and why.
2. Which existing components were reused.
3. Which new components were created and why.
4. Tests and build checks executed.
5. Known limitations or remaining risks.

---

## 16. Working Agreement for AI Agents

The agent is expected to behave as a responsible software engineer, not simply as a code generator.

### Before Implementation

- Understand the requirement.
- Inspect the existing implementation.
- Identify dependencies and potential side effects.
- Search for reusable functionality.
- Evaluate the simplest reasonable solution.
- Explain significant architectural decisions before implementing them.

### During Implementation

- Make the smallest reasonable change that solves the requirement.
- Maintain consistency with the existing project.
- Follow SOLID and Clean Code principles.
- Apply design patterns only when they solve a real problem.
- Handle nullability, exceptions and resource management explicitly.
- Avoid unrelated refactoring.
- Do not introduce unnecessary dependencies.
- Preserve existing behavior.

### After Implementation

- Review modified code for correctness and readability.
- Check for possible NPEs, resource leaks and unhandled exceptions.
- Verify that persistence operations preserve data integrity.
- Consider concurrency and EDT requirements for Swing operations.
- Run relevant tests and build checks.
- Report any remaining limitations honestly.

### Expected Behavior

When a requirement is ambiguous, inspect the existing implementation and use the simplest interpretation consistent with the application.

If a proposed solution introduces significant architectural complexity, explain why it is necessary.

Do not rewrite working code simply to make it conform to a preferred architecture or design pattern.

Do not claim that functionality works without appropriate verification.

**Final Engineering Principle**

Build software that is correct, consistent, reliable, maintainable and as simple as reasonably possible.

Do not confuse engineering quality with architectural complexity.
