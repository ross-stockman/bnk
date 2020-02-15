package org.rstockman.bnk.api.vendor.dto;

import org.rstockman.bnk.common.dto.StandardResourceDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class VendorResource extends StandardResourceDTO {
	private Long id;
	private String name;
}
