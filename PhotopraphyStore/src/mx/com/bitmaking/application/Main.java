package mx.com.bitmaking.application;


import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import mx.com.bitmaking.application.controller.LoginController;
import mx.com.bitmaking.application.controller.VentaController;
import javafx.scene.Parent;
import javafx.scene.Scene;

@Configuration
@EnableAutoConfiguration
@ComponentScan ("mx.com.bitmaking.application")
public class Main extends MystoreApplication {
	Logger logger = Logger.getLogger(Main.class);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader jfxLoader = initializeFXML("view/Login.fxml");
			
			Parent root = jfxLoader.load();
			LoginController ctrlObj = jfxLoader.getController();
			ctrlObj.setMainStage(primaryStage);
			Scene scene = new Scene(root,865,536);
			//scene.getStylesheets().add("");
			scene.getStylesheets().add(getClass().getResource("assets/css/Login.css").toString());

			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
	        primaryStage.setTitle("MACRO FOTO");
			primaryStage.setMinHeight(440.0);
			primaryStage.setMinWidth(369.0);
			primaryStage.setMaxHeight(440.0);
			primaryStage.setMaxWidth(369.0);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
	
	 
	public static void main(String[] args) {
		launch(args);
	}
}
