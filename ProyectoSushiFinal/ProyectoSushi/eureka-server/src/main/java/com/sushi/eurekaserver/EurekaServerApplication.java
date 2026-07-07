package com.sushi.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
		System.out.println("================================================");
		System.out.println(" Eureka Server (Sushi) corriendo en:");
		System.out.println(" http://localhost:8761");
		System.out.println(" Abre esa URL para ver el dashboard con todos");
		System.out.println(" los microservicios registrados.");
		System.out.println("================================================");
	}

}
