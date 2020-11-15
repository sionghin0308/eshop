package com.application.eshop.configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class PostgresDatabaseConfiguration {

	@Autowired
	private Environment environment;

	@Bean(name = "dataSource")
	@Primary
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// Refer application.properties
		dataSource.setDriverClassName(environment.getProperty("spring.postgres-db.driverClassName"));
		dataSource.setUrl(environment.getProperty("spring.postgres-db.url"));
		dataSource.setUsername(environment.getProperty("spring.postgres-db.username"));
		dataSource.setPassword(environment.getProperty("spring.postgres-db.password"));
		return dataSource;
	}

	@Autowired
	@Primary
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));
		properties.put("current_session_context_class", //
				environment.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));

		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		// Package contain entity classes
		factoryBean.setPackagesToScan(new String[] { "com.application.eshop.entity.postgres" });
		factoryBean.setDataSource(dataSource);
		factoryBean.setHibernateProperties(properties);
		factoryBean.afterPropertiesSet();
		//
		SessionFactory sf = factoryBean.getObject();
		return sf;
	}

	@Autowired
	@Primary
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
}
