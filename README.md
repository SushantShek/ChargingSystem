## **CHARGING SESSION APPLICATION**
This is an application which provide service endpoint to track the charging session.

The session essentially are of state IN_PROGRESS or FINISHED

#### **SERVICE ENDPOINTS**
1. POST: /chargingSessions
2. GET : /chargingSessions
3  GET : /chargingSessions/summary
4. PUT : /chargingSessions/{id}


# APPLICATION DETAILS

The application is a SpringBoot APP.
Project uses java version 8 or higher.

Navigate to the root of the project via command line and execute the command

`mvn spring-boot:run`

The application is running on Tomcat default port 
`port : 8080`

Running on localhost the curl URL are 

`curl --location --request POST 'localhost:8080/chargingSessions' \
--header 'Accept: application/json' \
--header 'Accept-Language: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{"stationId": <id>}'`

`curl --location --request GET 'localhost:8080/chargingSessions'`

`curl --location --request PUT 'localhost:8080/chargingSessions/{id}'`

`curl --location --request GET 'localhost:8080/chargingSessions/summary'`