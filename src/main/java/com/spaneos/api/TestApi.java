package com.spaneos.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

	@GetMapping(value = "/test")
	public String testApi() {
		return "MultiTenant demo woring";
	}
}
