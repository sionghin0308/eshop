package com.application.eshop.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.eshop.entity.postgres.OrderList;
import com.application.eshop.respository.postgres.OrderListDao;

@Service("orderListService")
public class OrderListService {

	@Autowired
	private OrderListDao orderListDao;

    public List<OrderList> getOrderList() {
    	return orderListDao.getOrderList();
    }
    
    public List<OrderList> getCustomerOrderList(BigDecimal customerId, String orderStatus) {
    	return orderListDao.getCustomerOrderList(customerId, orderStatus);
    }
    
    public OrderList getOrderDetail(String orderId, String productId, String orderStatus) {
    	return orderListDao.getOrderDetail(orderId, productId, orderStatus);
    }
    
    public OrderList insertOrderList(OrderList orderList) {
    	return orderListDao.insertOrderList(orderList);
    }
    
    public OrderList updateOrderList(OrderList orderList) {
    	return orderListDao.updateOrderList(orderList);
    }
    
    public OrderList deleteOrderList(OrderList orderList) {
    	return orderListDao.deleteOrderList(orderList);
    }
    
}
