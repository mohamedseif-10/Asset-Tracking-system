# Asset Management System Documentation

## Project Overview
This is a sophisticated Spring Boot-based Asset Management System designed to streamline the tracking and management of organizational assets. The system implements a robust security model with user authentication and role-based access control, making it suitable for organizations of various sizes.

## File Structure and Components

### 1. Application Core
#### `ManagerApplication.java`
- Main entry point of the Spring Boot application
- Initializes the Spring application context
- Enables auto-configuration
- Bootstrap point for the application

### 2. Models Package (`model/`)
The models package contains the core domain entities of the application.

#### `User.java`
- Core user entity implementing Spring Security's UserDetails
- Handles user authentication and role-based access
- Key features:
  - User authentication management
  - Role-based access control
  - User-asset relationship management
  - Security method implementations
- Fields:
  - id: Unique identifier
  - name: User's full name
  - email: Authentication username
  - password: Encrypted password
  - role: User permissions
  - assets: Associated assets

#### `Asset.java`
- Manages organizational assets
- Key features:
  - Asset lifecycle management
  - Status tracking
  - User assignment
  - History integration
- Fields:
  - id: Unique identifier
  - name: Asset name
  - type: Asset classification
  - status: Current state
  - description: Details
  - addDate: Registration date
  - user: Current owner
  - assetHistory: Transaction history

#### `AssetHistory.java`
- Records asset transactions and changes
- Key features:
  - Complete audit trail
  - Transaction timestamps
  - User action tracking
  - Status change monitoring
- Fields:
  - id: Record identifier
  - user: Acting user
  - asset: Affected asset
  - date: Action timestamp
  - status: Action type

### 3. Security Package (`security/`)
#### `SecurityConfig.java`
- Spring Security configuration
- Key features:
  - Authentication setup
  - URL permission management
  - Password encryption
  - Security filters
  - Login/logout handling
- Components:
  - SecurityFilterChain configuration
  - Authentication provider setup
  - Password encoder configuration
  - Authentication manager

### 4. Service Layer (`service/`)
#### `AuthenticationService.java`
- Handles user authentication processes
- Key features:
  - User registration
  - Password encryption
  - Data validation
  - User creation
- Dependencies:
  - UserRepository
  - PasswordEncoder

#### `CustomUserDetailsService.java`
- Spring Security user service implementation
- Key features:
  - User data loading
  - Security integration
  - Email-based lookup
  - Exception handling
- Methods:
  - loadUserByUsername(): Loads user security details

### 5. Repository Layer (`repository/`)
#### `UserRepository.java`
- JPA repository for user data
- Key features:
  - Database operations
  - Custom queries
  - User lookups
- Methods:
  - findByEmail(): Email-based user search
  - Standard JPA operations

### 6. Controllers (`controller/`)
#### `AuthController.java`
- Authentication endpoint handler
- Endpoints:
  - POST /api/auth/register: User registration
  - POST /api/auth/login: User authentication
  - POST /api/auth/logout: Session termination
- Features:
  - Request validation
  - Response formatting
  - Error handling

#### `HomeController.java`
- Basic web interface controller
- Endpoints:
  - GET /: Home page

### 7. DTOs (`dto/`)
#### `AuthenticationRequest.java`
- Login request data structure
- Fields:
  - email: User identifier
  - password: Authentication credential

#### `AuthenticationResponse.java`
- Authentication response container
- Fields:
  - token: JWT authentication token

#### `RegisterRequest.java`
- Registration data structure
- Fields:
  - name: User's full name
  - email: User's email
  - password: Desired password
  - role: Requested role

### 8. Resources (`resources/`)
#### `application.properties`
- Application configuration file
- Settings:
  - Database configuration
  - JPA/Hibernate properties
  - Security settings
  - Server configuration
  - Logging preferences

### 9. API-First Approach
- HTML frontend removed for API-focused testing
- All functionality accessible through RESTful endpoints
- Supports tools like Postman, curl, or any HTTP client for testing

### 10. JWT Security (`security/`)
#### `JwtUtils.java`
- JWT token utilities
- Features:
  - Token generation
  - Token validation
  - User details extraction
  - Role-based authorization support
- Methods:
  - generateJwtToken(): Creates JWT with user details and roles
  - validateJwtToken(): Verifies token integrity
  - getUserNameFromJwtToken(): Extracts username
  - getRolesFromJwtToken(): Extracts user roles

#### `JwtAuthenticationFilter.java`
- JWT authentication filter
- Features:
  - Token extraction from requests
  - Token validation
  - User authentication setup
  - Request authorization

## API Testing Guide

### Setting Up Postman for Testing
1. Download and install [Postman](https://www.postman.com/downloads/)
2. Create a new collection for Asset Management System
3. Set up environment variables for base URL (e.g., http://localhost:8080)

### Key API Endpoints

#### Authentication
1. **Register a User** (No authentication required)
   - Method: POST
   - URL: http://localhost:8080/api/auth/register
   - Headers:
     - Content-Type: application/json
   - Body (JSON):
   ```json
   {
     "name": "Test User",
     "email": "test@example.com",
     "password": "password123",
     "role": "MANAGER"
   }
   ```

2. **Login** (No authentication required)
   - Method: POST
   - URL: http://localhost:8080/api/auth/login
   - Headers:
     - Content-Type: application/json
   - Body (JSON):
   ```json
   {
     "email": "test@example.com",
     "password": "password123"
   }
   ```
   - Response (JWT token with user details):
   ```json
   {
     "token": "eyJhbGciOiJIUzUxMiJ9...", 
     "email": "test@example.com",
     "name": "Test User",
     "role": "MANAGER",
     "tokenType": "Bearer" 
   }
   ```

3. **Logout**
   - Method: POST
   - URL: http://localhost:8080/api/auth/logout
   - Headers:
     - Authorization: Bearer [JWT token]

4. **Get Current User**
   - Method: GET
   - URL: http://localhost:8080/api/auth/me
   - Headers:
     - Authorization: Bearer [JWT token]
   - Response:
   ```json
   {
     "email": "test@example.com",
     "name": "Test User",
     "role": "MANAGER",
     "authorities": [
       {
         "authority": "ROLE_MANAGER"
       }
     ]
   }
   ```

#### Asset Management
1. **Get All Assets**
   - Method: GET
   - URL: http://localhost:8080/api/assets
   - Headers:
     - Authorization: Bearer [JWT token]

2. **Get Asset By ID**
   - Method: GET
   - URL: http://localhost:8080/api/assets/{id}
   - Headers:
     - Authorization: Bearer [JWT token]

3. **Create New Asset**
   - Method: POST
   - URL: http://localhost:8080/api/assets
   - Headers:
     - Authorization: Bearer [JWT token]
     - Content-Type: application/json
   - Body (JSON):
   ```json
   {
     "name": "Office Laptop",
     "type": "ELECTRONIC",
     "status": "AVAILABLE",
     "description": "HP EliteBook, 16GB RAM, 512GB SSD"
   }
   ```

### Running the Application
1. Start MySQL database service
2. Navigate to project directory:
   ```bash
   cd "e:\FCIH\6th term\SEW-2\Asset-Tracking-system-main\Manager"
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Begin API testing with Postman or curl

### Running Tests with curl

#### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","password":"password123","role":"MANAGER"}'
```

Alternative PowerShell command for Windows:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/auth/register" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"name":"Test User","email":"test@example.com","password":"password123","role":"MANAGER"}'
```

#### Login with credentials
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

Alternative PowerShell command for Windows:
```powershell
$loginResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"email":"test@example.com","password":"password123"}'

# Store the JWT token in a variable for future requests
$token = $loginResponse.token
```

#### Get API information (using JWT token)
```bash
curl -X GET http://localhost:8080/ \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

PowerShell command for Windows:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/" `
  -Method GET `
  -Headers @{Authorization = "Bearer $token"}
```

#### Get Current User Information
```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

PowerShell command for Windows:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/auth/me" `
  -Method GET `
  -Headers @{Authorization = "Bearer $token"}
```

### Troubleshooting API Issues
1. **401 Unauthorized**
   - Check that you're including the correct JWT token in the Authorization header
   - Verify that the token has not expired (default expiration is 24 hours)
   - Verify that the user exists in the database
   - Ensure the password is correct when requesting a new token

2. **403 Forbidden**
   - Check that the user has the correct role for the requested resource
   - Verify that the JWT token contains the expected role claims
   
3. **404 Not Found**
   - Verify the endpoint URL is correct
   - Check if you're using the correct HTTP method

3. **500 Internal Server Error**
   - Check application logs for detailed error information
   - Verify database connection
   - Check for constraint violations or validation errors

### Testing Environment Setup
1. Configure application properties for testing
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/asset_management_test
   spring.datasource.username=test_user
   spring.datasource.password=test_password
   ```

2. Run the application with a test profile
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=test
   ```