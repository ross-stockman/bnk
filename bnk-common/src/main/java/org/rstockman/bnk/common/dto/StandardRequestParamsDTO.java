package org.rstockman.bnk.common.dto;

import lombok.Data;

@Data
public abstract class StandardRequestParamsDTO {
	private String fields;
	private String sort;
	private Integer limit;
	private Integer page;
}
