# Simple Inbox REST API

This project is the API implementation of an inbox system with Java & Spring boot framework.

The project uses H2 in-memory database to store the messages.

## Running the application in the Dev Mode
Run this command to start the project in the dev mode

```./gradlew bootRun```

This will start the app on the port 8080. The default messages list API can be seen in this URL:

`http://localhost:8080/api/messages`

On start, it should return an empty array.

## API Documentation (Swagger)

This project uses **Swagger (OpenAPI)** for API documentation.

After running the application, you can access the Swagger UI at:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

## Testing

To run the tests, run this command:

```./gradlew test```