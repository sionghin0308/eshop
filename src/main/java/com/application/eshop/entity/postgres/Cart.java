package com.application.eshop.entity.postgres;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String productId;
	
	private BigDecimal orderQuantity;
	
	public Cart() {
		
	}
	
	public Cart(OrderList orderList){
		this.productId = orderList.getProductId();
		this.orderQuantity = orderList.getOrderQuantity();
	}
}

