package mx.com.bitmaking.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"mx.com.bitmaking.application"})

public class Main extends Application {

    private ConfigurableApplicationContext context;
    
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader jfxLoader = new FXMLLoader(getClass().getResource("view/home/Home.fxml"));
			context =SpringApplication.run(getClass());
			context.getAutowireCapableBeanFactory().autowireBean(this);
			jfxLoader.setControllerFactory(context::getBean);
			
			Pane root = (Pane)jfxLoader.load();
			Scene scene = new Scene(root,865,536);
			//scene.getStylesheets().add("");
			scene.getStylesheets().add(getClass().getResource("assets/css/application.css").toString());

			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
	        primaryStage.setTitle("Macrofoto");
			primaryStage.setMinHeight(536.0);
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
