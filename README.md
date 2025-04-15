# AppStore Spring Boot Application

A robust and scalable e-commerce application built with Spring Boot, providing a comprehensive API for managing products, orders, and user accounts.

## 🚀 Features

- User authentication and authorization with JWT
- Product management with categories
- Shopping cart functionality
- Order processing and management
- File upload for product images
- Redis caching for improved performance
- Multi-environment configuration (dev/prod)
- RESTful API design
- Swagger API documentation

## 🛠️ Technologies

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MyBatis
- MySQL
- Redis
- Maven
- Docker
- Swagger/OpenAPI

## 📋 Prerequisites

- JDK 17 or higher
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- Docker (optional)

## 🔧 Installation & Setup

1. Clone the repository
```bash
git clone https://github.com/DvMinhGithub/app-store-spring.git
cd app-store-spring
```

2. Configure the database
```bash
# Create MySQL database
mysql -u root -p
CREATE DATABASE app_store_dev;
```

3. Configure environment variables for production
```bash
export MYSQL_ROOT_PASSWORD=your_password
export APP_JWT_SECRET=your_jwt_secret
export APP_JWT_EXPIRATION=86400000
export APP_JWT_REFRESH_SECRET=your_refresh_secret
export APP_JWT_REFRESH_EXPIRATION=259200000
```

4. Build the project
```bash
mvn clean install
```

5. Run the application
```bash
# Development
mvn spring-boot:run -Dspring.profiles.active=dev

# Production
mvn spring-boot:run -Dspring.profiles.active=prod
```

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/mdv/appstore/
│   │       ├── config/         # Configuration classes
│   │       ├── controller/     # REST controllers
│   │       ├── dto/           # Data Transfer Objects
│   │       ├── entity/        # JPA entities
│   │       ├── mapper/        # MyBatis mappers
│   │       ├── repository/    # JPA repositories
│   │       ├── service/       # Business logic
│   │       └── util/          # Utility classes
│   └── resources/
│       ├── mybatis/          # MyBatis mapper XML files
│       ├── sql/              # SQL scripts
│       └── application.yml   # Configuration files
```

## 🔐 Environment Configuration

The application supports multiple environments:

- **Development** (`application-dev.yml`)
  - Local database
  - Debug logging
  - Development JWT keys

- **Production** (`application-prod.yml`)
  - Production database
  - Environment variables for sensitive data
  - Info level logging
  - Secure JWT configuration

## 📚 API Documentation

API documentation is available through Swagger UI:
- Development: `http://localhost:8080/api/v1/swagger-ui.html`
- Production: `https://your-domain.com/api/v1/swagger-ui.html`

## 🐳 Docker Support

Build and run with Docker:

```bash
# Build the image
docker build -t app-store-spring .

# Run the container
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e MYSQL_ROOT_PASSWORD=your_password \
  app-store-spring
```

## 🧪 Testing

Run tests with Maven:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest
```

## 📝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- DvMinhGithub - Initial work - [YourGithub](https://github.com/DvMinhGithub)

## 🙏 Acknowledgments

- Spring Boot team
- All contributors and supporters
