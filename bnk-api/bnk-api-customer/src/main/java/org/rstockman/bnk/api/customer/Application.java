package org.rstockman.bnk.api.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.rstockman.bnk")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
