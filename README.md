# FHIR-Gateway-RBAC

## Project Overview
A secure Spring Boot-based backend gateway application engineered for healthcare data integration, featuring robust Role-Based Access Control (RBAC) and advanced security filters to protect sensitive patient records.

## Key Features & Architecture
* **Role-Based Security & Access Control:** Implements a strict RBAC mechanism to ensure users access only authorized endpoints matching their assigned roles—restricting regular patients solely to their personal health records while granting doctors and administrators full permissions over patient-specific FHIR data streams.
* **Core Technical Stack:** Built using Java and Spring Boot, the system integrates Spring Security, custom security filters like `RateLimitFilter`, and MySQL database persistence via Spring Data JPA for reliable backend performance and protection.

## Tech Stack
* **Language & Framework:** Java, Spring Boot
* **Security:** Spring Security, RBAC, Custom Filters
* **Database & ORM:** MySQL, Spring Data JPA
* **Version Control:** Git & GitHub

* Role-Based Security & Access Control: It implements a strict RBAC mechanism to ensure users access only authorized endpoints matching their roles—restricting patients solely to their personal records while granting doctors and administrators full permissions over patient-specific FHIR data streams.
