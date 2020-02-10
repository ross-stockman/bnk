package org.rstockman.bnk.api.account.dto;

import org.rstockman.bnk.common.dto.StandardResultDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Bank extends StandardResultDTO {
	private String id;
	private String name;
}
