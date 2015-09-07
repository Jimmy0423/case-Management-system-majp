package se.majp.caseManagement.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("se.majp.caseManagement.repository")
@EnableTransactionManagement
public class InfrastructureConfig
{
	@Bean
	public DataSource dataSource()
	{
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost:3307/majp");
		config.setUsername("root");
		config.setPassword("");

		return new HikariDataSource(config);
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory factory)
	{
		return new JpaTransactionManager(factory);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter()
	{
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);

		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
	{
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.setJpaProperties(additionalProperties());
		factory.setPackagesToScan("se.majp.caseManagement.model");

		return factory;
	}

	private Properties additionalProperties()
	{
		Properties properties = new Properties();
		properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
		return properties;
	}
}
