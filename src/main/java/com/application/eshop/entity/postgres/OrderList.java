package com.application.eshop.entity.postgres;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the Customer database table.
 * 
 */
@Entity
@Table(name="order_list")
@Getter
@Setter
@Data
public class OrderList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private BigDecimal Id;
	
	@Column(name="order_id")
	private String orderId;
	
	@Column(name="product_id")
	private String productId;
	
	@Column(name="customer_id")
	private BigDecimal customerId;
	
	@Column(name="order_time")
	private Timestamp orderTime;
	
	@Column(name="order_quantity")
	private BigDecimal orderQuantity;
	
	@Column(name="order_price")
	private BigDecimal orderPrice;
	
	@Column(name="order_status")
	private String orderStatus;	
	
	@Transient
	private String message;

}
