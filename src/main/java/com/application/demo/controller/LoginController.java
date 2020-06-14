package com.application.demo.controller;

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

import com.application.demo.entity.postgres.UserInformation;
import com.application.demo.services.UserInformationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/log")
public class LoginController {
	
	/*
	 * @Autowired private UserInformationService userInformationService;
	 * 
	 * @PostMapping(value="/in", consumes="application/json",
	 * produces="application/json") public @ResponseBody String
	 * getUserLogin(@RequestBody UserInformation userLoginDetail, HttpServletRequest
	 * request, HttpServletResponse response) throws JsonProcessingException {
	 * 
	 * Map<String, String> resMap = new HashMap<String,String>();
	 * 
	 * UserInformation userDetail = new UserInformation(); userDetail =
	 * userInformationService.getUserDetailByLogin(userLoginDetail.getUsername(),
	 * userLoginDetail.getPassword());
	 * 
	 * if(userDetail != null) { Cookie cookie = new Cookie("username",
	 * userDetail.getUsername()); cookie.setMaxAge(60*60);
	 * response.addCookie(cookie); resMap.put("success", "User " +
	 * userDetail.getUsername() + " login successful!"); } else {
	 * resMap.put("failure", "User " + userDetail.getUsername() +
	 * " fail to sign in!"); } ObjectMapper objMapper = new ObjectMapper(); return
	 * objMapper.writeValueAsString(resMap); }
	 */
	
	public boolean isDemoUser(HttpServletRequest request){
		boolean isDemoUser = false;
		Cookie cookie[] = request.getCookies();
		if(cookie!=null){
			for(int i=0; i<cookie.length; i++){
				if(cookie[i].getName().equals("username")){
					isDemoUser = true;
					break;
				}
			}
		}
		return isDemoUser;
	}
	
	public UserInformation getUsername(HttpServletRequest request){
		UserInformation userInformation = new UserInformation();
		Cookie cookie[] = request.getCookies();
		if(cookie!=null){
			for(int i=0; i<cookie.length; i++){
				if(cookie[i].getName().equals("username")){
					userInformation.setUsername(cookie[i].getValue());
				}
			}
		}
		return userInformation;
	}
}
