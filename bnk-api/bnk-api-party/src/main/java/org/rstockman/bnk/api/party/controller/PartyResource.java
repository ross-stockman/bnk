package org.rstockman.bnk.api.party.controller;

import java.util.List;

import org.rstockman.bnk.api.party.dto.PartyRequestParams;
import org.rstockman.bnk.api.party.dto.PartyResult;
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
@RequestMapping("/parties")
public class PartyResource {

	@Autowired
	private SimpleDAO<PartyResult, PartyRequestParams, String> dao;

	@GetMapping("/{id}")
	public PartyResult getParty(@PathVariable String id) {
		return dao.get(id).get();
	}

	@GetMapping
	public List<PartyResult> getAll(PartyRequestParams params) {
		return dao.getAll(params);
	}

	@PostMapping
	public String create(@RequestBody PartyResult obj) {
		return dao.create(obj);
	}

	@PutMapping("/{id}")
	public void put(@PathVariable String key, @RequestBody PartyResult obj) {
		dao.put(key, obj);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String key) {
		dao.delete(key);
	}
}