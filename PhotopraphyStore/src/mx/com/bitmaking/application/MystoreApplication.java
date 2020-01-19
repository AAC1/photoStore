package mx.com.bitmaking.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


@SpringBootApplication
public abstract class MystoreApplication extends Application{

    @Override
	public void start(Stage primaryStage) throws Exception {
	}
    private ConfigurableApplicationContext context;
    
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
	
	
    @Override
    public void stop() throws Exception {
        context.close();
    }
	
   
    protected static void launchApp(Class<? extends MystoreApplication> clazz, String[] args) {
    //	MystoreApplication.savedArgs = args;
        Application.launch(clazz, args);
    }
    
}
