## Document

Check out the [MY DOCUMENT - DOCS](https://docs.google.com/document/d/1XI2XfRsR_H96RntE31bhkS5xaV0gWCJ8_zjLHTX9h44/edit?usp=sharing).

## Demo

Check out the [VIDEO DEMO WEB](https://youtu.be/gcmjH-mjqhA).

## Other Document (DOCUMENT WORD FILE, PPT FILE ABOUT INTO ABOUT WEB, DATA CREATE - SQL FILE, VIDEO DEMO FILE)

Check out the [DOCUMENT WORD FILE](https://docs.google.com/document/d/1XI2XfRsR_H96RntE31bhkS5xaV0gWCJ8_zjLHTX9h44/edit?usp=drive_link).

Check out the [PPT FILE ABOUT INTO ABOUT WEB](https://docs.google.com/presentation/d/1oBhCjA5n8nSk-cEV_cZ6UQPpaXjvb2_Q/edit?usp=drive_link&ouid=114231269998507308345&rtpof=true&sd=true).

Check out the [SQL FILE TO CREATE DATA](https://drive.google.com/file/d/1--kgHzxzwE34oiYVVtBA3qMVjcIKxj8L/view?usp=drive_link).

Check out the [VIDEO DEMO FILE](https://drive.google.com/file/d/1zceoe7Aumy7JkWMF3AXj3ch3bexOMmcX/view?usp=sharing).

# Some image about my web

FORM CHOOSE ROLE

![](https://i.imgur.com/j7n5RTT.png)  

FORM LOGIN ROLE

![](https://i.imgur.com/wxMJ5TG.png)  

FORM LOGIN ROLE

![](https://i.imgur.com/vFjTOkI.png)  

ADD SKILL

![](https://i.imgur.com/xmGM2Rw.png) 

JOB INFO AND FIND SUITABLE CANDIDATE 

![](https://i.imgur.com/fdCHDPE.png)  

SEND MAIL INVITE JOB 

![](https://i.imgur.com/Cah0gfX.png)  

MAIL INVITED JOB 

![](https://i.imgur.com/Q6nir6f.png) 

JOB OPENING FOR CANDIDATES

![](https://i.imgur.com/MptWWGx.png) 

NAVBAR BUTTON CHOOSE FUNCTION

![](https://i.imgur.com/lDRjZCt.png) 

# Job Posting Web Application

This is a Java Spring Boot-based web application for managing job postings. It allows employers to post jobs and manage applications, while job seekers can browse and apply for jobs. 

## Features

- OAuth2 login (Google Sign-In)
- RESTful APIs for job and user management
- Dynamic content rendering with Thymeleaf
- Secure user authentication and role-based access control
- Email notifications using Google Gmail API
- Internationalization support
- Validation for form inputs
- Database integration with MariaDB
- Developer-friendly tools like Lombok and Spring DevTools
- 
## Technologies and Libraries Used

- **Spring Boot**:
  - `spring-boot-starter-oauth2-client`: OAuth2 client integration
  - `spring-boot-starter-data-jpa`: JPA for database interaction
  - `spring-boot-starter-data-rest`: Building RESTful APIs
  - `spring-boot-starter-security`: Authentication and security
  - `spring-boot-starter-web`: Web application development
  - `spring-boot-starter-web-services`: SOAP web services
  - `spring-boot-starter-thymeleaf`: Server-side HTML rendering
  - `spring-boot-starter-validation`: Data validation
  - `spring-boot-starter-mail`: Email support
  - `spring-boot-starter-jdbc`: JDBC support

- **Google Libraries**:
  - `google-auth-library-oauth2-http`: OAuth2 authentication
  - `google-api-client`: Google APIs client library
  - `google-oauth-client-jetty`: OAuth2 client with Jetty integration
  - `google-api-services-gmail`: Gmail API integration

- **JSON Web Token (JWT)**:
  - `jjwt-api`, `jjwt-impl`, `jjwt-jackson`: JWT support for secure token handling

- **Other Libraries**:
  - `nv-i18n`: Internationalization utilities
  - `mail`: JavaMail API
  - `mariadb-java-client`: MariaDB database connector
  - `gretty`: Web application server for development

- **Development Tools**:
  - `lombok`: Simplified Java development with annotations
  - `spring-boot-devtools`: Hot reload and development enhancements

- **Testing**:
  - `spring-boot-starter-test`: Testing framework
  - `spring-security-test`: Security testing utilities
  - `junit-platform-launcher`: JUnit integration

## Requirements

- Java 17 or later
- MariaDB or compatible database
- Gmail API credentials for email functionality

## Getting Started

### Clone the Repository

```bash
https://github.com/yourusername/job-posting-webapp.git
cd job-posting-webapp
```

### Setup Database

1. Install MariaDB and create a database.
2. Update `application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Configure Gmail API

1. Obtain Gmail API credentials from Google Cloud Console.
2. Place the credentials file in `src/main/resources` and update the path in `application.properties`:

```properties
gmail.credentials.path=src/main/resources/credentials.json
```

### Run the Application

Use the following command to start the application:

```bash
./mvnw spring-boot:run
```

The application will be available at [http://localhost:8080](http://localhost:8080).

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push the branch.
4. Create a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any questions or suggestions, feel free to reach out at trantrunghieu.mail@gmail.com.
