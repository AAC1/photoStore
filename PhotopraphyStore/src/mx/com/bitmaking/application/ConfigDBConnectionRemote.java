package mx.com.bitmaking.application;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("!test")      
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "mx.com.bitmaking.application.remote",
	entityManagerFactoryRef = "remoteEntityManager", transactionManagerRef = "remoteTransactionManager") 
public class ConfigDBConnectionRemote {
	
	@Bean(name = "remoteDataSource")  
    @ConfigurationProperties(prefix = "spring.datasource-remote")
    public DataSource mysqlDataSource() {
         return DataSourceBuilder.create().build();
    }
    
    @PersistenceContext(unitName = "remote")  
    @Bean(name = "remoteEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
         return builder.dataSource(mysqlDataSource()).persistenceUnit("remote").properties(jpaProperties())
                   .packages("mx.com.bitmaking.application.remote").build();
     }
  /* @Bean(name = "remoteTransactionManager")
   public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
         JpaTransactionManager tm = new JpaTransactionManager();
         tm.setEntityManagerFactory(entityManagerFactory(builder).getObject());
         tm.setDataSource(mysqlDataSource());
         return tm;
   }*/
   @Bean("remoteTransactionManager")
  	public JpaTransactionManager remoteTransactionManager(EntityManagerFactoryBuilder builder) {
  		JpaTransactionManager retVal = new JpaTransactionManager();
  		retVal.setEntityManagerFactory(entityManagerFactory(builder).getObject());
  		return retVal;
  	}
    private Map<String, Object> jpaProperties() {
         Map<String, Object> props = new HashMap<>();
         props.put("org.hibernate.dialect.MySQLDialect", new SpringNamingStrategy());
         return props;
     }

}
