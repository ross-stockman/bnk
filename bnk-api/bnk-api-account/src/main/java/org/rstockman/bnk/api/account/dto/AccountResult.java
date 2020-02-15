package org.rstockman.bnk.api.account.dto;

import org.rstockman.bnk.common.dto.StandardResourceDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AccountResult extends StandardResourceDTO {
	private String id;
	private String name;
	private Bank bank;
	private Customer customer;
	private Double initialBalance;
}
