# 🌆 Cities Data Service

## 📌 Overview

"Cities Data Service" 🛠️ is a Java-based application designed for cities data management. It integrates cloud technologies ☁️, database systems, and programming frameworks to provide a scalable and efficient solution for urban management.

## 🌐 Application Availability

The "Cities Data Service" Java application is deployed on SAP BTP Cloud Foundry within a trial account. You can access it [here](https://cities-service.cfapps.us10-001.hana.ondemand.com). Please note that in the trial account, applications are periodically stopped. To gain access as a member of the subaccount or to request the application to be started on demand, contact the developer.

## 📞 Contact Information

**Petar Rusev**
- Email: [petar.rusev.87@gmail.com](mailto:petar.rusev.87@gmail.com)

## 🌟 Key Features

- 🏙️ **Cities Data Management**: Comprehensive management of city data.
- 🔄 **Batch Data Processing**: Efficient processing of large data volumes with Spring Batch.
- 🔍 **Dynamic Search and Filtering**: Enhanced search functionalities with QueryDSL.
- 📚 **API Documentation and Testing**: Integrated Swagger for interactive API documentation.

## 🛠️ Technology Stack

- ☕ **Spring Boot**
- 📂 **Hibernate ORM**
- 💾 **H2 Database** (local development)
- 🌐 **PostgreSQL** (cloud deployment on SAP BTP)
- 📈 **Spring Batch**
- 🔎 **QueryDSL**
- 📖 **Swagger**
- ☁️ **Cloud Foundry** (SAP BTP)

## 🚀 Prerequisites

- Java JDK 17+
- Maven
- Docker

## ⚙️ Setup Instructions

### 1. Clone the Repository
```shell
git clone https://github.com/petar-rusev/cities-service
cd cities-service
```

### 2. Install Dependencies
```shell
mvn clean install
```

### 3. Run the Application
>Locally
```shell
mvn spring-boot:run
```
>On Docker
```shell
docker pull petarrusev/cities-service:latest
docker pull petarrusev/cities-app:latest
docker-compose up
```
### 🔗 Access services at http://localhost:8080 (Service) and http://localhost:8081 (UI).

## ☁️ Deployment on SAP BTP

### Cloud Prerequisites
1. **🔑 Create a SAP BTP Trial Account**
2. **🔧 Install Cloud Foundry CLI**
3. **💽 Provision PostgreSQL**
4. **🔗 Bind the Service**

### Deployment Commands
```shell
cf login -a https://api.cf.eu10.hana.ondemand.com
cf push
```
## 📖 Swagger Documentation

- 🏠 Local: http://localhost:8080/swagger-ui.html
- ☁️ Cloud: [https://cities-service.cfapps.us10-001.hana.ondemand.com/swagger-ui/index.html](https://cities-service.cfapps.us10-001.hana.ondemand.com/swagger-ui/index.html)


