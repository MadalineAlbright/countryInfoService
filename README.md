# CountryInfoService
A Spring Boot REST API that integrates with a SOAP-based Country Info service to retrieve, store, and expose country and language information.

## Project Goal

The project demonstrates the development and deployment of a simple microservice, including:

* REST API development
* MySQL database integration
* Docker containerization
* Kubernetes deployment
* Persistent database storage
* Application testing and troubleshooting

## Tech Stack

* **Java / Spring Boot**
* **MySQL**
* **Maven**
* **Docker & Docker Compose**
* **Kubernetes**

## Prerequisites

Ensure the following are installed and available:

* Java
* Maven
* Docker
* Docker Compose
* Kubernetes / Minikube
* kubectl

## Run Locally

### 1. Build the application

```bash
mvn clean package
```

### 2. Start with Docker Compose

```bash
docker compose up --build
```

Check the running containers:

```bash
docker compose ps
```

### 3. Stop the application

```bash
docker compose down
```

## API Endpoints

| Method | Endpoint              | Description         |
| ------ | --------------------- | ------------------- |
| GET    | `/api/countries`      | Get all countries   |
| GET    | `/api/countries/{id}` | Get a country by ID |
| POST   | `/api/countries`      | Create a country    |
| PUT    | `/api/countries/{id}` | Update a country    |
| DELETE | `/api/countries/{id}` | Delete a country    |

> Update the endpoints above if the implemented application uses different paths.

## Kubernetes Deployment

Before deploying, replace the placeholder values with the actual database credentials for the environment being used.

stringData:
DB_USERNAME: <your-database-username>
DB_PASSWORD: <your-database-password>

Ensure Kubernetes is running, then apply the resources:

```bash
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/pvc.yaml
kubectl apply -f k8s/mysql-deployment.yaml
kubectl apply -f k8s/mysql-service.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

Check the deployment:

```bash
kubectl get pods
kubectl get svc
kubectl get pvc
```

### Access with Minikube

```bash
minikube service country-info --url
```

Use the returned URL to test the API.

## Troubleshooting

### Application logs

```bash
kubectl logs deployment/country-info
```

### MySQL logs

```bash
kubectl logs deployment/mysql
```

### Inspect a pod

```bash
kubectl describe pod <pod-name>
```

### Check services and endpoints

```bash
kubectl get svc
kubectl get endpoints
```

### Check storage

```bash
kubectl get pvc
```

### Restart the application

```bash
kubectl rollout restart deployment country-info
```

## Kubernetes Architecture

```text
Client
  |
  | REST/HTTP
  v
Country Info Service
  |
  v
Spring Boot Deployment
  |
  v
MySQL Service
  |
  v
MySQL Deployment
  |
  v
Persistent Storage
```

Configuration is managed through a **ConfigMap**, database credentials through a **Secret**, and MySQL data through a **PersistentVolumeClaim**.

## Technical Documentation

For the detailed architecture, API specification, MVC structure, integration approach, design decisions, trade-offs, scalability, security considerations, and future improvements, see:

**[Case Study Submission – Integrations and Microservices Engineer](TechnicalSpecification.md)**

## Repository

**GitHub Repository:**
https://github.com/MadalineAlbright/countryInfoService
