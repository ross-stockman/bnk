package org.rstockman.bnk.api.transaction.dto;

import java.util.Date;

import org.rstockman.bnk.common.dto.StandardResourceDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TransactionResource extends StandardResourceDTO {
	private Long id;
	private String description;
	private String memo;
	private String checkNum;
	private Date transDate;
	private Account account;
	private Party party;
	private Double amount;

	public TransactionResource() {
		super();
	}

	public TransactionResource(Long id, String description, String memo, String checkNum, Date transDate,
			Account account, Party party, Double amount, String key, String version, Date created, Date updated) {
		super();
		this.id = id;
		this.description = description;
		this.memo = memo;
		this.checkNum = checkNum;
		this.transDate = transDate;
		this.account = account;
		this.party = party;
		this.amount = amount;
		setKey(key);
		setVersion(version);
		setCreated(created);
		setUpdated(updated);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
