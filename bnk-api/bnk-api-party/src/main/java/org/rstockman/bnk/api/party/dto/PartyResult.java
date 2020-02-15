package org.rstockman.bnk.api.party.dto;

import org.rstockman.bnk.common.dto.StandardResourceDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PartyResult extends StandardResourceDTO {
	private Long id;
	private String name;
	private Vendor vendor;
	private Category category;
}
