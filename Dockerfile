FROM openjdk:17-alpine

EXPOSE 8080

COPY build/libs/cloud_file_storage-1.0.jar file_storage.jar

CMD ["java", "-jar", "file_storage.jar"]