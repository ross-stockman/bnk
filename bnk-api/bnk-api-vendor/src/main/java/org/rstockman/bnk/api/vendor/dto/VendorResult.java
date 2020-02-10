package org.rstockman.bnk.api.vendor.dto;

import org.rstockman.bnk.common.dto.StandardResultDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class VendorResult extends StandardResultDTO {
	private Long id;
	private String name;
}
