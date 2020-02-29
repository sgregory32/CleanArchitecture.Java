## Java 8 Spring Boot Maven API using Clean Architecture  

#### Project Update  

Added Angular 8 Web UI project to access .Net API.  

<img src="AngularJS_google.png" alt="Angular logo" width="160" height="89">  

This solution consists of a Java 8 Spring Boot Maven Api developed using Clean Architecture principles. The solution contains the Api, Infrastructure, Core, and Tests.

Clean Architecture in this example is obtained through the implementation of the following:  

* Separation of Concerns/Single Responsibility: Api, Infrastructure (Data Access), Core(Domain Models, service & repository interfaces)
* Dependency Inversion Principle: All concrete class packages connect only through abstractions (interface or abstract class packages ) 
* Explicit Dependencies Principle: All dependencies requested via constructor (except Serilog)  

## Project Structure  

* The Api project contains the REST endpoints, services, log files, DTO models, & configuration.  
* The Infrastructure project contains the JPA repository extensions.  
* The Core project contains the business domain entities, and JPA repository interface.  

![Clean Architecture Diagram](clean_architecture.png)  
Clean Architecture Diagram

From the diagram above, the api & infrastrucure projects depend on the core project; all dependencies point inward to the core project. Inner projects define interfaces, outer projects implement the interfaces. None of the projects reference outward-positioned projects - inward references only. The Angular web project does not depend on other projects in this solution. It is a seperate project which hits the REST endpoints in the Java Api project. The Java Api runs seperatly as a Springboot microservice. The Angular project runs seperately on Node.js.

1.) The Angular UI project (not pictured below) calls the Java Api project REST endpoints.
2.) The Api project has references to the Infrastructure and Core projects.  
3.) The Infrastructure project only references the Core project.  
4.) The Core project has no other project references.  

## Prerequisites

Angular 8 with Bootstrap 4.4.1
Gson  
H2  
Hibernate  
Jpa (Java Persistence Api)  
ModelMapper   
Log4j2  

## Installing

1.) Clone or download the project  
2.) Open the solution in Eclipse, or IntelliJ  
3.) [Optional] If you have access to an MSSQL server, create a database called "OptBot"  
4.) [Optional] Run the commands in the CREATE Tables script.txt file in the "docs" folder  
5.) [Optional] Modify the solution connection strings to reflect your MSSQL environment  
6.) Build the solution  
7.) Run the solution  
8.) The Swagger implementation can be veiwed at: [host url]/swagger-ui.html

## Running the tests

To run all of the Api solution tests, right-click the "com.clean.architecture.api.tests" package >>> "Run as JUnit Test"

## Test Composition

There are two types of tests included in the solution. These tests use an in-memory database (H2) for tests. Here is the test breakdown:

1.) Repository Integration Tests: Tests the implementations of Jpa data repositories.  
2.) Functional Tests: Tests the Api REST controllers.  

## Built With

* Eclipse STS 4.5
* MSSQL Server 2017

## Disclaimer

This solution is provided as a simple implementation of clean architecture using Java/Spring/Maven & Angular. It is not meant to be used in any environment other than a development environment for learning purposes. By downloading, cloning, or any other means of implementing this solution, you agree to indemnify the author of all liability resulting from the use of this code.

## Author

* **Skip Gregory** - https://github.com/sgregory32
