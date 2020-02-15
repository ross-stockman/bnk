package org.rstockman.bnk.api.customer.dto;

import org.rstockman.bnk.common.dto.StandardResourceDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CustomerResult extends StandardResourceDTO {
	private Long id;
	private String name;
}
