# Project 1 Requirements
A Mavenized Java 8 web application connected to a PostgreSQL database. Submit a README.md with a proposal that matches most requirements below. You may use the example proposal below for reference, or as your project itself.

### Tools & APIs
- [] Java SE 8
- [] Maven 3+
- [] JDBC 4+
- [] PostgreSQL 9+
- [] Servlet 2.5+
- [] Tomcat 7+
- [] HTML/JS/CSS
- [] AJAX
- [] JUnit 4
- [] log4j 
- [] Optional:
    - [] Docker, Docker-Compose
    - [] Angular 8
    - [] Bootstrap
    - [] Remote hosting (AWS EC2/RDS)
    - [] Jenkins CI/Travis CI
    - [] Mockito

### Architecture
- [] Anemic/DDD package & class structure
- [] Design Patterns:
    - [] Dependency Injection
    - [] Data Access Object
    - [] Business Delegate
    - [] Model-View-Controller
    - [] Front Controller
- [] SQL Normalization
- [] PL/pgSQL
- [] Optional:
    - [] Single Page Application

### Functionality
- [] CRUD - Create, Read, Update, Delete
- [] Web App dashboard interface
- [] Asynchronous interface updates
- [] Login - Authentication & Authorization
- [] Database persistance
- [] Session management

### Presentation
- [] Prepare a demonstration of functionality requirements through a browser
- [] Prepare visual aides (slides) introducing the project requirements and features

## Example: Expense Reimbursement System
- An Employee...
    - [] can login
    - [] can view the Employee Homepage
    - [] can logout
    - [] can submit a reimbursement request
    - [] can upload an image of his/her receipt as part of the reimbursement request
    - [] can view their pending reimbursement requests
    - [] can view their resolved reimbursement requests
    - [] can view their information
    - [] can update their information
    - [] receives an email when one of their reimbursement requests is resolved (optional)

- A Manager...
    - [] can login
    - [] can view the Manager Homepage
    - [] can logout
    - [] can approve/deny pending reimbursement requests
    - [] can view all pending requests from all employees
    - [] can view images of the receipts from reimbursement requests
    - [] can view all resolved requests from all employees and see which manager resolved it
    - [] can view all Employees
    - [] can view reimbursement requests from a single Employee 
