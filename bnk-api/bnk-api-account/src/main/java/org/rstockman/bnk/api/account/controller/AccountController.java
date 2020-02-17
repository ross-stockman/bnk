package org.rstockman.bnk.api.account.controller;

import java.util.List;

import org.rstockman.bnk.api.account.dto.AccountRequestParams;
import org.rstockman.bnk.api.account.dto.AccountResource;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private SimpleDAO<AccountResource, AccountRequestParams, String, String> dao;

	@GetMapping("/{id}")
	public AccountResource getAccount(@PathVariable String id) {
		return dao.get(id).get();
	}

	@GetMapping
	public List<AccountResource> getAll(AccountRequestParams params) {
		return dao.getAll(params);
	}

	@PostMapping
	public String create(@RequestBody AccountResource obj) {
		return dao.create(obj);
	}

	@PutMapping("/{id}")
	public void put(@PathVariable String key, @RequestBody AccountResource obj) {
		dao.put(key, obj);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String key) {
		dao.delete(key);
	}
}
