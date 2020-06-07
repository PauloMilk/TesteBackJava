package com.paulo.altra.gasto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class GastoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GastoApplication.class, args);
	}

}
