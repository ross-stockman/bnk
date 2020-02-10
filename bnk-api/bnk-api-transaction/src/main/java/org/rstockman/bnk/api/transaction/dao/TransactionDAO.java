package org.rstockman.bnk.api.transaction.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.rstockman.bnk.api.transaction.dto.Account;
import org.rstockman.bnk.api.transaction.dto.Party;
import org.rstockman.bnk.api.transaction.dto.TransactionRequestParams;
import org.rstockman.bnk.api.transaction.dto.TransactionResult;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.rstockman.bnk.common.rest.SimpleRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAO implements SimpleDAO<TransactionResult, TransactionRequestParams, String> {

	private static final Map<String, TransactionResult> MAP = new HashMap<>();

	@Value("${account.api.uri}")
	private String accountUrl;

	@Value("${party.api.uri}")
	private String partyUrl;

	@Autowired
	private SimpleRestClient<Account, String> accountRestClient;

	@Autowired
	private SimpleRestClient<Party, String> partyRestClient;

	@Override
	public Optional<TransactionResult> get(String key) {
		return Optional.ofNullable(MAP.get(key));
	}

	@Override
	public List<TransactionResult> getAll(TransactionRequestParams params) {
		return new ArrayList<TransactionResult>();
	}

	@Override
	public String create(TransactionResult obj) {
		if (Objects.nonNull(obj.getParty())) {
			partyRestClient.get(obj.getParty().getId());
		}
		if (Objects.nonNull(obj.getAccount())) {
			accountRestClient.get(obj.getAccount().getId());
		}
		var key = UUID.randomUUID().toString();
		MAP.put(key, obj);
		return key;
	}

	@Override
	public void put(String key, TransactionResult obj) {
		if (Objects.nonNull(obj.getParty())) {
			partyRestClient.get(obj.getParty().getId());
		}
		if (Objects.nonNull(obj.getAccount())) {
			accountRestClient.get(obj.getAccount().getId());
		}
		MAP.put(key, obj);
	}

	@Override
	public void delete(String key) {
		MAP.remove(key);
	}
}
