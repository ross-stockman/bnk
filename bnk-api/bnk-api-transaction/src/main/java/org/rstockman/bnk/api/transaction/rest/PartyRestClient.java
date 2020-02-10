package org.rstockman.bnk.api.transaction.rest;

import org.rstockman.bnk.api.transaction.dto.Party;
import org.rstockman.bnk.common.exceptions.ExceptionFactory;
import org.rstockman.bnk.common.rest.SimpleRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class PartyRestClient implements SimpleRestClient<Party, String> {

	@Autowired
	private WebClient partyWebClient;

	@Override
	public Party get(String key) {
		var result = partyWebClient.get().uri("/" + key).accept(MediaType.APPLICATION_JSON).exchange();
		return result.flatMap(response -> {
			if (response.statusCode() == HttpStatus.NOT_FOUND) {
				throw ExceptionFactory.resourceConflict("/parties/" + key);
			} else {
				return response.bodyToMono(Party.class);
			}
		}).block();
	}

}
