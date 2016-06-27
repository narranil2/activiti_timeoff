package com.activiti.extension.conf;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.activiti.TimeTrackerApp")
@EnableJpaRepositories(basePackages = "com.activiti.TimeTrackerApp.bean",
entityManagerFactoryRef = "timeOffEntityManagerFactory",
transactionManagerRef = "timeOffTransactionManager")
public class CustomBeanConfiguration {

	final static Logger logger = Logger.getLogger(CustomBeanConfiguration.class);

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "timeoffApp.hibernate.dialect";

	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "timeoffApp.jpa.show-sql";

	@Autowired
	private Environment env;

	@Bean(name = "timeOffDataSource")
	public DataSource timeOffDataSource() {
		logger.info("Configuring mysql Datasource");

		String dataSourceDriver = env.getProperty("timeoffApp.datasource.driver", "com.mysql.jdbc.Driver");
		String dataSourceUrl = env.getProperty("timeoffApp.datasource.url", "jdbc:mysql://127.0.0.1:3306/activiti?characterEncoding=UTF-8");

		String dataSourceUsername = env.getProperty("timeoffApp.datasource.username", "root");
		String dataSourcePassword = env.getProperty("timeoffApp.datasource.password", "root");

		/* Integer minPoolSize = env.getProperty("timeoffApp.datasource.min-pool-size", Integer.class);
    if (minPoolSize == null) {
      minPoolSize = 10;
    }

    Integer maxPoolSize = env.getProperty("timeoffApp.datasource.max-pool-size", Integer.class);
    if (maxPoolSize == null) {
      maxPoolSize = 100;
    }*/

		/*
    Integer acquireIncrement = env.getProperty("timeoffApp.datasource.acquire-increment", Integer.class);
    if (acquireIncrement == null) {
      acquireIncrement = 5;
    }

    String preferredTestQuery = env.getProperty("timeoffApp.datasource.preferred-test-query");

    Boolean testConnectionOnCheckin = env.getProperty("ness.datasource.test-connection-on-checkin", Boolean.class);
    if (testConnectionOnCheckin == null) {
      testConnectionOnCheckin = true;
    }

    Boolean testConnectionOnCheckOut = env.getProperty("timeoffApp.datasource.test-connection-on-checkout", Boolean.class);
    if (testConnectionOnCheckOut == null) {
      testConnectionOnCheckOut = true;
    }

    Integer maxIdleTime = env.getProperty("timeoffApp.datasource.max-idle-time", Integer.class);
    if (maxIdleTime == null) {
      maxIdleTime = 1800;
    }

    Integer maxIdleTimeExcessConnections = env.getProperty("timeoffApp.datasource.max-idle-time-excess-connections", Integer.class);
    if (maxIdleTimeExcessConnections == null) {
      maxIdleTimeExcessConnections = 1800;
    }*/
		ComboPooledDataSource ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(dataSourceDriver);
		} catch (PropertyVetoException e) {
			return null;
		}

		// Connection settings
		ds.setJdbcUrl(dataSourceUrl);
		ds.setUser(dataSourceUsername);
		ds.setPassword(dataSourcePassword);

		// Pool config: see http://www.mchange.com/projects/c3p0/#configuration
		ds.setMinPoolSize(10);
		ds.setMaxPoolSize(100);
		ds.setAcquireIncrement(5);
		/*if (preferredTestQuery != null) {
      ds.setPreferredTestQuery(preferredTestQuery);
    }*/
		ds.setTestConnectionOnCheckin(true);
		ds.setTestConnectionOnCheckout(true);
		//ds.setMaxIdleTimeExcessConnections(maxIdleTimeExcessConnections);
		ds.setMaxIdleTime(1800);

		return ds;
	}

	@SuppressWarnings("deprecation")
	@Bean(name = "timeOffEntityManagerFactory")
	public EntityManagerFactory timeOffEntityManagerFactory() {
		logger.info("Configuring EntityManager");
		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setPersistenceProvider(new HibernatePersistence());
		lcemfb.setPersistenceUnitName("nessPersistenceUnit");
		lcemfb.setDataSource(timeOffDataSource());
		lcemfb.setJpaDialect(new HibernateJpaDialect());
		lcemfb.setJpaVendorAdapter(jpaVendorAdapter());

		Properties jpaProperties = new Properties();
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		jpaProperties.put("timeoffApp.hibernate.cache.use_second_level_cache", false);
		jpaProperties.put("timeoffApp.hibernate.generate_statistics", env.getProperty("ness.hibernate.generate_statistics", Boolean.class, false));
		lcemfb.setJpaProperties(jpaProperties);

		lcemfb.setPackagesToScan("com.activiti.TimeTrackerApp.domain");
		lcemfb.afterPropertiesSet();
		return lcemfb.getObject();
	}

	@Bean(name = "timeOffJpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(env.getProperty("timeoffApp.jpa.show-sql", Boolean.class, false));
		jpaVendorAdapter.setDatabasePlatform(env.getProperty("timeoffApp.hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"));
		return jpaVendorAdapter;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean(name = "timeOffTransactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(timeOffEntityManagerFactory());
		return jpaTransactionManager;
	}

	@Bean(name = "timeOffJdbcTemplate")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(timeOffDataSource());
	}

	@Bean(name = "timeOffTransactionTemplate")
	public TransactionTemplate transactionTemplate() {
		return new TransactionTemplate(annotationDrivenTransactionManager());
	}

}
