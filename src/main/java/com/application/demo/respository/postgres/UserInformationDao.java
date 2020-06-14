package com.application.demo.respository.postgres;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.application.demo.entity.postgres.UserInformation;


@Repository
@Transactional(value = "transactionManager")
public class UserInformationDao {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Get User Detail by Login from table User Information
	 * 
	 * @return User Information
	 */
	public UserInformation getUserDetailByLogin(String username, String password){
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from UserInformation where username=:username and password=:password";
		Query query = session.createQuery(sql, UserInformation.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
	
		UserInformation userDetail = new UserInformation();
		if(!CollectionUtils.isEmpty(query.getResultList())){
			userDetail = (UserInformation) query.getResultList().get(0);
			return userDetail;
		}else{
			return null;
		}
	}

}
