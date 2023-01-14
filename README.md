# Blog-API-Project
# 1. Technologies
| Category             | Technology Name   |
|----------------------|-------------------|
| Programming Language | Java 17           |
| Backend Framework    | Spring Boot 3.0.1 |
| Project builder      | Gradle 7.5.1      |
| Database             | PostgreSQL 14.1   |
| Containerization     | Docker            |

# 2. How to start the project?
### 1. clone this repository
### 2. go to project folder
### 3. docker-compose up

# 3. API documentation
## 3.1 Publishers
### All API operations:
| Operation                   | URL                     | Method | Response Status |
|-----------------------------|-------------------------|--------|-----------------|
| Get publishers in page view | /api/v0/publishers      | GET    | 200, 204        |
| Get one publisher           | /api/v0/publishers/{id} | GET    | 200, 404        |
| Add new publisher           | /api/v0/publishers      | POST   | 201, 400        |
### Request Model:
| Field    | Type    | Description                 |
|----------|---------|-----------------------------|
| id       | Long    | Publisher's id              |
| username | String  | Unique publisher's username |
| email    | String  | Unique publisher's email    |

## 3.2 Get publishers in page view
### Request URL:
    http://localhost:8080/api/v0/publishers?page=0&size=10
### Response Body:
    

