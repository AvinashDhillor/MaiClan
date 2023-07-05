package com.ad.maiclan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MaiclanApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaiclanApplication.class, args);
	}
}
