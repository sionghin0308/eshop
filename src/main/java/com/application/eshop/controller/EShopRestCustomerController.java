package com.application.eshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.eshop.common.CommonConstant;
import com.application.eshop.common.CommonConstant.CommonMessage;
import com.application.eshop.entity.postgres.Customer;
import com.application.eshop.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/customer")
public class EShopRestCustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/list")
	@ApiOperation(value = "Get Customer List", notes = "Get Customer List")
	public @ResponseBody List<Customer> getCustomerList() {
		List<Customer> result = customerService.getCustomerList();
		if (!CollectionUtils.isEmpty(result)) {
			return result;
		} else {
			List<Customer> emptyList = new ArrayList<>();
			Customer empty = new Customer();
			empty.setMessage(CommonMessage.CUSTOMER_LIST_EMPTY.message);
			emptyList.add(empty);
			return emptyList;
		}
	}

	@GetMapping(value = "/detail")
	@ApiOperation(value = "Get Customer Detail", notes = "Get Customer Detail")
	public @ResponseBody Customer getCustomerDetail(@RequestParam(value = "customerUsername") String customerUsername) {
		Customer result = customerService.getCustomerDetail(customerUsername);
		if (result != null) {
			result.setMessage(CommonMessage.CUSTOMER_FOUND.message);
			return result;
		} else {
			Customer empty = new Customer();
			empty.setMessage(CommonMessage.CUSTOMER_NOTFOUND.message);
			return empty;
		}
	}

	@PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Register User", notes = "Insert Customer")
	public @ResponseBody Customer insertCustomer(@RequestParam(value = "customerUsername") String customerUsername,
			@RequestBody Customer customer, HttpServletRequest request) throws JsonProcessingException {
		customer.setCustomerUsername(customerUsername);

		Customer result = customerService.getCustomerDetail(customerUsername);

		if (result != null) {
			customer.setMessage(CommonMessage.CUSTOMER_EXIST.message);
		} else {
			customer = customerService.insertCustomer(customer);
			if (customer.getMessage().equals(CommonConstant.SUCCESS)) {
				customer.setMessage(CommonMessage.CUSTOMER_ADD_SUCCESS.message);
			} else {
				customer.setMessage(CommonMessage.CUSTOMER_ADD_FAIL.message);
			}
		}
		return customer;
	}

	@PostMapping(value = "/change", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Edit User Detail", notes = "Update Customer")
	public @ResponseBody Customer updateCustomer(@RequestParam(value = "customerUsername") String customerUsername,
			@RequestBody Customer customer, HttpServletRequest request) throws JsonProcessingException {
		customer.setCustomerUsername(customerUsername);

		Customer result = customerService.getCustomerDetail(customerUsername);

		if (result != null) {
			customer.setCustomerId(result.getCustomerId());
			customer = customerService.updateCustomer(customer);
			if (customer.getMessage().equals(CommonConstant.SUCCESS)) {
				customer.setMessage(CommonMessage.CUSTOMER_UPDATE_SUCCESS.message);
			} else {
				customer.setMessage(CommonMessage.CUSTOMER_UPDATE_FAIL.message);
			}
		} else {
			customer.setMessage(CommonMessage.CUSTOMER_NOTEXIST.message);
		}
		return customer;
	}

	@PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Delete User", notes = "Delete Customer")
	public @ResponseBody Customer deleteCustomer(@RequestParam(value = "customerUsername") String customerUsername,
			@RequestBody Customer customer, HttpServletRequest request) throws JsonProcessingException {
		customer.setCustomerUsername(customerUsername);

		Customer result = customerService.getCustomerDetail(customerUsername);

		if (result != null) {
			customer.setCustomerId(result.getCustomerId());
			customer = customerService.deleteCustomer(customer);
			if (customer.getMessage().equals(CommonConstant.SUCCESS)) {
				customer.setMessage(CommonMessage.CUSTOMER_DELETE_SUCCESS.message);
			} else {
				customer.setMessage(CommonMessage.CUSTOMER_DELETE_FAIL.message);
			}
		} else {
			customer.setMessage(CommonMessage.CUSTOMER_NOTEXIST.message);
		}
		return customer;
	}

	@GetMapping(value = "/login")
	@ApiOperation(value = "Get Customer Login", notes = "Get Customer Login")
	public @ResponseBody Customer getCustomerLogin(@RequestParam(value = "customerUsername") String customerUsername,
			@RequestParam(value = "password") String password, HttpServletRequest request, HttpServletResponse response) {
		Customer result = customerService.getCustomerDetail(customerUsername);
		if (result != null) {
			result.setMessage(CommonMessage.CUSTOMER_FOUND.message);
			Customer resultLogin = customerService.getCustomerLogin(customerUsername, password);
			if (resultLogin != null) {
				resultLogin.setMessage(CommonMessage.CUSTOMER_LOGIN_SUCCESS.message);
				request.getSession().setAttribute("userId", resultLogin.getCustomerId());
				return resultLogin;
			} else {
				Customer resultLoginFail = new Customer();
				resultLoginFail.setMessage(CommonMessage.CUSTOMER_LOGIN_FAIL.message);
				return resultLoginFail;
			}
		} else {
			Customer empty = new Customer();
			empty.setMessage(CommonMessage.CUSTOMER_NOTFOUND.message);
			return empty;
		}
	}
	
	@GetMapping(value = "/logout")
	@ApiOperation(value = "Get Customer Logout", notes = "Get Customer Logout")
	public @ResponseBody String getCustomerLogin(HttpServletRequest request, HttpServletResponse response) 
			throws JsonProcessingException {
		request.getSession().removeAttribute("userId");
		Map<String, String> resMap = new HashMap<String,String>();
		resMap.put(CommonConstant.SUCCESS, CommonMessage.ORDER_LOGOUT.message);
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsString(resMap);
	}

}
