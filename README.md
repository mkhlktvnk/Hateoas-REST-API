# Blog-API-Project ✏️

---

# Content
1. [Technologies](#1-technologies-)
2. [How to start the project?](#2-how-to-start-the-project-)
3. [API description](#3-api-description-)
4. [Database structure](#4-database-structure)

---

# 1. Technologies ⚙️

| Category             | Technology Name   |
|----------------------|-------------------|
| Programming Language | Java 17           |
| Project builder      | Gradle 7.5.1      |
| Backend Framework    | Spring Boot 3.0.1 |
| Testing              | JUnit5, Mockito   |
| Database             | PostgreSQL 14.1   |
| Caching              | Redis             |
| Containerization     | Docker            |
| API Documentation    | Swagger           |

---

# 2. How to start the project? 🚀

### 1. clone this repository

### 2. go to project folder

### 3. Call this command:
    docker-compose up

---

# 3. API description 📝

---

## 3.1 Publishers

---

### All API operations:

| Operation                   | URL                     | Method | Request params | Consumes         | Produces         | Response Status                                                 |
|-----------------------------|-------------------------|--------|----------------|------------------|------------------|-----------------------------------------------------------------|
| Get publishers in page view | /api/v0/publishers      | GET    | page, size     | -                | Application/JSON | 200, 204 if response is empty                                   |
| Get one publisher           | /api/v0/publishers/{id} | GET    | -              | -                | Application/JSON | 200, 404 if publisher not found                                 |
| Add new publisher           | /api/v0/publishers      | POST   | -              | Application/JSON | Application/JSON | 201, 400 if request data is invalid                             |
| Update publisher            | /api/v0/publishers/{id} | PUT    | -              | Application/JSON | Application/JSON | 204, 400 if request data is invalid, 404 if publisher not found |             
| Delete publisher            | /api/v0/publishers/{id} | DELETE | -              | -                | Application/JSON | 204, 404 if publisher not found                                 | 

### Request Model:

| Field    | Type   | Description                 | Required | Not null |
|----------|--------|-----------------------------|----------|----------|
| id       | Long   | Publisher's id              | True     | True     |
| username | String | Unique publisher's username | True     | True     |
| email    | String | Unique publisher's email    | True     | True     |

---

## 3.1.1 Get publishers in page view
This url returns the list of publishers with pagination by parameters "page" and "size". If publishers were returned, response status is 200 OK, if not, then status is 204 NO CONTENT. 
### 🌐 Request URL:
    GET http://localhost:8080/api/v0/publishers?page=2&size=3
### ✅ Response Body:
```` json
    {
        "_embedded": {
            "publishers": [
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/publishers/7"
                        },
                        "articles": {
                            "href": "http://localhost:8080/api/v0/users/7/articles"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/users/7/reviews"
                        }
                    },
                    "id": 7,
                    "username": "example-username-7",
                    "email": "example.email7@mail.com"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/publishers/8"
                        },
                        "articles": {
                            "href": "http://localhost:8080/api/v0/users/8/articles"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/users/8/reviews"
                        }
                    },
                    "id": 8,
                    "username": "example-username-8",
                    "email": "example.email8@mail.com"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/publishers/9"
                        },
                        "articles": {
                            "href": "http://localhost:8080/api/v0/users/9/articles"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/users/9/reviews"
                        }
                    },
                    "id": 9,
                    "username": "example-username-9",
                    "email": "example.email9@mail.com"
                }
            ]
        },
        "_links": {
            "first": {
                "href": "http://localhost:8080/api/v0/publishers?page=0&size=3"
            },
            "prev": {
                "href": "http://localhost:8080/api/v0/publishers?page=1&size=3"
            },
            "self": {
                "href": "http://localhost:8080/api/v0/publishers?page=2&size=3"
            },
            "next": {
                "href": "http://localhost:8080/api/v0/publishers?page=3&size=3"
            },
            "last": {
                "href": "http://localhost:8080/api/v0/publishers?page=3&size=3"
            }
        },
        "page": {
            "size": 3,
            "totalElements": 12,
            "totalPages": 4,
            "number": 2
        }
    }
````
### ✅ Response status:
    200 OK / 204 NO CONTENT

---

## 3.1.2 Get publisher by id
This url return publisher by "id" parameter. If publisher was returned, then status is 200 OK. If not, then status will be 404 and message will be returned. 
### 🌐 Request URL:
    GET http://localhost:8080/api/v0/publishers/9
### ✅ Response body
```` json
    {
        "_links": {
            "self": {
                "href": "http://localhost:8080/api/v0/publishers/9"
            },
            "articles": {
                "href": "http://localhost:8080/api/v0/users/9/articles"
            },
            "reviews": {
                "href": "http://localhost:8080/api/v0/users/9/reviews"
            }
        },
        "id": 9,
        "username": "example-username-9",
        "email": "example.email9@mail.com"
    }
````
### ⛔️ Response body if publisher was not found:
```` json
    {
        "message": "Publisher was not found!",
        "status": 404
    }
````

---

## 3.1.3 Add a new publisher
### 🌐 Request URL:
    POST http://localhost:8080/api/v0/publishers
### Request body:
```` json
    {
        "id": 0,
        "username": "example-username"
        "email": "example-email@email.com"
    }
````
### ✅ Response body:
```` json
    {
        "_links": {
            "self": {
                "href": "http://localhost:8080/api/v0/publishers/1"
            },
            "articles": {
                "href": "http://localhost:8080/api/v0/users/1/articles"
            },
            "reviews": {
                "href": "http://localhost:8080/api/v0/users/1/reviews"
            }
        },
        "id": 1,
        "username": "example-username",
        "email": "example-email@email.com"
    }
````
### ✅ Response status: 
    201 CREATED
### ⛔️ Response body if publisher's username is not unique:
```` json
    {
        "message": "Publisher with this username already exists!",
        "status": 400
    }
````
### ⛔️ Response body if publisher's email is not unique:
```` json
    {
        "message": "Publisher with this email already exists!",
        "status": 400
    } 
````

---

## 3.1.4 Update publisher (Full update)
### 🌐 Request URL:
    PUT http://localhost:8080/api/v0/publishers/11
### Request Body:
```` json
    {
        "id": 11,
        "username": "new-example-username"
        "email": "new-example-email@email.com"
    }
````
### ✅ Response status:
    204 NO CONTENT
### ⛔️ Response body if publisher was not found:
```` json
    {
        "message": "Publisher was not found!",
        "status": "404"
    }
````
---

## 3.1.5 Delete publisher 
### 🌐 Request URL:
    DELETE http://localhost:8080/api/v0/publishers/11
### ✅ Response status:
    204 NO CONTENT
### ⛔️ Response body if publisher was not found:
```` json
    {
        "message": "Publisher was not found!",
        "status": "404"
    }
````
---

## 3.2 Articles

---

### All API operations:
| Operation                             | URL                                      | Method | Request params | Consumes         | Produces         | Response Status                                               |
|---------------------------------------|------------------------------------------|--------|----------------|------------------|------------------|---------------------------------------------------------------|
| Get articles in page view             | /api/v0/articles                         | GET    | page, size     | -                | Application/JSON | 200, 204 if response is empty                                 |
| Get articles of user in page view     | /api/v0/users/{userId}/articles          | GET    | page, size     | -                | Application/JSON | 200, 204 if response is empty                                 |
| Get articles of category in page view | /api/v0/categories/{categoryId}/articles | GET    | page, size     | -                | Application/JSON | 200, 204 if response is empty                                 |                                 
| Get one article                       | /api/v0/articles/{id}                    | GET    | -              | -                | Application/JSON | 200, 404 if article not found                                 |
| Add new article                       | /api/v0/articles                         | POST   | -              | Application/JSON | Application/JSON | 201, 400 if request data is invalid                           |
| Update article                        | /api/v0/articles/{id}                    | PUT    | -              | Application/JSON | Application/JSON | 204, 400 if request data is invalid, 404 if article not found |             
| Delete article                        | /api/v0/articles/{id}                    | DELETE | -              | -                | Application/JSON | 204, 404 if publisher not found                               | 
### Request Model:
| Field       | Type    | Description         | Required | Not null |
|-------------|---------|---------------------|----------|----------|
| id          | Long    | Article id          | True     | True     |
| topic       | String  | Article topic       | True     | True     |
| description | String  | Article description | True     | True     |
| content     | String  | Article content     | True     | True     |

---

## 3.2.1 Get articles in page view
### 🌐 Request URL:
    GET http://localhost:8080/api/v0/articles?page=2&size=3
### ✅ Response Body:
``` json
    {
        "_embedded": {
            "publishers": [
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/7"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/7"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/7"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/7/reviews"
                        }
                    },
                    "id": 7,
                    "topic": "some-example-topic-7",
                    "description": "some-example-description-7",
                    "content": "some-example-content-7"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/8"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/8"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/8"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/8/reviews"
                        }
                    },
                    "id": 9,
                    "topic": "some-example-topic-8",
                    "description": "some-example-description-8",
                    "content": "some-example-content-8"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/9"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/9"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/9"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/9/reviews"
                        }
                    },
                    "id": 9,
                    "topic": "some-example-topic-9",
                    "description": "some-example-description-9",
                    "content": "some-example-content-9"
                },
            ]
        },
        "_links": {
            "first": {
                "href": "http://localhost:8080/api/v0/articles?page=0&size=3"
            },
            "prev": {
                "href": "http://localhost:8080/api/v0/articles?page=1&size=3"
            },
            "self": {
                "href": "http://localhost:8080/api/v0/articles?page=2&size=3"
            },
            "next": {
                "href": "http://localhost:8080/api/v0/articles?page=3&size=3"
            },
            "last": {
                "href": "http://localhost:8080/api/v0/articles?page=3&size=3"
            }
        },
        "page": {
            "size": 3,
            "totalElements": 12,
            "totalPages": 4,
            "number": 2
        }
    }
```
### ✅ Response status:
    200 OK / 204 NO CONTENT

---

## 3.2.2 Get articles of user in page view
### 🌐 Request URL:
    GET http://localhost:8080/api/v0/users/7/articles?page=2&size=3
### ✅ Response Body:
``` json
    {
        "_embedded": {
            "publishers": [
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/7"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/7"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/7"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/7/reviews"
                        }
                    },
                    "id": 7,
                    "topic": "some-example-topic-7",
                    "description": "some-example-description-7",
                    "content": "some-example-content-7"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/8"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/8"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/8"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/8/reviews"
                        }
                    },
                    "id": 9,
                    "topic": "some-example-topic-8",
                    "description": "some-example-description-8",
                    "content": "some-example-content-8"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/9"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/9"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/9"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/9/reviews"
                        }
                    },
                    "id": 9,
                    "topic": "some-example-topic-9",
                    "description": "some-example-description-9",
                    "content": "some-example-content-9"
                },
            ]
        },
        "_links": {
            "first": {
                "href": "http://localhost:8080/api/v0/articles?page=0&size=3"
            },
            "prev": {
                "href": "http://localhost:8080/api/v0/articles?page=1&size=3"
            },
            "self": {
                "href": "http://localhost:8080/api/v0/articles?page=2&size=3"
            },
            "next": {
                "href": "http://localhost:8080/api/v0/articles?page=3&size=3"
            },
            "last": {
                "href": "http://localhost:8080/api/v0/articles?page=3&size=3"
            }
        },
        "page": {
            "size": 3,
            "totalElements": 12,
            "totalPages": 4,
            "number": 2
        }
    }
```
### ✅ Response status:
    200 OK / 204 NO CONTENT

---

## 3.2.3 Get articles of category in page view
### 🌐 Request URL:
    GET http://localhost:8080/api/v0/categories/7/articles
### ✅ Response Body:
``` json
    {
        "_embedded": {
            "publishers": [
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/7"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/7"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/7"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/7/reviews"
                        }
                    },
                    "id": 7,
                    "topic": "some-example-topic-7",
                    "description": "some-example-description-7",
                    "content": "some-example-content-7"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/8"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/8"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/8"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/8/reviews"
                        }
                    },
                    "id": 9,
                    "topic": "some-example-topic-8",
                    "description": "some-example-description-8",
                    "content": "some-example-content-8"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/9"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/9"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/9"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/9/reviews"
                        }
                    },
                    "id": 9,
                    "topic": "some-example-topic-9",
                    "description": "some-example-description-9",
                    "content": "some-example-content-9"
                },
            ]
        },
        "_links": {
            "first": {
                "href": "http://localhost:8080/api/v0/articles?page=0&size=3"
            },
            "prev": {
                "href": "http://localhost:8080/api/v0/articles?page=1&size=3"
            },
            "self": {
                "href": "http://localhost:8080/api/v0/articles?page=2&size=3"
            },
            "next": {
                "href": "http://localhost:8080/api/v0/articles?page=3&size=3"
            },
            "last": {
                "href": "http://localhost:8080/api/v0/articles?page=3&size=3"
            }
        },
        "page": {
            "size": 3,
            "totalElements": 12,
            "totalPages": 4,
            "number": 2
        }
    }
```
### ✅ Response status:
    200 OK / 204 NO CONTENT

---

## 3.2.4 Get article by id
### 🌐 Request URL:
    POST http://localhost:8080/api/v0/articles/9
### ✅ Response Body:
```` json
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/9"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/9"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/9"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/9/reviews"
                        }
                    },
                    "id": 9,
                    "topic": "some-example-topic-9",
                    "description": "some-example-description-9",
                    "content": "some-example-content-9"
                }
````
### ✅ Response status:
    200 OK
### ⛔️ Response body if article was not found:
```` json
    {
        "message": "Article was not found!",
        "status": "404"
    }
````

## 3.2.4 Add new article
### 🌐 Request URL:
    POST http://localhost:8080/api/v0/articles
### Request body:
```` json
{
    "id": 0,
    "topic": "some-example-topic",
    "description": "some-example-descriptiom",
    "content": "some-example-content"
}
````
### ✅ Response Body:
```` json
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/10"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/1"
                        },
                        "category": {
                            "href": "http://localhost:8080/api/v0/categories/1"
                        },
                        "reviews": {
                            "href": "http://localhost:8080/api/v0/articles/10/reviews"
                        }
                    },
                    "id": 10,
                    "topic": "some-example-topic",
                    "description": "some-example-description",
                    "content": "some-example-content"
                }
````
### ✅ Response Status:
    201 CREATED
---

## 3.2.5 Update existing article
### 🌐 Request URL:
    PUT http://localhost:8080/api/v0/articles
### Request body:
```` json
{
    "id": "1",
    "topic": "some-new-topic",
    "description": "some-new-description",
    "content": "some-new-content"
}
````
### ✅ Response status:
    204 NO CONTENT
### ⛔️ Response body if article was not found:
```` json
    {
        "message": "Article was not found!",
        "status": "404"
    }
````

## 3.2.6 Delete existing article
### 🌐 Request URL:
    DELETE http://localhost:8080/api/v0/articles
### ✅ Response status:
    204 NO CONTENT
### ⛔️ Response body if article was not found:
```` json
    {
        "message": "Article was not found!",
        "status": "404"
    }
````
---

## 3.3 Categories

---

### All API operations:
| Operation                     | URL                     | Method | Request params | Consumes         | Produces         | Response Status                                                    |
|-------------------------------|-------------------------|--------|----------------|------------------|------------------|--------------------------------------------------------------------|
| Get categories in a page view | /api/v0/categories      | GET    | page, size     | -                | Application/JSON | 200, 204 if response is empty                                      |
| Get category by id            | /api/v0/categories/{id} | GET    | -              | -                | Application/JSON | 200, 404 if category was not found                                 |
| Add new category              | /api/v0/categories      | POST   | -              | Application/JSON | Application/JSON | 200, 400 if request data is invalid                                |
| Update existing category      | /api/v0/categories/{id} | PUT    | page, size     | Application/JSON | -                | 200, 400 if request data is invalid, 404 if category was not found |
| Delete existing category      | /api/v0/categories/{id} | DELETE | page, size     | Application/JSON | -                | 200, 404 if category was not found                                 |
### Request Model:
| Field       | Type   | Description          | Required | Not null |
|-------------|--------|----------------------|----------|----------|
| id          | Long   | Category id          | True     | True     |
| name        | String | Unique category name | True     | True     |
| description | String | Category description | True     | True     |

---

## 3.3.1 Get categories in page view
### 🌐 Request URL:
    GET http://localhost:8080/api/v0/categories?page=2&size=3
### ✅ Response Body:
``` json
    {
        "_embedded": {
            "categories": [
               {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/categories/7"
                        },
                        "articles": {
                            "href": "http://localhost:8080/api/v0/categories/7/articles"
                        }
                    }
                    "id": 7,
                    "name": "some-category-name-7",
                    "description": some-category-description"
               },
               {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/categories/8"
                        },
                        "articles": {
                            "href": "http://localhost:8080/api/v0/categories/8/articles"
                        }
                    }
                    "id": 8,
                    "name": "some-category-name-8",
                    "description": some-category-description"
               },
               {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/categories/9"
                        },
                        "articles": {
                            "href": "http://localhost:8080/api/v0/categories/9/articles"
                        }
                    }
                    "id": 9,
                    "name": "some-category-name-9",
                    "description": some-category-description"
               }
            ]
        },
        "_links": {
            "first": {
                "href": "http://localhost:8080/api/v0/categories?page=0&size=3"
            },
            "prev": {
                "href": "http://localhost:8080/api/v0/categories?page=1&size=3"
            },
            "self": {
                "href": "http://localhost:8080/api/v0/categories?page=2&size=3"
            },
            "next": {
                "href": "http://localhost:8080/api/v0/categories?page=3&size=3"
            },
            "last": {
                "href": "http://localhost:8080/api/v0/categores?page=3&size=3"
            }
        },
        "page": {
            "size": 3,
            "totalElements": 12,
            "totalPages": 4,
            "number": 2
        }
    }
```
### ✅ Response status:
    200 OK / 204 NO CONTENT

---

## 3.3.2 Get category by id
### 🌐 Request URL:
    POST http://localhost:8080/api/v0/categories/9
### ✅ Response Body:
```` json
               {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/categories/9"
                        },
                        "articles": {
                            "href": "http://localhost:8080/api/v0/categories/9/articles"
                        }
                    }
                    "id": 9,
                    "name": "some-category-name-9",
                    "description": some-category-description"
               }
````
### ✅ Response status:
    200 OK
### ⛔️ Response body if category was not found:
```` json
    {
        "message": "Category was not found!",
        "status": "404"
    }
````

---
## 3.3.3 Add new category
### 🌐 Request URL:
    POST http://localhost:8080/api/v0/categories
### Request body:
```` json
    {
        "id": 0,
        "name": "some-name",
        "description": "some-description"
    }
````
### ✅ Response Body:
```` json
               {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/categories/10"
                        },
                        "articles": {
                            "href": "http://localhost:8080/api/v0/categories/10/articles"
                        }
                    }
                    "id": 10,
                    "name": "some-name",
                    "description": some-description"
               }
````
### ✅ Response status:
    201 CREATED
---
## 3.3.4 Update existing category
### 🌐 Request URL:
    PUT http://localhost:8080/api/v0/categories/{id}
### Request body:
```` json
    {
        "id": 10,
        "name": "some-new-name",
        "description": "some-new-description"
    }
````
### ✅ Response status:
    204 NO CONTENT
### ⛔️ Response body if category was not found:
```` json
    {
        "message": "Category was not found!",
        "status": "404"
    }
````
## 3.3.5 Delete existing category
### 🌐 Request URL:
    DELETE http://localhost:8080/api/v0/categories/{id}
### ✅ Response status:
    204 NO CONTENT
### ⛔️ Response body if category was not found:
```` json
    {
        "message": "Category was not found!",
        "status": "404"
    }
````
---

## 3.4 Reviews

### All API operations:
| Operation                | URL                                  | Method | Request params         | Consumes         | Produces         | Response Status                                                  |
|--------------------------|--------------------------------------|--------|------------------------|------------------|------------------|------------------------------------------------------------------|
| Get reviews made by user | /api/v0/users/{userId}/reviews       | GET    | page, size             | -                | Application/JSON | 200, 204 if response is empty                                    |
| Get reviews of article   | /api/v0/articles/{articleId}/reviews | GET    | page, size             | -                | Application/JSON | 200, 204 if response is empty                                    |
| Get review by id         | /api/v0/reviews/{id}                 | GET    | -                      | -                | Application/JSON | 200, 404 if review was not found                                 |
| Add new review           | /api/v0/reviews                      | POST   | articleId, publisherId | Application/JSON | Application/JSON | 200                                                              |
| Update existing review   | /api/v0/reviews/{id}                 | PUT    | -                      | Application/JSON | -                | 204, 400 if request body is invalid, 404 if review was not found |
| Delete existing review   | /api/v0/reviews/{id}                 | DELETE | -                      | Application/JSON | -                | 204, 404 if review was not found                                 |

### Request Model:
| Field   | Type    | Description       | Required | Not null |
|---------|---------|-------------------|----------|----------|
| id      | Long    | Review id         | True     | True     |
| mark    | Integer | Mark for article  | True     | True     |
| content | String  | Content of review | True     | True     |

---

## 3.4.1 Get reviews of article
### 🌐 Request URL:
    GET http://localhost:8080/api/v0/articles/1/reviews
### ✅ Response Body:
``` json
    {
        "_embedded": {
            "reviews": [
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/1/reviews/1"
                        },
                        "article": {
                            "href": "http://localhost:8080/api/v0/articles/1"
                        } ,
                        "publisher" {
                            "href": "http://localhost:8080/api/v0/publishers/1"
                        }
                    },
                    "id": 1,
                    "mark": 5,
                    "content": "some-content"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/1/reviews/2"
                        },
                        "article": {
                            "href": "http://localhost:8080/api/v0/articles/1"
                        } ,
                        "publisher" {
                            "href": "http://localhost:8080/api/v0/publishers/2"
                        }
                    },
                    "id": 2,
                    "mark": 3,
                    "content": "some-content"
                },
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/1/reviews/3"
                        },
                        "article": {
                            "href": "http://localhost:8080/api/v0/articles/1"
                        } ,
                        "publisher" {
                            "href": "http://localhost:8080/api/v0/publishers/3"
                        }
                    },
                    "id": 3,
                    "mark": 1,
                    "content": "some-content"
                }
            ]
        },
        "_links": {
            "first": {
                "href": "http://localhost:8080/api/v0/articles/1/reviews?page=0&size=3"
            },
            "prev": {
                "href": "http://localhost:8080/api/v0/articles/1/reviews?page=1&size=3"
            },
            "self": {
                "href": "http://localhost:8080/api/v0/articles/1/reviews?page=2&size=3"
            },
            "next": {
                "href": "http://localhost:8080/api/v0/articles/1/reviews?page=3&size=3"
            },
            "last": {
                "href": "http://localhost:8080/api/v0/articles/1/reviews?page=3&size=3"
            }
        },
        "page": {
            "size": 3,
            "totalElements": 12,
            "totalPages": 4,
            "number": 2
        }
    }
```
### ✅ Response status:
    200 OK / 204 NO CONTENT

---

## 3.4.2 Get reviews by id
### 🌐 Request URL:
    GET http://localhost:8080/api/v0/reviews/1
### ✅ Response Body:
````json
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/1/reviews/3"
                        },
                        "article": {
                            "href": "http://localhost:8080/api/v0/articles/1"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/3"
                        }
                    },
                    "id": 3,
                    "mark": 1,
                    "content": "some-content"
                }
````
### ✅ Response status:
    200 OK / 204 NO CONTENT
### ⛔️ Response body if review was not found:
```` json
    {
        "message": "Review was not found!",
        "status": "404"
    }
````

---

## 3.4.3 Add new review
### 🌐 Request URL:
    POST http://localhost:8080/api/v0/reviews
### Request body:
```` json
    {
        "id": 0,
        "mark": 4,
        "content": "some-content"
    }
````
### ✅ Response Body:
````json
                {
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/v0/articles/1/reviews/4"
                        },
                        "article": {
                            "href": "http://localhost:8080/api/v0/articles/1"
                        },
                        "publisher": {
                            "href": "http://localhost:8080/api/v0/publishers/3"
                        }
                    },
                    "id": 4,
                    "mark": 4,
                    "content": "some-content"
                }
````

### ✅ Response status:
    200 OK

## 3.4.4 Update existing review
### 🌐 Request URL:
    POST http://localhost:8080/api/v0/reviews/4
### Request body:
```` json
    {
        "id": 4,
        "mark": 5,
        "content": "some-new-content"
    }
````

### ✅ Response status:
    204 NO CONTENT

### ⛔️ Response body if review was not found:
```` json
    {
        "message": "Review was not found!",
        "status": "404"
    }
````

---

# 4. Database structure

```` postgresql
    
    CREATE TABLE ARTICLES (
       ID BIGINT NOT NULL,
        CONTENT VARCHAR(255) NOT NULL,
        DESCRIPTION VARCHAR(255) NOT NULL,
        TOPIC VARCHAR(255) NOT NULL,
        CATEGORY_ID BIGINT NOT NULL,
        PUBLISHER_ID BIGINT NOT NULL,
        PRIMARY KEY (ID)
    )
    
    CREATE TABLE CATEGORIES (
       ID BIGINT NOT NULL,
        DESCRIPTION VARCHAR(255) NOT NULL,
        NAME VARCHAR(255) NOT NULL,
        PRIMARY KEY (ID)
    )
    
    CREATE TABLE PUBLISHERS (
       ID BIGINT NOT NULL,
        EMAIL VARCHAR(255) NOT NULL,
        USERNAME VARCHAR(255) NOT NULL,
        PRIMARY KEY (ID)
    )
    
    CREATE TABLE REVIEWS (
       ID BIGINT NOT NULL,
        CONTENT VARCHAR(255) NOT NULL,
        MARK INTEGER NOT NULL,
        ARTICLE_ID BIGINT NOT NULL,
        PUBLISHER_ID BIGINT NOT NULL,
        PRIMARY KEY (ID)
    )
    
    ALTER TABLE IF EXISTS PUBLISHERS 
       ADD CONSTRAINT UK_USERNAME UNIQUE (username)
    
    ALERT TABLE IF EXISTS PUBLISHERS
        ADD CONSTRAINT UK_EMAIL UNIQUE (email)
       
    ALTER TABLE IF EXISTS ARTICLES 
       ADD CONSTRAINT FK_CATEGORY_ID 
       FOREIGN KEY (CATEGORY_ID) 
       REFERENCES CATEGORIES
    
    ALTER TABLE IF EXISTS ARTICLES 
       ADD CONSTRAINT FK_PUBLISHER_ID 
       FOREIGN KEY (PUBLISHER_ID) 
       REFERENCES PUBLISHERS
    
    ALTER TABLE IF EXISTS REVIEWS 
       ADD CONSTRAINT FK_ARTICLE_ID 
       FOREIGN KEY (ARTICLE_ID) 
       REFERENCES ARTICLES   
    
    ALTER TABLE IF EXISTS REVIEWS 
       ADD CONSTRAINT FK_PUBLISHER_ID
       FOREIGN KEY (PUBLISHER_ID) 
       REFERENCES PUBLISHERS
       
````

---