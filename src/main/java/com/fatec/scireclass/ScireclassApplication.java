package com.fatec.scireclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ScireClass Api", version = "1", description = "API desenvolvida para o projeto integrador da Fatec"))
public class ScireclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScireclassApplication.class, args);
	}

}
