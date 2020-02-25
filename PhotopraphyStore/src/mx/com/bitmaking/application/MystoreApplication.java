package mx.com.bitmaking.application;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


@SpringBootApplication
public abstract class MystoreApplication extends Application{
	
	
    @Override
	public void start(Stage primaryStage) throws Exception {
	}
   // private ConfigurableApplicationContext context;
    
    @Autowired
    private ApplicationContext context ;
	
	
    
	@Override
	public void init() {
	//	context =SpringApplication.run(getClass());
	//	context.getAutowireCapableBeanFactory().autowireBean(this);
	}
	
    public FXMLLoader initializeFXML(String source) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(source));
      
        context =SpringApplication.run(getClass());
		context.getAutowireCapableBeanFactory().autowireBean(this);
        loader.setControllerFactory(context::getBean);
	//	loader.setController(menuCtrl);
        return loader;
    }
	
   // @Autowired
   // private EntityManagerFactory entityManagerFactory;
    @PersistenceContext(unitName = "primary")
	//@Qualifier("remoteEntityManager")
	private EntityManager entityManager;
    
    
    @Bean("sessionFactory")
    public SessionFactory getSessionFactory() {
        if (entityManager.getEntityManagerFactory().unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
    }
    
  
    @PersistenceContext(unitName = "remote")
	//@Qualifier("remoteEntityManager")
	private EntityManager remoteEntityManager;
    
    @Bean("remoteSessionFactory")
    public SessionFactory getRemoteSessionFactory() {
        if (remoteEntityManager.getEntityManagerFactory().unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return remoteEntityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
    }
    
   
    
}
