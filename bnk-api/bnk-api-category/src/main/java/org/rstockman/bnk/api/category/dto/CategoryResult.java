package org.rstockman.bnk.api.category.dto;

import org.rstockman.bnk.common.dto.StandardResourceDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CategoryResult extends StandardResourceDTO {
	private Long id;
	private String name;
}
