# SEERBIT Remittance Payout API.
-
This project provides a basic illustration of an integrating with SEERBIT payout API.

The application uses SpringBoot 3, Junit5 and Mockito. Please use at least Java 17 to run the project<break>

The application runs on port 8080 and is accessible at:
- http://localhost:8080/seerbit/api/v1/ <br>

The API documentation is accessible at:
- http://localhost:8080/swagger-ui/index.html#/ <br>

The application also features integration tests for both:
- Service layer
- REST controller layer <br>

Improvements:
- for production code quality, more tests coverage should be employed (Unit tests inclusive)
- proper exception handling using controller advice
- client API credentials should rather be accessed through secrets for better security rather than in the code as properties
- If a fintech product is to be built around this API, I will recommend design patterns such as CQRS and Event-sourcing
