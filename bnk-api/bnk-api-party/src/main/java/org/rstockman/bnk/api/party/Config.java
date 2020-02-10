package org.rstockman.bnk.api.party;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

	@Value("${vendor.api.uri}")
	private String vendorUrl;

	@Value("${category.api.uri}")
	private String categoryUrl;

	@Bean
	public WebClient vendorWebClient() {
		return WebClient.create(vendorUrl);
	}

	@Bean
	public WebClient categoryWebClient() {
		return WebClient.create(categoryUrl);
	}
}
