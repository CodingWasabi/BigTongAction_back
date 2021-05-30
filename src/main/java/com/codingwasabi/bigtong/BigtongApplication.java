package com.codingwasabi.bigtong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class BigtongApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigtongApplication.class, args);
	}

}
