# 📝 Blogging App REST API

A full-featured blogging platform REST API built with **Spring Boot 4**, **Spring Security**, and **JWT Authentication**. The API supports user management, blog posts, categories, comments, and cloud image uploads via Cloudinary.

---

## 🚀 Features

- **User Management** — Register, login, update, and delete users
- **JWT Authentication** — Secure token-based authentication
- **Role Based Authorization** — `ROLE_ADMIN` and `ROLE_USER` access control
- **Blog Posts** — Full CRUD with pagination, sorting, and filtering
- **Categories** — Organize posts by category (Admin only)
- **Comments** — Create, update, and delete comments on posts
- **Image Upload** — Cloud image storage via Cloudinary
- **Search** — Search posts by keyword
- **API Documentation** — Interactive Swagger UI via SpringDoc OpenAPI

---

## 🛠️ Tech Stack

| Technology | Description |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.0.3 | Application framework |
| Spring Security 7 | Authentication & Authorization |
| JWT (jjwt 0.12.3) | Token based authentication |
| Spring Data JPA | Database ORM |
| Hibernate 7 | JPA implementation |
| MySQL | Relational database |
| MapStruct | DTO mapping |
| Lombok | Boilerplate reduction |
| Cloudinary | Cloud image storage |
| SpringDoc OpenAPI 3.0.1 | API documentation |
| Maven | Build tool |

---

## 📁 Project Structure

```
src/main/java/com/webApp/bloggingapp/
├── config/               # Security, Swagger, App constants
│   ├── AppConstants.java
│   ├── CloudinaryConfig.java
│   ├── PostSecurity.java
│   ├── SecurityConfig.java
│   ├── SwaggerConfig.java
│   └── JwtAuthenticationFilter.java (in security/)
├── controllers/          # REST controllers
│   ├── AuthController.java
│   ├── CategoryController.java
│   ├── CommentController.java
│   ├── PostController.java
│   └── UserController.java
├── entities/             # JPA entities
│   ├── Category.java
│   ├── Comment.java
│   ├── Post.java
│   ├── Role.java
│   └── User.java
├── exceptions/           # Custom exceptions
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── DuplicateResourceException.java
├── mappers/              # MapStruct mappers
│   ├── CategoryMapper.java
│   ├── PostMapper.java
│   └── UserMapper.java
├── payloads/             # DTOs and response objects
│   ├── ApiResponse.java
│   ├── CategoryDto.java
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── PaginationResponse.java
│   ├── PostDto.java
│   └── UserDto.java
├── repositories/         # Spring Data JPA repositories
│   ├── CategoryRepo.java
│   ├── CommentRepo.java
│   ├── PostRepo.java
│   └── UserRepo.java
├── security/             # JWT filter
│   └── JwtAuthenticationFilter.java
├── seeders/              # Data seeders
│   └── DataSeeder.java
├── services/             # Service interfaces and implementations
│   ├── impl/
│   │   ├── CategoryServiceImpl.java
│   │   ├── CustomUserDetailsService.java
│   │   ├── FileServiceImpl.java
│   │   ├── PostServiceImpl.java
│   │   └── UserServiceImpl.java
│   ├── CategoryService.java
│   ├── FileService.java
│   ├── PostService.java
│   └── UserService.java
└── utils/                # Utility classes
    ├── JwtUtils.java
    └── PaginationUtils.java
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- MySQL (local or cloud)
- Cloudinary account (free tier)

### Installation

**1. Clone the repository:**
```bash
git clone https://github.com/yourusername/blogging-app.git
cd blogging-app
```

**2. Configure `application.properties`:**
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/bloggingapp
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT
jwt.secret=your_base64_encoded_secret_key
jwt.expiry=36000000

# Cloudinary
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret
```

**3. Run the application:**
```bash
mvn spring-boot:run
```

**4. Default admin credentials (auto-created on first run):**
```
Email:    admin@gmail.com
Password: admin123
```

---

## 🔐 Authentication

This API uses **JWT Bearer Token** authentication.

**Step 1 — Register:**
```http
POST /api/auth/register
Content-Type: application/json

{
    "name": "John Doe",
    "email": "johndoe@gmail.com",
    "password": "john123",
    "about": "Software developer"
}
```

**Step 2 — Login:**
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "johndoe@gmail.com",
    "password": "john123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzM4NCJ9...",
    "email": "johndoe@gmail.com",
    "name": "John Doe"
}
```

**Step 3 — Use token in requests:**
```http
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
```

---

## 📡 API Endpoints

### 🔑 Authentication
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login and get JWT token |

### 👤 Users
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/users` | Admin | Get all users |
| GET | `/api/users/{id}` | Admin | Get user by ID |
| PUT | `/api/users/{id}` | Admin | Update user |
| DELETE | `/api/users/{id}` | Admin | Delete user |

### 📂 Categories
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/categories` | Public | Get all categories |
| GET | `/api/categories/{id}` | Public | Get category by ID |
| POST | `/api/categories` | Admin | Create category |
| PUT | `/api/categories/{id}` | Admin | Update category |
| DELETE | `/api/categories/{id}` | Admin | Delete category + posts |

### 📝 Posts
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/posts` | Public | Get all posts (paginated) |
| GET | `/api/posts/{id}` | Public | Get post by ID |
| GET | `/api/user/{userId}/posts` | Public | Get posts by user |
| GET | `/api/category/{categoryId}/posts` | Public | Get posts by category |
| GET | `/api/posts/search/{keyword}` | Public | Search posts by keyword |
| POST | `/api/user/{userId}/category/{categoryId}/posts` | User/Admin | Create post |
| PUT | `/api/posts/{id}` | Owner/Admin | Update post |
| DELETE | `/api/posts/{id}` | Owner/Admin | Delete post |
| POST | `/api/posts/{id}/image/upload` | User/Admin | Upload post image |

### 💬 Comments
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/posts/{postId}/comments` | User/Admin | Create comment |
| PUT | `/api/comments/{id}` | Owner/Admin | Update comment |
| DELETE | `/api/comments/{id}` | Owner/Admin | Delete comment |

---

## 📄 Pagination & Sorting

All list endpoints support pagination and sorting via query parameters:

```http
GET /api/posts?pageNo=0&pageSize=5&sortBy=postId&sortDir=asc
```

| Parameter | Default | Description |
|---|---|---|
| `pageNo` | `0` | Page number (0-indexed) |
| `pageSize` | `5` | Number of items per page |
| `sortBy` | `postId` | Field to sort by |
| `sortDir` | `asc` | Sort direction (`asc` or `desc`) |

**Pagination Response:**
```json
{
    "posts": [...],
    "pageNo": 0,
    "pageSize": 5,
    "totalElements": 20,
    "totalPages": 4,
    "lastPage": false
}
```

---

## 🖼️ Image Upload

Images are stored on **Cloudinary**. Upload a post image using:

```http
POST /api/posts/{postId}/image/upload
Authorization: Bearer {token}
Content-Type: multipart/form-data

Key: image
Value: [select image file]
```

The response will include the full Cloudinary URL stored in `imageUrl`.

---

## 🔒 Role Based Access Control

| Role | Capabilities |
|---|---|
| `ROLE_ADMIN` | Full access to all endpoints |
| `ROLE_USER` | Create/update/delete own posts and comments |
| Public | Read posts, categories, register, login |

**Post/Comment ownership rules:**
- Only the **post owner** or an **Admin** can update or delete a post
- Only the **comment owner** or an **Admin** can update or delete a comment

---

## 📚 API Documentation

Interactive Swagger UI is available at:
```
http://localhost:8081/swagger-ui.html
```

To test protected endpoints in Swagger UI:
1. Login via `/api/auth/login`
2. Copy the token from the response
3. Click **Authorize** button in Swagger UI
4. Enter `Bearer your_token_here`
5. Click **Authorize**

---

## 🌐 Environment Profiles

The project supports multiple Spring profiles for easy environment switching:

**`application-local.properties`** — Local MySQL:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bloggingapp
spring.datasource.username=root
spring.datasource.password=your_local_password
```

**`application-cloud.properties`** — Cloud MySQL:
```properties
spring.datasource.url=jdbc:mysql://your-cloud-host:3306/dbname
spring.datasource.username=cloud_username
spring.datasource.password=cloud_password
```

Switch profiles in IntelliJ Run Configuration → Active profiles field.

---

## 🗄️ Database Schema

```
users
  └── posts (one-to-many)
          └── comments (one-to-many)

categories
  └── posts (one-to-many)
```

---

## 🚧 Known Issues

- Spring Boot 4 + Hibernate 7 requires `@Transactional` and native queries for delete operations due to bidirectional relationship management changes
- `datetime(6)` not supported on older MySQL versions — use `@Column(columnDefinition = "datetime")` on date fields

---

## 📌 Future Improvements

- [ ] Like/Dislike posts
- [ ] Nested comment replies
- [ ] Email notifications
- [ ] Full frontend (React)
- [ ] Deploy to cloud (Render/Railway)
- [ ] Refresh token support
- [ ] Rate limiting

---

## 📜 License

This project is licensed under the MIT License.
