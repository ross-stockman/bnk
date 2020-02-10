package org.rstockman.bnk.api.transaction.rest;

import org.rstockman.bnk.api.transaction.dto.Account;
import org.rstockman.bnk.common.exceptions.ExceptionFactory;
import org.rstockman.bnk.common.rest.SimpleRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class AccountRestClient implements SimpleRestClient<Account, String> {

	@Autowired
	private WebClient accountWebClient;

	@Override
	public Account get(String key) {
		var result = accountWebClient.get().uri("/" + key).accept(MediaType.APPLICATION_JSON).exchange();
		return result.flatMap(response -> {
			if (response.statusCode() == HttpStatus.NOT_FOUND) {
				throw ExceptionFactory.resourceConflict("/accounts/" + key);
			} else {
				return response.bodyToMono(Account.class);
			}
		}).block();
	}

}
