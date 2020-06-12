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

	private final static Logger logger = LoggerFactory.getLogger(UserInformationDao.class);

	/**
	 * Get User Information List from table User
	 * 
	 * @return User Information List
	 */
	public List<UserInformation> getUserInformationList() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "SELECT USERNAME, PASSWORD FROM USERINFORMATION";
		Query query = session.createSQLQuery(sql);

		List<Object[]> result = query.getResultList();
		
		if(!CollectionUtils.isEmpty(result)) {
			return result.stream() .map(a->{
				UserInformation userInformation = new UserInformation();
				userInformation.setName((String) a[0]);
				userInformation.setPassword((String) a[1]);
				return userInformation;
			}).collect(Collectors.toList());
		} else { 
			return null;
		}
	}

}
