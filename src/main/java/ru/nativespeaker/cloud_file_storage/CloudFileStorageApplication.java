package ru.nativespeaker.cloud_file_storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class CloudFileStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudFileStorageApplication.class, args);
	}
}
