package com.bruno.caetano.dev.itemstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ItemStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemStorageApplication.class, args);
    }

}
