package org.rstockman.bnk.api.party.dto;

import org.rstockman.bnk.common.dto.StandardResourceDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Vendor extends StandardResourceDTO {
	private String id;
	private String name;
}
