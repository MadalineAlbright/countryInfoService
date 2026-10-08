# Country Info Application – Technical Specification

## 1. Overview

The **Country Info Application** is a Spring Boot microservice that exposes a REST API for managing country information and persists the data in MySQL.

The application is containerized using Docker and can be run locally using Docker Compose or deployed to Kubernetes.

### Objectives

* Provide a RESTful API for country information.
* Persist country data in MySQL.
* Separate API, business logic, and data access responsibilities.
* Containerize the application for consistent deployment.
* Deploy the application and database to Kubernetes.
* Support persistent database storage.
* Provide a foundation for future scalability and production hardening.

---

# 2. Technology Stack

| Technology      | Purpose                                |
| --------------- | -------------------------------------- |
| Java            | Application development                |
| Spring Boot     | REST API and application framework     |
| Spring Data JPA | Database access                        |
| MySQL           | Relational data persistence            |
| Maven           | Build and dependency management        |
| Docker          | Application containerization           |
| Docker Compose  | Local multi-container environment      |
| Kubernetes      | Container orchestration and deployment |
| kubectl         | Kubernetes management                  |

---

# 3. High-Level Architecture

```text
                         Client
                           |
                           | HTTP/REST
                           v
                 ┌───────────────────┐
                 │  Country Info API │
                 │    Spring Boot    │
                 └─────────┬─────────┘
                           |
                           v
                 ┌───────────────────┐
                 │      Service      │
                 │  Business Logic   │
                 └─────────┬─────────┘
                           |
                           v
                 ┌───────────────────┐
                 │    Repository     │
                 │   Spring Data JPA │
                 └─────────┬─────────┘
                           |
                           v
                 ┌───────────────────┐
                 │       MySQL       │
                 │     Database      │
                 └───────────────────┘
```

The application follows a layered architecture:

**Controller → Service → Repository → Database**

This separates responsibilities and reduces direct coupling between the API and database.

---

# 4. Application Architecture

## 4.1 Controller Layer

The Controller layer exposes the REST API endpoints.

Responsibilities:

* Receive HTTP requests.
* Validate and map request data.
* Call the appropriate service.
* Return HTTP responses.

The controller does not directly access the database.

## 4.2 Service Layer

The Service layer contains the application's business logic.

Responsibilities:

* Process application operations.
* Coordinate between controllers and repositories.
* Apply business rules.
* Handle application-level exceptions where required.

## 4.3 Repository Layer

The Repository layer is responsible for persistence.

Spring Data JPA is used to communicate with MySQL and perform database operations.

## 4.4 Model / Entity

The entity represents the country data stored in the database.

The entity is mapped to a relational database table using JPA annotations.

## 4.5 DTOs

DTOs are used at the API/presentation boundary to define the data exchanged between the client and application.

They prevent the database entity from being exposed directly through the API and allow the API contract to evolve independently from the persistence model.

```text
Request JSON
     |
     v
Request DTO
     |
     v
Controller
     |
     v
Service
     |
     v
Entity
     |
     v
Repository
     |
     v
MySQL
```

For responses, the process is reversed and the application returns the appropriate response DTO as JSON.

---

# 5. Integration Pattern

The application uses a **synchronous request-response REST integration pattern**.

```text
Client
  |
  | HTTP Request
  v
Country Info API
  |
  | Database Query
  v
MySQL
  |
  | Result
  v
Country Info API
  |
  | HTTP Response
  v
Client
```

REST was selected because it is lightweight, widely supported, and appropriate for exposing a simple HTTP-based microservice.

The API communicates using JSON.

---

# 6. API Specification

## Base Path

```text
/api/countries
```

## Endpoints

| Method | Endpoint              | Description              |
| ------ | --------------------- | ------------------------ |
| GET    | `/api/countries`      | Retrieve all countries   |
| GET    | `/api/countries/{id}` | Retrieve a country by ID |
| POST   | `/api/countries`      | Create a country         |
| PUT    | `/api/countries/{id}` | Update a country         |
| DELETE | `/api/countries/{id}` | Delete a country         |

### GET `/api/countries`

Retrieves all available countries.

Example:

```http
GET /api/countries
```

Example response:

```json
{
  "content": [
    {
      "capitalCity": "Kampala",
      "continent": "AF",
      "countryFlag": "http://www.oorsprong.org/WebSamples.CountryInfo/Flags/Uganda.jpg",
      "countryName": "Uganda",
      "currency": null,
      "currencyCode": "UG",
      "id": 9,
      "isoCode": "UG",
      "languages": [
        {
          "languageCode": "eng",
          "languageName": "English"
        }
      ],
      "phoneCode": "256"
    },
    {
      "capitalCity": "Nairobi",
      "continent": "AF",
      "countryFlag": "http://www.oorsprong.org/WebSamples.CountryInfo/Flags/Kenya.jpg",
      "countryName": "Kenya",
      "currency": null,
      "currencyCode": "KES",
      "id": 11,
      "isoCode": "KE",
      "languages": [
        {
          "languageCode": "swa",
          "languageName": "Swahili"
        }
      ],
      "phoneCode": "254"
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 2,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "unpaged": false
  },
  "size": 20,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "totalElements": 2,
  "totalPages": 1
}
```

### GET `/api/countries/{id}`

Retrieves a specific country.

```http
GET /api/countries/1
```

Example response:

```json
{
  "capitalCity": "Kampala",
  "continent": "AF",
  "countryFlag": "http://www.oorsprong.org/WebSamples.CountryInfo/Flags/Uganda.jpg",
  "countryName": "Uganda",
  "currency": null,
  "currencyCode": "UG",
  "id": 9,
  "isoCode": "UG",
  "languages": [
    {
      "languageCode": "eng",
      "languageName": "English"
    }
  ],
  "phoneCode": "256"
}
```

### POST `/api/countries`

Creates a new country.

```http
POST /api/countries
Content-Type: application/json
```

Example request:

```json
{
  "name": "Kenya"
}
```

### PUT `/api/countries/{id}`

Updates an existing country.

```http
PUT /api/countries/1
Content-Type: application/json
```

Example request:

```json
{
  "countryName": "Uganda",
  "capitalCity": "Kampala",
  "continent": "AF",
  "currency": "UG",
  "currencyCode": "UGX",
  "phoneCode": "256",
  "countryFlag": "http://www.oorsprong.org/WebSamples.CountryInfo/Flags/Uganda.jpg"
}
```

### DELETE `/api/countries/{id}`

Deletes a country.

```http
DELETE /api/countries/1
```

---

# 7. HTTP Response Codes

The API uses standard HTTP status codes.

| Status                      | Meaning                           |
| --------------------------- | --------------------------------- |
| `200 OK`                    | Request completed successfully    |
| `201 Created`               | Resource successfully created     |
| `400 Bad Request`           | Invalid request                   |
| `404 Not Found`             | Requested resource does not exist |
| `500 Internal Server Error` | Unexpected application error      |

---

# 8. Database Design

MySQL is used as the application's relational database.

The country entity is persisted as a relational record.

A simplified representation is:

```text
Country
-----------------
id
name
capital
currency
```

Spring Data JPA handles communication between the application and MySQL.

## Why MySQL?

MySQL was selected because the application works with structured relational data.

It provides:

* Relational data modelling
* SQL querying
* Strong Spring Boot/JPA support
* Easy Docker integration
* Reliable persistent storage

For production, the database could be moved to a managed service such as:

* Amazon RDS for MySQL
* Azure Database for MySQL
* OCI MySQL HeatWave

These services can provide managed backups, monitoring, availability, and database administration.

Oracle Database could also be considered where existing enterprise infrastructure or requirements justify it. However, moving from MySQL to Oracle would require database-specific changes and testing.

---

# 9. Configuration Management

Application configuration is externalized rather than hardcoded.

Typical database configuration includes:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

In Kubernetes:

* **ConfigMap** stores non-sensitive configuration.
* **Secret** stores database credentials.

The application connects to the database using the Kubernetes MySQL Service name.

Example:

```text
DB_HOST=mysql
DB_PORT=3306
```

This allows the application configuration to change between environments without rebuilding the application image.

---

# 10. Docker Architecture

The application is packaged into a Docker image.

The local environment consists of:

```text
Docker Compose
      |
      +-------------------+
      |                   |
      v                   v
Country Info          MySQL
Container             Container
```

Docker Compose provides networking between the containers and allows the application to communicate with MySQL without using `localhost`.

### Build

```bash
mvn clean package
```

### Start

```bash
docker compose up --build
```

### Check

```bash
docker compose ps
```

### Stop

```bash
docker compose down
```

---

# 11. Kubernetes Architecture

The Kubernetes deployment separates the application and database.

```text
                         Client
                           |
                           v
                 Country Info Service
                           |
                           v
              Country Info Deployment
                           |
                           v
                    MySQL Service
                           |
                           v
                    MySQL Deployment
                           |
                           v
                 PersistentVolumeClaim
```

## Kubernetes Resources

| Resource              | Purpose                               |
| --------------------- | ------------------------------------- |
| Deployment            | Manages application/database pods     |
| Service               | Provides stable network communication |
| ConfigMap             | Stores non-sensitive configuration    |
| Secret                | Stores database credentials           |
| PersistentVolumeClaim | Provides persistent MySQL storage     |

The application connects to MySQL using the Kubernetes Service name.

This is important because pod IP addresses are not permanent.

---

# 12. Kubernetes Deployment

The deployment files are stored under:

```text
k8s/
├── configmap.yaml
├── secret.yaml
├── pvc.yaml
├── mysql-deployment.yaml
├── mysql-service.yaml
├── deployment.yaml
└── service.yaml
```

Resources can be deployed using:

```bash
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/pvc.yaml
kubectl apply -f k8s/mysql-deployment.yaml
kubectl apply -f k8s/mysql-service.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

Verify:

```bash
kubectl get pods
kubectl get services
kubectl get pvc
```

For Minikube:

```bash
minikube service country-info --url
```

---

# 13. Scalability

The Spring Boot application is designed to be stateless, allowing multiple application instances to run concurrently.

Example:

```bash
kubectl scale deployment country-info --replicas=3
```

The application can therefore scale horizontally by adding more pods.

The database is stateful and should not simply be scaled by creating additional independent MySQL pods.

For production, database scalability and availability would require solutions such as:

* Replication
* Failover
* Read replicas
* Managed database services
* Automated backups

---

# 14. Testing

Testing was performed at different stages of deployment.

### Application

The application can be built and tested using Maven.

```bash
mvn clean package
```

### Docker

The application and database are started together using:

```bash
docker compose up --build
```

The REST endpoints can then be tested using tools such as:

* Postman
* curl
* Browser for GET endpoints

### Kubernetes

Deployment health can be checked using:

```bash
kubectl get pods
kubectl get services
kubectl get pvc
```

Application logs:

```bash
kubectl logs deployment/country-info
```

MySQL logs:

```bash
kubectl logs deployment/mysql
```

---

# 15. Troubleshooting Approach

Troubleshooting follows a layered approach:

```text
Pod
 ↓
Container Logs
 ↓
Configuration
 ↓
Service
 ↓
Database
 ↓
Persistent Storage
```

### Pod failure

```bash
kubectl get pods
kubectl describe pod <pod-name>
kubectl logs <pod-name>
```

### Database connectivity

Check:

```bash
kubectl get pods
kubectl get svc
kubectl get endpoints
```

Verify that the application uses:

```text
DB_HOST=mysql
```

rather than:

```text
DB_HOST=localhost
```

### Storage problems

```bash
kubectl get pvc
kubectl describe pvc <pvc-name>
```

---

# 16. Key Design Decisions and Trade-offs

### Layered architecture

Separates responsibilities between API, business logic, and persistence.

**Trade-off:** Introduces more classes and structure than putting all logic in the controller, but improves maintainability.

### REST

Provides a simple and standard integration interface.

**Trade-off:** Synchronous communication means the client waits for the response. Asynchronous messaging could be considered for long-running or event-driven processes.

### MySQL

Provides structured relational persistence and strong Spring Boot integration.

**Trade-off:** It introduces stateful infrastructure that requires backup, availability, and scaling considerations.

### Kubernetes

Provides deployment management, service discovery, and horizontal scaling.

**Trade-off:** Kubernetes adds operational complexity compared with running the application directly with Docker Compose.

---

# 17. Production Improvements

The current implementation provides the core functionality required for the case study. For production, the following areas would be prioritized.

## Security

Security would be the first priority.

Potential improvements include:

* API authentication and authorization
* HTTPS/TLS
* Production-grade secrets management
* Kubernetes RBAC
* NetworkPolicies
* Container vulnerability scanning
* Running containers with minimal privileges

## Reliability

* Readiness and liveness probes
* Resource requests and limits
* Database backups
* Database high availability
* Failure and recovery strategies

## Observability

* Centralized logging
* Application metrics
* Health monitoring
* Distributed tracing where required
* Alerting

## CI/CD

A production pipeline could automate:

```text
GitHub
   ↓
Build
   ↓
Test
   ↓
Security Scan
   ↓
Docker Image
   ↓
Container Registry
   ↓
Kubernetes Deployment
```

---

# 18. Repository Structure

```text
country-info/
│
├── src/
│   └── main/
│       └── java/
│
├── k8s/
│   ├── configmap.yaml
│   ├── secret.yaml
│   ├── pvc.yaml
│   ├── mysql-deployment.yaml
│   ├── mysql-service.yaml
│   ├── deployment.yaml
│   └── service.yaml
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
└── TECHNICAL_SPEC.md
```
