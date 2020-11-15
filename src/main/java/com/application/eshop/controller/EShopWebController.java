/*package com.application.eshop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.application.eshop.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/page")
public class EShopWebController {
	
	@Autowired
	private ProductService userInformationService;
	
	@GetMapping(value="/login")
	public ModelAndView getLoginPage(HttpServletRequest request) {
		String page = "login";
		ModelAndView mv = new ModelAndView(page);
		return mv;
	}
	
	@GetMapping(value="/home")
	public ModelAndView getHomePage(HttpServletRequest request) {
		String page = "index";
		ModelAndView mv = new ModelAndView(page);
		return mv;
	}
	
	@GetMapping(value="/userHome")
	public ModelAndView getUserHomePage(HttpServletRequest request) {
		String page = "main";
		ModelAndView mv = new ModelAndView(page);
		return mv;
	}
	
}*/
