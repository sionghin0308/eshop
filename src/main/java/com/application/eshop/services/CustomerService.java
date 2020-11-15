package com.application.eshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.eshop.entity.postgres.Customer;
import com.application.eshop.respository.postgres.CustomerDao;

@Service("customerService")
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

    public List<Customer> getCustomerList() {
    	return customerDao.getCustomerList();
    }
    
    public Customer getCustomerDetail(String customerUsername) {
    	return customerDao.getCustomerDetail(customerUsername);
    }
    
    public Customer getCustomerLogin(String customerUsername, String password) {
    	return customerDao.getCustomerLogin(customerUsername, password);
    }
    
    public Customer insertCustomer(Customer customer) {
    	return customerDao.insertCustomer(customer);
    }
    
    public Customer updateCustomer(Customer customer) {
    	return customerDao.updateCustomer(customer);
    }
    
    public Customer deleteCustomer(Customer customer) {
    	return customerDao.deleteCustomer(customer);
    }
    
}
