package com.application.eshop.controller;

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

import com.application.eshop.entity.postgres.UserInformation;
import com.application.eshop.services.UserInformationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/page")
public class EShopController {
	
	@Autowired
	private UserInformationService userInformationService;
	
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
	
	@PostMapping(value="/login", consumes="application/json", produces="application/json")
	public @ResponseBody String getUserLogin(@RequestBody UserInformation userLoginDetail,
			HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		
		Map<String, String> resMap = new HashMap<String,String>();
		
		UserInformation userDetail = new UserInformation();
		userDetail = userInformationService.getUserDetailByLogin(userLoginDetail.getUsername(), userLoginDetail.getPassword());
		
		if(userDetail != null) {
			Cookie cookie = new Cookie("username", userDetail.getUsername());
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
			resMap.put("success", "User " + userDetail.getUsername() + " login successful!");
		} else {
			resMap.put("failure", "User " + userDetail.getUsername() + " fail to sign in!");
		}
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsString(resMap);
	}
	
}
