package br.edu.ifba.nutrition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class NutritionApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutritionApplication.class, args);
	}

}
