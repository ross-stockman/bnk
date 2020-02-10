package org.rstockman.bnk.api.account.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.rstockman.bnk.api.account.dto.AccountRequestParams;
import org.rstockman.bnk.api.account.dto.AccountResult;
import org.rstockman.bnk.api.account.dto.Bank;
import org.rstockman.bnk.api.account.dto.Customer;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.rstockman.bnk.common.rest.SimpleRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO implements SimpleDAO<AccountResult, AccountRequestParams, String> {

	private static final Map<String, AccountResult> MAP = new HashMap<>();

	@Value("${bank.api.uri}")
	private String bankUrl;

	@Value("${customer.api.uri}")
	private String customerUrl;

	@Autowired
	private SimpleRestClient<Bank, String> bankRestClient;

	@Autowired
	private SimpleRestClient<Customer, String> customerRestClient;

	@Override
	public Optional<AccountResult> get(String key) {
		return Optional.ofNullable(MAP.get(key));
	}

	@Override
	public List<AccountResult> getAll(AccountRequestParams params) {
		return new ArrayList<AccountResult>();
	}

	@Override
	public String create(AccountResult obj) {
		if (Objects.nonNull(obj.getCustomer())) {
			customerRestClient.get(obj.getCustomer().getId());
		}
		if (Objects.nonNull(obj.getBank())) {
			bankRestClient.get(obj.getBank().getId());
		}
		var key = UUID.randomUUID().toString();
		MAP.put(key, obj);
		return key;
	}

	@Override
	public void put(String key, AccountResult obj) {
		if (Objects.nonNull(obj.getCustomer())) {
			customerRestClient.get(obj.getCustomer().getId());
		}
		if (Objects.nonNull(obj.getBank())) {
			bankRestClient.get(obj.getBank().getId());
		}
		MAP.put(key, obj);
	}

	@Override
	public void delete(String key) {
		MAP.remove(key);
	}
}
