package mx.com.bitmaking.application;


import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main extends MystoreApplication {
    
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader jfxLoader = initializeFXML("view/Home.fxml");
			
			Parent root = jfxLoader.load();
			Scene scene = new Scene(root,865,536);
			//scene.getStylesheets().add("");
			scene.getStylesheets().add(getClass().getResource("assets/css/application.css").toString());

			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
	        primaryStage.setTitle("Macrofoto");
			primaryStage.setMinHeight(636.0);
			primaryStage.setMinWidth(865.0);
			//primaryStage.setMaxHeight(450.0);
			//primaryStage.setMaxWidth(620.0);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 
	public static void main(String[] args) {
		launch(args);
	}
}
