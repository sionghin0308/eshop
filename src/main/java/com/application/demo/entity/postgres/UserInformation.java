package com.application.demo.entity.postgres;

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
@Table(name="USERINFORMATION")
@Getter
@Setter
public class UserInformation {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Transient
	private String name;

}
