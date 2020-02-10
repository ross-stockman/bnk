package org.rstockman.bnk.api.vendor.dto;

import org.rstockman.bnk.common.dto.StandardRequestParamsDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VendorRequestParams extends StandardRequestParamsDTO {
	private Long id;
	private String name;
}
