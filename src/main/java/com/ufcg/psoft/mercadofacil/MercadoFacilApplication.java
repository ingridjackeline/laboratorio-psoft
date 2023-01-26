package com.ufcg.psoft.mercadofacil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ufcg.psoft.mercadofacil.*")
public class MercadoFacilApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadoFacilApplication.class, args);
	}
	
}
