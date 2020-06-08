package com.paulo.altran.gasto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.RestTemplate;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@SpringBootApplication
@EnableResourceServer
@EnableFeignClients
public class GastoApplication implements CommandLineRunner {

	@Bean
	public RequestInterceptor getInterceptorDeAutenticacao() {
		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate template) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					return;
				}
				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
				template.header("Authorization", "Bearer" + details.getTokenValue());
			}
		};
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(GastoApplication.class, args);
	}

//	@Autowired
//	private GastoRepository repo;
//	'1', 'Mercado Extra', '256.98', NULL, '1', '2016-07-08 15:30:00', ''

	@Override
	public void run(String... args) throws Exception {
//		Gasto gasto = Gasto.builder().descricao("tes").build();
//		Page<Gasto> buscarData = this.repo.buscarData(null,null,null, LocalDate.of(2016, 7, 8), PageRequest.of(0, 10));
//
//		buscarData.getContent().forEach(x -> {
//			System.out.println(x.getId());
//		});

	}

}
