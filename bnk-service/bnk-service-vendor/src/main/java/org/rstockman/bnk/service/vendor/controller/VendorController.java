package org.rstockman.bnk.service.vendor.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.rstockman.bnk.common.dao.SimpleDAO;
import org.rstockman.bnk.common.exceptions.ExceptionFactory;
import org.rstockman.bnk.service.vendor.dto.VendorResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{id}")
    public VendorResource getVendor(@PathVariable String id) {
        return restTemplate.getForObject("http://bnk-api-vendor/vendors/" + id, VendorResource.class);
    }


}