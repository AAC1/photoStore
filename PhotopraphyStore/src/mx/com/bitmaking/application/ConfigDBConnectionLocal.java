package mx.com.bitmaking.application;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javafx.application.Application;

@Profile("!test")      
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "mx.com.bitmaking.application.local",
	entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager") 
public class ConfigDBConnectionLocal {
//	@Autowired
  //  private EntityManagerFactory entityManagerFactory;

    @Bean(name = "dataSource")  
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysqlDataSource() {
         return DataSourceBuilder.create().build();
    }
    
    @PersistenceContext(unitName = "primary")  
    @Primary
    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
         return builder.dataSource(mysqlDataSource()).persistenceUnit("primary").properties(jpaProperties())
                   .packages("mx.com.bitmaking.application.local").build();
    }
   /*
   @Bean
   public PlatformTransactionManager transactionManager(){
       DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(mysqlDataSource());
       return transactionManager;
   }*/
   @Bean("transactionManager")
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
