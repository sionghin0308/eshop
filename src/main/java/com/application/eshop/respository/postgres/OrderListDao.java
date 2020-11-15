package com.application.eshop.respository.postgres;

import java.math.BigDecimal;
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
import com.application.eshop.entity.postgres.OrderList;


@Repository
@Transactional(value = "transactionManager")
public class OrderListDao {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Get Order List from table Order List
	 * 
	 * @return Order List
	 */
	public List<OrderList> getOrderList() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from OrderList order by orderId";
		Query query = session.createQuery(sql, OrderList.class);
	
		List<OrderList> results = query.getResultList();
		
		if(!CollectionUtils.isEmpty(results)){
			return results;
		} else {
			return null;
		}
	}
	
	/**
	 * Get Order List from table Order List
	 * 
	 * @return Order List
	 */
	public List<OrderList> getCustomerOrderList(BigDecimal customerId, String orderStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from OrderList order where customerId = :customerId and orderStatus = :orderStatus";
		Query query = session.createQuery(sql, OrderList.class);
		query.setParameter("customerId", customerId);
		query.setParameter("orderStatus", orderStatus);
	
		List<OrderList> results = query.getResultList();
		
		if(!CollectionUtils.isEmpty(results)){
			return results;
		} else {
			return null;
		}
	}
	
	/**
	 * Get Order Detail from table Order List
	 * 
	 * @return Order Detail
	 */
	public OrderList getOrderDetail(String orderId, String productId, String orderStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from OrderList where orderId = :orderId and productId = :productId and orderStatus = :orderStatus";
		Query query = session.createQuery(sql, OrderList.class);
		query.setParameter("orderId", orderId);
		query.setParameter("productId", productId);
		query.setParameter("orderStatus", orderStatus);
		
		OrderList orderDetail = new OrderList();
		if(!CollectionUtils.isEmpty(query.getResultList())){
			orderDetail = (OrderList) query.getResultList().get(0);
			return orderDetail;
		} else {
			return null;
		}
	}
	
	/**
	 * Insert Order List to table Order List
	 * 
	 * @return OrderList
	 */
	public OrderList insertOrderList(OrderList orderList) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.save(orderList);
        	orderList.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            orderList.setMessage(CommonConstant.FAILED);
        }
        return orderList;
	}
	
	/**
	 * Update Order List to table Order List
	 * 
	 * @return OrderList
	 */
	public OrderList updateOrderList(OrderList orderList) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.update(orderList);
        	orderList.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            orderList.setMessage(CommonConstant.FAILED);
        }
        return orderList;
	}
	
	/**
	 * Delete Order List to table Order List
	 * 
	 * @return OrderList
	 */
	public OrderList deleteOrderList(OrderList orderList) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.delete(orderList);
        	orderList.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            orderList.setMessage(CommonConstant.FAILED);
        }
        return orderList;
	}

}
