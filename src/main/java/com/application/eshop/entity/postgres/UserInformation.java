package com.application.eshop.entity.postgres;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the USER database table.
 * 
 */
@Entity
@Table(name="UserInformation")
@Getter
@Setter
public class UserInformation {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="UserID")
	private String userId;
	
	@Column(name="Username")
	private String username;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="LastName")
	private String lastName;
	
	@Column(name="FirstName")
	private String firstName;
	
	@Column(name="Address")
	private String address;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="ContactNo")
	private String contactNo;
	
	@Column(name="Roles")
	private String roles;

}
