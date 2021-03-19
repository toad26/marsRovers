package com.mars.marsRovers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.mars.resource", "com.mars.service"})
public class MarsRoversApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarsRoversApplication.class, args);
	}

}
