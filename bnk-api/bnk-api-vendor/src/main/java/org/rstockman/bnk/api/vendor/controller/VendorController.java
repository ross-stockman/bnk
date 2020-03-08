package org.rstockman.bnk.api.vendor.controller;

import java.util.ArrayList;
import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.rstockman.bnk.api.vendor.dto.VendorRequestParams;
import org.rstockman.bnk.api.vendor.dto.VendorResource;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.rstockman.bnk.common.exceptions.ExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendors")
public class VendorController {

	@Autowired
	private SimpleDAO<VendorResource, VendorRequestParams, String, String> dao;

	@GetMapping("/{id}")
	public VendorResource getVendor(@PathVariable String id) {
		var obj = dao.get(id);
		if (obj.isEmpty()) {
			throw ExceptionFactory.resourceNotFound();
		}
		return obj.get();
	}

	@GetMapping
	@HystrixCommand(fallbackMethod = "getAllFallback", commandProperties = {
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
		@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
		@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
		@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
	})
	public List<VendorResource> getAll(VendorRequestParams params) {
		return dao.getAll(params);
	}

	public List<VendorResource> getAllFallback(VendorRequestParams params) {
		return new ArrayList<>();
	}

	@PostMapping
	public String create(@RequestBody VendorResource obj) {
		return dao.create(obj);
	}

	@PutMapping("/{key}")
	public void put(@PathVariable String key, @RequestBody VendorResource obj) {
		dao.put(key, obj);
	}

	@DeleteMapping("/{key}")
	public void delete(@PathVariable String key) {
		dao.delete(key);
	}

}