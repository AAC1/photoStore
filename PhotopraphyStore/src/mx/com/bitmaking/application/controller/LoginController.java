package mx.com.bitmaking.application.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.dto.ResponseDTO;
import mx.com.bitmaking.application.service.ILoginService;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class LoginController {
	@FXML
	private ImageView imgLogo;
	@FXML
	private JFXButton btnLogin;
	@FXML 
	private JFXTextField inputUsr;
	@FXML 
	private JFXTextField inputPasswd;
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	private ILoginService loginService;
	
	private Stage mainStage;
	
	
	/**
	 * @return the mainStage
	 */
	public Stage getMainStage() {
		return mainStage;
	}

	/**
	 * @param mainStage the mainStage to set
	 */
	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	private void initialize() {
		initForm();
	}

	private void initForm() {

		inputUsr.setText("");
		inputPasswd.setText("");
		
		File file = new File("/mx/com/bitmaking/application/assets/macrofoto_logo.jpg");
		Image image = new Image(file.toURI().toString(),300,200,false,false);
		imgLogo.setFitHeight(200);
		imgLogo.setFitWidth(300);
		imgLogo.setPreserveRatio(false);
		imgLogo.setImage(image);
	}
	
	@FXML
	private void validLogin(){
		if(true){
			openModal("Home",true);
			return;
		}
		ResponseDTO resp = loginService.validUsr(inputUsr.getText(), inputPasswd.getText());
		if("ERROR".equals(resp.getEstado())){
			if(resp.getMsg().length()>0){
				GeneralMethods.modalMsg("ERROR", "", resp.getMsg());
			}else{
				GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error en la validaci\u00F3n de usuario");
			}
		}
		else if(resp.isValid()){
			if(mainStage!=null)mainStage.close();
			openModal("Home",true);
		}
	}

	private void openModal(String sectName,boolean hasCss) {
		try {
			FXMLLoader loader = //storeApp.initializeFXML("view/"+scene+".fxml");
			        		new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/"+sectName+".fxml"));
			        loader.setControllerFactory(context::getBean);
			        Parent sceneHome = loader.load();
			        if(hasCss) 
			        	sceneHome.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
			        
			        	Scene scene = new Scene(sceneHome,22,119);
			        	Stage primaryStage =new Stage();
			        	primaryStage.initModality(Modality.APPLICATION_MODAL); //Evitar que otras ventanas se puedan modificar
			        	primaryStage.setScene(scene);
						primaryStage.centerOnScreen();
				        primaryStage.setTitle("Control de acceso");
						primaryStage.setMinHeight(636.0);
						primaryStage.setMinWidth(865.0);
					//	primaryStage.setMaxHeight(636.0);
					//	primaryStage.setMaxWidth(865.0);
						primaryStage.show();
					
			       
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			

}

}
