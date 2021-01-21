package com.bruno.caetano.dev.itemstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ItemStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemStorageApplication.class, args);
	}

}
