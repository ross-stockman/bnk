package org.rstockman.bnk.api.account.rest;

import org.rstockman.bnk.api.account.dto.Bank;
import org.rstockman.bnk.common.exceptions.ExceptionFactory;
import org.rstockman.bnk.common.rest.SimpleRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class BankRestClient implements SimpleRestClient<Bank, String> {

	@Autowired
	private WebClient bankWebClient;

	@Override
	public Bank get(String key) {
		var result = bankWebClient.get().uri("/" + key).accept(MediaType.APPLICATION_JSON).exchange();
		return result.flatMap(response -> {
			if (response.statusCode() == HttpStatus.NOT_FOUND) {
				throw ExceptionFactory.resourceConflict("/banks/" + key);
			} else {
				return response.bodyToMono(Bank.class);
			}
		}).block();
	}

}
