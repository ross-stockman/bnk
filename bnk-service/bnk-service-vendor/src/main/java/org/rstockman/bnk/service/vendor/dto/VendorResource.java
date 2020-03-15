package org.rstockman.bnk.service.vendor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.rstockman.bnk.common.dto.StandardResourceDTO;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorResource extends StandardResourceDTO {
    private Long id;
    private String name;

    public VendorResource() {
        super();
    }

    public VendorResource(Long id, String name, String key, String version, Date created, Date updated) {
        super();
        this.id = id;
        this.name = name;
        setKey(key);
        setVersion(version);
        setCreated(created);
        setUpdated(updated);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}