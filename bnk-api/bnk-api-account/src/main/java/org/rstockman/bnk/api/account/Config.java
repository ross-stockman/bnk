package org.rstockman.bnk.api.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

	@Value("${customer.api.uri}")
	private String customerUrl;

	@Value("${bank.api.uri}")
	private String bankUrl;

	@Bean
	public WebClient customerWebClient() {
		return WebClient.create(customerUrl);
	}

	@Bean
	public WebClient bankWebClient() {
		return WebClient.create(bankUrl);
	}
}
