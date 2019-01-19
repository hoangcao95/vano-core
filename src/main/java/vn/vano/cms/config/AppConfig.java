package vn.vano.cms.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.yotel.commons.context.AppContext;
import vn.yotel.commons.util.PasswordUtil;

import javax.sql.DataSource;

@Configuration
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = { "vn.yotel", "vn.vano" })
@ImportResource(value = { "classpath:applicationContext-webembedded.xml" })
//@Profile("HRYotel")
public class AppConfig {

	/**
	 * JDBC properties
	 */
	@Value("${jdbc.driverClassName}")
	private String jdbcDriverClassName;

	@Value("${jdbc.url}")
	private String jdbcUrl;

	@Value("${jdbc.username}")
	private String jdbcUsername;

	@Value("${jdbc.password}")
	private String jdbcPassword;

	@Value("${jndi.datasource}")
	private String jndiDatasource;

	@Value("${jdbc.max-total-connection}")
	private int jdbcMaxTotalConnection;

	@Value("${jdbc.max-idle-connection}")
	private int jdbcMaxIdleConnection;

	@Value("${jdbc.max-init-connection}")
	private int jdbcInitConnection;

	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(this.jdbcDriverClassName);
		basicDataSource.setUrl(this.jdbcUrl);
//		basicDataSource.setUsername(PasswordUtil.decryptPropertyValue(this.jdbcUsername));
//		basicDataSource.setPassword(PasswordUtil.decryptPropertyValue(this.jdbcPassword));
		basicDataSource.setUsername(this.jdbcUsername);
		basicDataSource.setPassword(this.jdbcPassword);
		basicDataSource.setInitialSize(this.jdbcInitConnection);
		basicDataSource.setMaxIdle(this.jdbcMaxIdleConnection);
		basicDataSource.setMaxTotal(this.jdbcMaxTotalConnection);
		return basicDataSource;
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Primary
	public AppContext appContext() {
		return new AppContext();
	}
	
	@Bean
	@Primary
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
