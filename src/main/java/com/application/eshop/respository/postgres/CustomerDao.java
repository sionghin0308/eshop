package com.application.eshop.respository.postgres;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.application.eshop.common.CommonConstant;
import com.application.eshop.entity.postgres.Customer;


@Repository
@Transactional(value = "transactionManager")
public class CustomerDao {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Get Customer List from table Customer
	 * 
	 * @return Customer List
	 */
	public List<Customer> getCustomerList() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from Customer";
		Query query = session.createQuery(sql, Customer.class);
	
		List<Customer> results = query.getResultList();
		
		if(!CollectionUtils.isEmpty(results)){
			return results;
		} else {
			return null;
		}
	}
	
	/**
	 * Get Customer Detail from table Customer
	 * 
	 * @return Customer Detail
	 */
	public Customer getCustomerDetail(String customerUsername) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from Customer where customerUsername = :customerUsername";
		Query query = session.createQuery(sql, Customer.class);
		query.setParameter("customerUsername", customerUsername);
		
		Customer customerDetail = new Customer();
		if(!CollectionUtils.isEmpty(query.getResultList())){
			customerDetail = (Customer) query.getResultList().get(0);
			return customerDetail;
		} else {
			return null;
		}
	}
	
	/**
	 * Get Customer Login from table Customer
	 * 
	 * @return Customer Detail
	 */
	public Customer getCustomerLogin(String customerUsername, String customerPassword) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from Customer where customerUsername = :customerUsername and customerPassword = :customerPassword";
		Query query = session.createQuery(sql, Customer.class);
		query.setParameter("customerUsername", customerUsername);
		query.setParameter("customerPassword", customerPassword);
		
		Customer customerDetail = new Customer();
		if(!CollectionUtils.isEmpty(query.getResultList())){
			customerDetail = (Customer) query.getResultList().get(0);
			return customerDetail;
		} else {
			return null;
		}
	}
	
	/**
	 * Insert Customer to table Customer
	 * 
	 * @return Customer
	 */
	public Customer insertCustomer(Customer customer) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.save(customer);
        	customer.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            customer.setMessage(CommonConstant.FAILED);
        }
        return customer;
	}
	
	/**
	 * Update Customer Detail to table Customer
	 * 
	 * @return Customer
	 */
	public Customer updateCustomer(Customer customer) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.update(customer);
        	customer.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            customer.setMessage(CommonConstant.FAILED);
        }
        return customer;
	}
	
	/**
	 * Delete Customer from table Customer
	 * 
	 * @return Customer
	 */
	public Customer deleteCustomer(Customer customer) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.remove(customer);
        	customer.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            customer.setMessage(CommonConstant.FAILED);
        }
        return customer;
	}

}
