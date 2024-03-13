package com.fatec.scireclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ScireclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScireclassApplication.class, args);
	}

}
