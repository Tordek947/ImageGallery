# ImageGallery
You can find task requirements #you_know_where  

## Prerequisities

In order to run this example successfully, you should have the next items installed:
- Docker (with docker-compose);
- JDK 1.8+;
- Maven (optional).

## Launch Procedure

To run this app locally, you have to perform the following set of actions (from the bash-console of the project root folder):  
- `docker-compose up -d` to start dockerized mysql local database;
- `mvn spring-boot:run` to launch an app itself (in case you don't have Maven installed, you can run `mvnw` instead of `mvn`).

## API Discovery

This project leverages **Open API Specification** annotations and springdoc-openapi-ui library, so RESTful API is automatically generated.  
To visit it, follow http://localhost:8080/swagger-ui.html  
Currently a cronJob, that updates 'local cash' (i. e., content of local mysql db) runs once per 5 minutes starting from :00, so to find cache data populated you should wait for 5 minutes.  
Alternatively, you can change job cron expression, using `cronjob.image.metadata.refresh-cron` property in application.properties file.
