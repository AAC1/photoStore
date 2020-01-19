package mx.com.bitmaking.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "mx.com.bitmaking.application.repository")
@ComponentScan(basePackages = {"mx.com.bitmaking.application.service",
		"mx.com.bitmaking.application.controller","mx.com.bitmaking.application.entity"})
public class Main extends MystoreApplication {
    
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader jfxLoader = initializeFXML("view/home/Home.fxml");
			
			Parent root = jfxLoader.load();
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
