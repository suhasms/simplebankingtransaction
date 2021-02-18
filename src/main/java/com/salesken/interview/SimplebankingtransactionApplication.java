package com.salesken.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.salesken.interview.model.entity")
@EnableJpaRepositories("com.salesken.interview.model.repository")
public class SimplebankingtransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplebankingtransactionApplication.class, args);
	}

}
