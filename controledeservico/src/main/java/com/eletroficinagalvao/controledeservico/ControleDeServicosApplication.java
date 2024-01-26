package com.eletroficinagalvao.controledeservico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControleDeServicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleDeServicosApplication.class, args);
	}

}
