package com.application.fruitaddict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/page")
public class WebPageController {
	
	@GetMapping(value = "/main")
	public String getMainPage() {
		return "main";
	}
	
}
