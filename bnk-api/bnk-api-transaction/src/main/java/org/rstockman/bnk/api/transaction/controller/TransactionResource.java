package org.rstockman.bnk.api.transaction.controller;

import java.util.List;

import org.rstockman.bnk.api.transaction.dto.TransactionRequestParams;
import org.rstockman.bnk.api.transaction.dto.TransactionResult;
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
@RequestMapping("/transactions")
public class TransactionResource {

	@Autowired
	private SimpleDAO<TransactionResult, TransactionRequestParams, String, String> dao;

	@GetMapping("/{id}")
	public TransactionResult getTransaction(@PathVariable String id) {
		return dao.get(id).get();
	}

	@GetMapping
	public List<TransactionResult> getAll(TransactionRequestParams params) {
		return dao.getAll(params);
	}

	@PostMapping
	public String create(@RequestBody TransactionResult obj) {
		return dao.create(obj);
	}

	@PutMapping("/{id}")
	public void put(@PathVariable String key, @RequestBody TransactionResult obj) {
		dao.put(key, obj);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String key) {
		dao.delete(key);
	}
}
