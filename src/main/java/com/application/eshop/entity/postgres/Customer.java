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

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the Customer database table.
 * 
 */
@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id")
	private BigDecimal customerId;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="customer_age")
	private BigDecimal customerAge;
	
	@Column(name="customer_username")
	private String customerUsername;
	
	@Column(name="customer_password")
	private String customerPassword;

	@Transient
	private String message;
}
