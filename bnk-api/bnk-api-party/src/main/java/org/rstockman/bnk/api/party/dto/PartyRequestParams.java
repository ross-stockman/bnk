package org.rstockman.bnk.api.party.dto;

import org.rstockman.bnk.common.dto.StandardRequestParamsDTO;

public class PartyRequestParams extends StandardRequestParamsDTO {
	private Long id;
	private String name;

	public PartyRequestParams() {
		super();
	}

	public PartyRequestParams(Long id, String name, String fields, String sort, Integer limit, Integer page) {
		super();
		this.id = id;
		this.name = name;
		setFields(fields);
		setSort(sort);
		setLimit(limit);
		setPage(page);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}