package com.senlainc.warsaw.tyurin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.senlainc.warsaw.tyurin")
@SpringBootApplication(scanBasePackages = "com.senlainc.warsaw.tyurin")
public class PrivateAdsPlacementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivateAdsPlacementSystemApplication.class, args);
    }

}
