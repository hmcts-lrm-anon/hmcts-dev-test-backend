# HMCTS Task Management Backend

A simple Spring Boot REST API for managing tasks. This backend provides basic CRUD operations for task management with validation and database persistence.

## Tech Stack

- **Java 21** - Modern Java LTS version
- **Spring Boot 3.5.5** - Application framework
- **Spring Data JDBC** - Database access
- **Jakarta Bean Validation** - Input validation and constraints
- **PostgreSQL** - Database
- **Lombok** - Reduces boilerplate code
- **JUnit 5** - Testing framework
- **MockMVC** - Unit testing of REST controllers

## Quick Start

### Prerequisites

- Java 21 or higher
- Docker (for PostgreSQL)

### Running the Application

1. Start the PostgreSQL database:

   ```bash
   cd docker
   docker-compose up -d
   ```

2. Build and run the application:
   ```bash
   ./gradlew bootRun
   ```

The application will start on **http://localhost:4000**

You can view all tasks directly at: **http://localhost:4000/tasks**

### Sample Data

To populate the database with sample tasks on startup, change `application.yaml`:

```yaml
spring:
  sql:
    init:
      mode: always # Change from 'never' to 'always'
```

This will load sample data from `data.sql` when the application starts.

## API Endpoints

| Method | Endpoint      | Description          |
| ------ | ------------- | -------------------- |
| GET    | `/tasks`      | Get all tasks        |
| GET    | `/tasks/{id}` | Get task by ID       |
| POST   | `/tasks`      | Create new task      |
| PUT    | `/tasks/{id}` | Update existing task |
| DELETE | `/tasks/{id}` | Delete task          |

## Task Model

```json
{
  "id": "number (auto-generated)",
  "title": "string (required)",
  "description": "string (optional)",
  "status": "string (required - see valid values below)",
  "dueDateTime": "string (ISO 8601 format, must be present or future)"
}
```

### Task Status Values

- `OPEN` - Task is open and ready for work
- `IN_PROGRESS` - Task is currently being worked on
- `COMPLETED` - Task has been completed

### Validation Rules

- **Title**: Required, cannot be empty
- **Description**: Optional field
- **Status**: Required, must be valid enum value
- **Due Date/Time**: Must be a valid present or future date

## Testing

API testing uses:

- **@WebMvcTest** - Spring Boot slice testing for controllers
- **@MockitoBean** - Mocking repository dependencies
- **JSONPath assertions** - Validating JSON response structure
- **Mockito** - Behavior verification and stubbing

Run tests with:

```bash
./gradlew test
```

## Future Improvements

These include:
- Global Exception Handling
- More sophisticated architecture (eg add a service layer between controller and repository)
- Add endpoints for filtering and searching (by status, date ranges, text search)
- Implement user authentication and authorization
- Add more comprehensive test coverage
- Consider more sophisticated documentation (JavaDoc currently used for endpoints)
