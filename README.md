# Axiomatics Demo
This is an implementation of a simple microservice having an inventory of entities related to Axiomatics (AttributeDefinition).
<br/>
**Note: The project requires a running PostgreSQL instance.** 

### How to build/run
* Run `./gradlew build` to build the application jar.
* Run just the service only by using `./gradlew bootRun`, but you need to have a PostgreSQL instance running. Check application.yml for the connection details.

 
## Features

- CRUD operations for attribute definitions and categories
- Category-based attribute grouping
- DTO-based separation of domain and API layers
- Exception handling and input validation
- Integration-ready for frontend or external systems
- Database schema generation and data entry through Spring boot
- Exception Handling on Requests
  
## Getting Started

### Prerequisites

- Java 17+
- Gradle 7.x
- Spring Boot 3.x
- Docker for containerized database or app deployment (Optional )

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/denevias/attribute-management-api.git
   cd attribute-management-api
   
### API Endpoints
- **Create Attribute Definition**
  - `PUT /api/v1/attributes/`
  - Request Body: JSON object representing the attribute definition
  - Response: Created attribute definition object
- **Get All Attribute Definitions**
  - `GET /api/v1/attributes/`
  - Response: List of all attribute definitions
  - Query Parameters: `page`, `size`, `sort` for pagination and sorting
  - Response: List of attribute definitions
- **Get Attribute Definition by CategoryID**
  - `GET /api/v1/attributes/category/{id}`
  - Response: Attribute definition object with the specified ID
- **Update Attribute Definition**
- `PUT /attributes/update/{id}`
  - Request Body: JSON object representing the updated attribute definition
    - Response: Updated attribute definition object
- **Delete Attribute Definition**
  - `DELETE /api/v1/attributes/{id}`
  - Response: Success message


### Improvements
(Optional) Docker for containerized database or app deployment
Provided Dockerfile for the application [Docker Compose file](docker-compose.yml)

...
