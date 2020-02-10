package org.rstockman.bnk.api.party.rest;

import org.rstockman.bnk.api.party.dto.Category;
import org.rstockman.bnk.common.exceptions.ExceptionFactory;
import org.rstockman.bnk.common.rest.SimpleRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class CategoryRestClient implements SimpleRestClient<Category, String> {

	@Autowired
	private WebClient categoryWebClient;

	@Override
	public Category get(String key) {
		var result = categoryWebClient.get().uri("/" + key).accept(MediaType.APPLICATION_JSON).exchange();
		return result.flatMap(response -> {
			if (response.statusCode() == HttpStatus.NOT_FOUND) {
				throw ExceptionFactory.resourceConflict("/categories/" + key);
			} else {
				return response.bodyToMono(Category.class);
			}
		}).block();
	}

}
