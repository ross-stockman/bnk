package org.rstockman.bnk.common.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class KeyProvisionerService {
	public String getKey() {
		return UUID.randomUUID().toString();
	}
}
