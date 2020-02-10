package org.rstockman.bnk.api.account.rest;

import org.rstockman.bnk.api.account.dto.Customer;
import org.rstockman.bnk.common.exceptions.ExceptionFactory;
import org.rstockman.bnk.common.rest.SimpleRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class CustomerRestClient implements SimpleRestClient<Customer, String> {

	@Autowired
	private WebClient customerWebClient;

	@Override
	public Customer get(String key) {
		var result = customerWebClient.get().uri("/" + key).accept(MediaType.APPLICATION_JSON).exchange();
		return result.flatMap(response -> {
			if (response.statusCode() == HttpStatus.NOT_FOUND) {
				throw ExceptionFactory.resourceConflict("/customers/" + key);
			} else {
				return response.bodyToMono(Customer.class);
			}
		}).block();
	}

}
