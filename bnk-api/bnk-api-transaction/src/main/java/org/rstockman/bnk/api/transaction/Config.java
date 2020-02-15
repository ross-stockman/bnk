package org.rstockman.bnk.api.transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

	@Value("${bnk.api.party.uri}")
	private String partyUrl;

	@Value("${bnk.api.account.uri}")
	private String accountUrl;

	@Bean
	public WebClient partyWebClient() {
		return WebClient.create(partyUrl);
	}

	@Bean
	public WebClient accountWebClient() {
		return WebClient.create(accountUrl);
	}
}
