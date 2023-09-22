# CLOUD FILE STORAGE
Welcome to cloud file storage repository. This application allow users to store their file in remote server.

Authentication working by custom tokens which valid for 12 hours.

*Max size of file 63KB.*
## Run application 
### With local database
If want to run application with local database then change database url, username and password in [*application.properites*](src/main/resources/application.properties).
- Next build the project
  ```cmd
    gradle clean build
  ```
- And run the application
  ```cmd
    java -jar build/libs/cloud_file_storage-1.2.jar
  ```

### With Docker Compose
- Build the project
  ```cmd 
    gradle clean build
  ```
- Run the application
  ```cmd
    docker-compose up
  ```

## Contract
OpenApi 3.0 specification for Cloud File Storage is in [file](CloudFileStorageSpecification.yaml).

## Technologies
- ***Java 17***
- ***Spring (core, boot, mvc, data jpa, security)***
- ***JUnit 4/5***
- ***Lombok***
- ***Mockito***
- ***Gradle 8.1***
- ***Docker 24.0.2***