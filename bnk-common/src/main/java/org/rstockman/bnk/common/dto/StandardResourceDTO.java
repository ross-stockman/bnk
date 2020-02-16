package org.rstockman.bnk.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class StandardResourceDTO {
	@JsonProperty("_key")
	private String key;
	@JsonProperty("_version")
	private String version;
	@JsonProperty("_created")
	private Date created;
	@JsonProperty("_updated")
	private Date updated;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
