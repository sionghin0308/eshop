package com.application.eshop.entity.postgres;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name="product")
@Getter
@Setter
@Data
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_id")
	private String productId;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="product_price")
	private BigDecimal productPrice;
	
	@Column(name="product_quantity")
	private BigDecimal productQuantity;
	
	@Transient
	private String message;

}
