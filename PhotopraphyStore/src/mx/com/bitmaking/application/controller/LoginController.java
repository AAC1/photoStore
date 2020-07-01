package mx.com.bitmaking.application.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.dto.ResponseDTO;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.iservice.ILoginService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;
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
	private JFXPasswordField inputPasswd;
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	@Qualifier("LoginService")
	private ILoginService loginService;
	
	@Autowired
	@Qualifier("remoteLoginService")
	private ILoginService remoteLoginService;
	
	private Stage mainStage;
	private Stage homeStage;
	
	
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
	private void login(KeyEvent e) {
	//	System.out.println("entra:"+e.getCode().toString());
		if("ENTER".equals(e.getCode().toString()) ){
			enterLogin();
		}
	}
	
	@FXML
	private void validLogin(){
		enterLogin();
	}
	
	public void enterLogin() {
		/*if(true){
		openModal("Home",true);
		return;
		}*/
		//Flags.remote_valid=true;//Valor inicial para incluir login remoto
		Flags.remote_valid=false;
		try {
		ResponseDTO resp = null;
		String passwd = GeneralMethods.cifraSha256(inputPasswd.getText(), Constantes.SALT);
		//System.out.println("passwd:"+passwd);
		//	try {
				resp = loginService.validUsr(inputUsr.getText(), passwd);
						// remoteLoginService.validUsr(inputUsr.getText(), inputPasswd.getText());
		/*	}
			catch(Exception e) {
				Flags.remote_valid = false;
				System.out.println("Logueo local, no hay conexion remoto");
				resp =loginService.validUsr(inputUsr.getText(), inputPasswd.getText());
			}*/
			if(resp==null) {
				GeneralMethods.modalMsg("ERROR", "", "No fue posible validar acceso");
				return ;
			}
		//	ResponseDTO resp =remoteLoginService.validUsr(inputUsr.getText(), inputPasswd.getText());
			if("ERROR".equals(resp.getEstado())){
				if(resp.getMsg().length()>0){
					GeneralMethods.modalMsg("ERROR", "", resp.getMsg());
				}else{
					GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error en la validaci\u00F3n de usuario");
				}
			}
			else if(resp.isValid()){
				if(mainStage!=null)mainStage.close();
				inputPasswd.setText("");
				openModal("Home",true);
				
			}
		}catch(Exception e) {
			GeneralMethods.modalMsg("ERROR", "", e.getMessage());
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
			        	homeStage =new Stage();
			        	homeStage.initModality(Modality.APPLICATION_MODAL); //Evitar que otras ventanas se puedan modificar
			        	homeStage.setScene(scene);
			        	homeStage.centerOnScreen();
			        	homeStage.setTitle("Control de acceso");
			        	homeStage.setMinHeight(636.0);
			        	homeStage.setMinWidth(865.0);
					//	primaryStage.setMaxHeight(636.0);
					//	primaryStage.setMaxWidth(865.0);
						HomeController ctrller = loader.getController();
						ctrller.getCloseSession().addEventHandler(MouseEvent.MOUSE_CLICKED,closeSession());
						
						homeStage.show();
					
			       
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			

	}
	
	public EventHandler<MouseEvent> closeSession(){
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					UserSessionDTO instance = UserSessionDTO.getInstance();
					instance.cleanUserSession();
					
					
					if(homeStage!=null)homeStage.close();
					/*
					FXMLLoader loader = //storeApp.initializeFXML("view/"+scene+".fxml");
			        		new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/Login.fxml"));
			        loader.setControllerFactory(context::getBean);
			        Parent sceneHome = loader.load();
			        sceneHome.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
			        
			        	Scene scene = new Scene(sceneHome,22,119);
			        	Stage primaryStage =new Stage();
			        	primaryStage.initModality(Modality.APPLICATION_MODAL); //Evitar que otras ventanas se puedan modificar
			        	primaryStage.setScene(scene);
						primaryStage.centerOnScreen();
				        primaryStage.setTitle("Control de acceso");
				        primaryStage.setMinHeight(400.0);
						primaryStage.setMinWidth(369.0);
						primaryStage.setMaxHeight(400.0);
						primaryStage.setMaxWidth(369.0);
						primaryStage.show();
					*/
					mainStage.show();
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
		
	}
	
}
