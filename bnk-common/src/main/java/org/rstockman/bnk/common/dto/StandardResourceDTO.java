package org.rstockman.bnk.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class StandardResourceDTO {
	@JsonProperty("_key")
	private String key;
	@JsonProperty("_version")
	private String version;
	@JsonProperty("_created")
	private Date created;
	@JsonProperty("_updated")
	private Date updated;
}
