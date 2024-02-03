# Triply API Documentation

## Introduction

Welcome to the documentation for the Triply API.
This API provides functionality for managing companies, employees, vehicles, and emissions data. It
also offers suggestions for replacing vehicles with electric alternatives based on emissions and
mileage data.

## Table of Contents

1. [Authentication](#authentication)
2. [API Endpoints](#api-endpoints)
3. [CSV Parsing](#csv-parsing)
4. [Calculations](#calculations)
5. [Libraries and Frameworks](#libraries-and-frameworks)
6. [Database Schema](#database-schema)
7. [Setup Instructions](#setup-instructions)
8. [Testing](#testing)

## Authentication

The API uses JSON Web Tokens (JWT) for authentication. Two endpoints are provided:

- **Login:** `/auth/login` (POST)
    - Authenticate user and generate an access token.

- **Refresh Token:** `/auth/refresh` (POST)
    - Refresh the access token using a valid refresh token.

Check: http://localhost:8080/api/v1/triply/swagger-ui/index.html for API documentation.

## Authorization

- The API uses custom annotation and interceptor to enforce roles. Available roles are:
  ROLE_SUPER_ADMIN, ROLE_COMPANY_EMPLOYEE, ROLE_COMPANY_ADMIN

## API Endpoints

1. **Create a New Company:** `/company` (POST)
    - Creates a new company.
    - Role: ROLE_SUPER_ADMIN

2. **Upload Vehicle Models:** `/vehicle-models:upload` (POST)
    - Uploads a CSV file with vehicle model data.
    - Request Body: Multipart Form Data with "file" parameter, should be in csv format.
    - Role: ROLE_SUPER_ADMIN

3. **Upload Employees for a Company:** `/company/{companyId}/employees:upload` (POST)
    - Uploads a CSV file with employee data for a specific company.
    - Request Body: Multipart Form Data with "file" parameter, should be in csv format.
    - Path Parameter: `companyId` (integer)
    - Role: ROLE_SUPER_ADMIN

4. **Upload Vehicle Mileages for a Company:** `/company/{companyId}/employees/mileage:upload` (POST)
    - Uploads a CSV file with vehicle mileage data for a specific company, year, month, and week.
    - Request Body: Multipart Form Data with "file" parameter, should be in csv format.
    - Path Parameter: `companyId` (integer)
    - Query Parameters: `year` (string), `month` (string - enum), `week` (integer)
    - Role: ROLE_SUPER_ADMIN, ROLE_COMPANY_ADMIN

5. **Get Vehicle Suggestions for an Employee:** `/employee/{employeeId}/vehicle-model/suggestions` (
   GET)
    - Retrieves vehicle suggestions for a specific employee.
    - Path Parameter: `employeeId` (integer)
    - Role: ROLE_COMPANY_EMPLOYEE

6. **Get Emission Summary for an Employee:** `/employee/{employeeId}/emissions:summary` (GET)
    - Retrieves emission summary for a specific employee, optionally filtered by year, month, and
      week.
    - Path Parameter: `employeeId` (integer)
    - Query Parameters: `year` (string), `month` (string - enum), `week` (integer)
    - Role: ROLE_COMPANY_EMPLOYEE

7. **Get Emission Summary for a Company:** `/company/{companyId}/emissions:summary` (GET)
    - Retrieves emission summary for a specific company, optionally filtered by year, month, and
      week.
    - Path Parameter: `companyId` (integer)
    - Query Parameters: `year` (string), `month` (string - enum), `week` (integer)
    - Role: ROLE_COMPANY_EMPLOYEE, ROLE_COMPANY_ADMIN

Check: http://localhost:8080/api/v1/triply/swagger-ui/index.html for API documentation.

## CSV Parsing

Uploaded CSV files should follow the specified format with columns for employee ID, vehicle type,
and average weekly mileage.

### Vehicle Model Upload CSV Format

FuelTypes: REGULAR_GASOLINE, PREMIUM_GASOLINE, DIESEL, ETHANOL, NATURAL_GAS, ELECTRIC, HYBRID,
HYDROGEN

| make   | name    | fuelType         | emissionPerKm |
|--------|---------|------------------|---------------|
| Toyota | Corolla | REGULAR_GASOLINE | 120.5         |
| Honda  | Accord  | REGULAR_GASOLINE | 130.2         |

- [Global Vehicle Models Upload](src/main/resources/static/vehicle-models.csv)

### Company Employees Upload CSV Format

| employeeId | password      | registrationNumber | vehicleModel | admin |
|------------|---------------|--------------------|--------------|-------|
| EMP001     | password123   | ABC123             | Corolla      | FALSE |
| EMP002     | securePass456 | ABC124             | Accord       | TRUE  |

- [Company 1 Upload](src/main/resources/static/company1-data.csv)
- [Company 2 Upload](src/main/resources/static/company2-data.csv)

### Company Employees Emission Upload CSV Format

| employeeId | distanceTravelledInKm | energyConsumed | fuelConsumed |
|------------|-----------------------|----------------|--------------|
| EMP001     | 150.5                 | 30.2           | 15.7         |
| EMP002     | 180.3                 | 35.1           | 18.5         |

- [Company 1 Upload](src/main/resources/static/company1-mileage.csv)
- [Company 2 Upload](src/main/resources/static/company2-mileage.csv)

## Calculations

- Emissions are calculated based on the fuel type of vehicle and emission per km. Later, other
  mileage fields and vehicle model can be taken into account.
- Suggestions are calculated by fetching emission summary for ELECTRIC vehicle models which whose
  avg weekly distance similar to employee's avg weekly distance (+- 10 km). Then filter is applied
  to remove vehicles which have greater emission than employee.

## Libraries and Frameworks

The API is built using Spring Boot and Java 8+.

## Database Schema

The API uses a relational database with the following
tables: `role`, `company`, `employee`, `employee_role`, `vehicle_model`, `vehicle`, `mileage`,
and `refresh_token`. Refer to the provided SQL script for the complete schema.

[Schema Link](schema.png)

## Setup Instructions

1. Create a database named `triply`.
2. Configure the application.properties file with your database credentials.
3. Build and run the Spring Boot application.
4. All tables would be created using flyway and seeded with initial data.
5. Super Admin EmployeeId: JOHN_DOE, Password: password
6. Check: http://localhost:8080/api/v1/triply/swagger-ui/index.html for API documentation.

## Testing

- Unit tested using JUnit5 for core business logic.
- Manual API tested using postman.