# Getting Started
This is a simple Spring Boot application that provides a RESTful API for managing user and simple quota mechanism.
The application is built using Java 21. The application uses a MySQL and ElasticSearch  to store user information.
## Current Implementation

The current implementation uses a `ConcurrentHashMap` to store the quota information for each userEntity. The key of the map is the userEntity ID and the value is the number of requests made by the userEntity. The maximum number of requests per userEntity is defined by the constant `MAX_REQUESTS_PER_USER`.

## Usage
To run the application, follow these steps:
- clone the repository to your local machine
- run mySql database on port `3306` with db `quota-db`, username `user` and password `password`
- run ElasticSearch on port `9200` with username `elastic` and password `6361`
- run the application
- use the following endpoints to test the application
  - `GET http://localhost:8080/users/quota` to get all quotas
  - `POST http://localhost:8080/users/quota/{userId}` to consume a quota for a specific user
  
  - `GET http://localhost:8080/users/{userId}` to get the user by id
  - `POST http://localhost:8080/users` to create a new user
  - `PUT http://localhost:8080/users/{userId}` to update a user
  - `DELETE http://localhost:8080/users/{userId}` to delete a user
## Potential Upgrades

Potential upgrades for this service could include:

- Storing the quota information in a database for persistence.
- Implementing new `QuotaManagementService` using a distributed cache like Redis for scalability and performance.
- Add integration tests to ensure the service works as expected in a real environment.


