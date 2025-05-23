package com.asd.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ApiController {
	
	@Value("${config.custom.message}")
	private String message;
	
	@GetMapping("/hello")
	public String hello() {
		return message;
	}
}
