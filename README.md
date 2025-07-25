# JSON Dataset API – Spring Boot (MySQL)

### A Spring Boot 3.3 application to store arbitrary JSON dataset records in MySQL, with support for:

- ### Inserting JSON data into a named dataset.

- ### Querying records by grouping and/or sorting based on JSON fields.

## 🔧 Tech Stack Used

| Layer                  | Technology / Tool           | Purpose / Reason                            |
| ---------------------- | --------------------------- | ------------------------------------------- |
| **Language**           | Java 17+                    | LTS version compatible with Spring Boot 3   |
| **Framework**          | Spring Boot 3.3             | Rapid REST API development                  |
| **ORM**                | Spring Data JPA + Hibernate | Simplified and declarative DB access        |
| **Database**           | MySQL 8+                    | JSON support, indexing, broad compatibility |
| **JSON Mapping**       | Jackson                     | Seamless JSON serialization/deserialization |
| **Build Tool**         | Maven                       | Dependency management and build automation  |
| **Testing** (optional) | JUnit 5 + Spring Test       | For unit/integration testing                |
| **IDE**                | IntelliJ IDEA / Eclipse     | Java development environment                |
| **API Testing**        | Postman / curl              | API debugging and testing                   |



##  📂 Project Structure
```
groupsortoperators/
├── src/main/java/com/example/groupsortoperators/
│   ├── controller/     --> REST Controllers
│   ├── service/        --> Business logic
│   ├── repo/           --> JPA Repositories
│   └── model/          --> JPA Entity (DatasetEntity)
└── src/main/resources/
└── application.properties
```

## 📆 Database Schema (MySQL)
```
CREATE TABLE dataset_records (
    id           VARCHAR(36) PRIMARY KEY,
    dataset_name VARCHAR(255) NOT NULL,
    data         JSON NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_dataset_name ON dataset_records(dataset_name);
```

## 🔹 API Endpoints

### 1. 📂 Insert Record

### URL:
```
POST /api/dataset/employee_dataset/record
```
### Request Body
```
{
  "id": 1,
  "name": "John Doe",
  "age": 30,
  "department": "Engineering"
}
```

### Response:
```
{
    "dataset": "employee_dataset",
    "message": "Record added successfully",
    "recordId": "1"
}
```

### 2. 🔍 Query with Group-By

### URL:
```
GET /api/dataset/employee_dataset/query?groupBy=department
```
### Response
```
{
    "groupedRecords": {
        "Marketing": [
            {
                "id": 4,
                "name": "Tony Stark",
                "age": 26,
                "department": "Marketing"
            }
        ],
        "Engineering": [
            {
                "id": 1,
                "name": "John Doe",
                "age": 30,
                "department": "Engineering"
            },
            {
                "id": 3,
                "name": "Alice Brown",
                "age": 28,
                "department": "Engineering"
            },
            {
                "id": 2,
                "name": "Jane Smith",
                "age": 25,
                "department": "Engineering"
            }
        ]
    }
}
```

### 3. 🔄 Query with Sort-By Only

### URL:
```
GET /api/dataset/employee_dataset/query?sortBy=age,name:desc
```
### Response
```
{
    "sortedRecords": [
        {
            "id": 2,
            "name": "Jane Smith",
            "age": 25,
            "department": "Engineering"
        },
        {
            "id": 4,
            "name": "Tony Stark",
            "age": 26,
            "department": "Marketing"
        },
        {
            "id": 3,
            "name": "Alice Brown",
            "age": 28,
            "department": "Engineering"
        },
        {
            "id": 1,
            "name": "John Doe",
            "age": 30,
            "department": "Engineering"
        }
    ]
}
```

### 🔄 4. Query with Group By and Sort By
### URL
```
GET api/dataset/employee_dataset/query?groupBy=department&sortBy=age
```

### Response
```
{
    "groupedRecords": {
        "Marketing": [
            {
                "id": 4,
                "name": "Tony Stark",
                "age": 26,
                "department": "Marketing"
            }
        ],
        "Engineering": [
            {
                "id": 2,
                "name": "Jane Smith",
                "age": 25,
                "department": "Engineering"
            },
            {
                "id": 3,
                "name": "Alice Brown",
                "age": 28,
                "department": "Engineering"
            },
            {
                "id": 1,
                "name": "John Doe",
                "age": 30,
                "department": "Engineering"
            }
        ]
    }
}
```

### 🚀 Run Locally

### 1. Clone the Repo

```
git clone https://github.com/shivamsprajapati13/groupsortoperators.git
cd groupsortoperators
```

### 2. Configure MySQL
Ensure MySQL 8+ is running and a database is created:
```
CREATE DATABASE groupsortoperators;
```

### 3. Update application.properties
```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/groupsortoperators
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql: true
```

### 4. Run the App
```
./mvnw spring-boot:run
```
