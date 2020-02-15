package org.rstockman.bnk.api.transaction.dto;

import java.util.Date;

import org.rstockman.bnk.common.dto.StandardResourceDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TransactionResult extends StandardResourceDTO {
	private String id;
	private String description;
	private String memo;
	private String checkNum;
	private Date transDate;
	private Account account;
	private Party party;
	private Double amount;
}
