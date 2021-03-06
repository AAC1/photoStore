package mx.com.bitmaking.application.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gluonhq.charm.glisten.control.CardPane;
import com.gluonhq.charm.glisten.control.ExpansionPanelContainer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.MystoreApplication;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.entity.Store_menu;

@Component
//@Scope("prototype")
public class HomeController {
	
	
	@FXML private Label titleHome;
	@FXML private Label itemAdmin;
	@FXML private Label lblGestionCat;
	@FXML private Label lblReportes;
	@FXML private Label itemVenta;
	@FXML private Label lblGestCostProd;
	@FXML private Label lblUsrSession;
	@FXML private JFXHamburger menuHamburger;
	@FXML private AnchorPane menuContainer;
	@FXML private AnchorPane bodyContainer;
	@FXML private AnchorPane backgroundOnMenu;
	@FXML private ImageView imgAvatar;
	@FXML private Label closeSession;
	
//	@Autowired
  //	private MystoreApplication storeApp;
	 @Autowired
	 private ApplicationContext context ;
	 UserSessionDTO instance = null;
	 
	 
	 
	 /**
	 * @return the closeSession
	 */
	public Label getCloseSession() {
		return closeSession;
	}


	public void initialize() {
		
		instance = UserSessionDTO.getInstance();
		if(instance!=null){
			System.out.println("HOME_UserSessionDTO [login=" + instance.getLogin() + ", nombre=" + instance.getNombre() + 
			", correo=" + instance.getCorreo() + ", telefono=" + instance.getTelefono()
			+ ", direccion=" + instance.getDireccion() + ", prefijo=" + instance.getPrefijo() + "]");
			
			lblUsrSession.setText(instance.getNombre());
			System.out.println("Home_lnList:"+instance.getMenuAccess().size());
			Parent parent = menuContainer.getParent(); // the Parent (or Scene) that contains the TextFields
			Label textField = null;
			for(Store_menu el:instance.getMenuAccess()) {
				textField=(Label) parent.lookup("#"+el.getFx_id());
				if (textField != null) {
					textField.setVisible(true);
					System.out.println(el.getFx_id()+" NO nulo");
				}else {
					System.out.println(el.getFx_id()+"nulo");
				}
			}
		}
		titleHome.getStyleClass().add("label-title");
		//menuContainer.getStyleClass().add("rootMenu");
		//menuContainer.setStyle("-fx-background-color: transparent;");
		/*
		File file = new File("/mx/com/bitmaking/application/assets/macrofoto_logo.jpg");
		Image image = new Image(file.toURI().toString(),300,200,false,false);
		imgAvatar.setFitHeight(200);
		imgAvatar.setFitWidth(300);
		imgAvatar.setPreserveRatio(false);
		imgAvatar.setImage(image);
		*/
		itemVenta.addEventHandler(MouseEvent.MOUSE_CLICKED,eventClick("Venta",true,false));
		lblGestionCat.addEventHandler(MouseEvent.MOUSE_CLICKED,eventClick("GestionProductos",true,false));
		lblReportes.addEventHandler(MouseEvent.MOUSE_CLICKED,eventClick("BusqPedidoReporte",false,false));
		lblGestCostProd.addEventHandler(MouseEvent.MOUSE_CLICKED,eventClick("CostProdByClient",false,false));
	//	menuHamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, showMenuContainer());
		
	}
	

	/**
	 * Aparece 
	 * @return
	 */
	@FXML
	private void showMenuContainer(MouseEvent event){
		System.out.println("entra menuContainershow_hide");
		if(menuContainer.isVisible()) {
			menuContainer.setVisible(false);
		//	lblGestionCat.setVisible(false);
			//lblGestCostProd.setVisible(false);
			//lblReportes.setVisible(false);
		//	backgroundOnMenu.setVisible(false);
			
		}else {
			menuContainer.setVisible(true);
		//	backgroundOnMenu.setVisible(true);
		}
		
	}
	
	/**
	 * Oculta menu al dar click en otro lado
	 * @param event
	 */
	@FXML
	private void hideMenuContainer(MouseEvent event){
		menuContainer.setVisible(false);/*
		lblGestionCat.setVisible(false);
		lblGestCostProd.setVisible(false);
		lblReportes.setVisible(false);
		backgroundOnMenu.setVisible(false);*/
	}
	/**
	 * Aparece u oculta sub menu de administraci�n
	 * @return
	 */
	@FXML
	private void showSubMenuAdmin(MouseEvent event){
		
		if(lblGestionCat.isVisible()) {
			lblGestionCat.setVisible(false);
			lblReportes.setVisible(false);
			lblGestCostProd.setVisible(false);
		}else {
			lblGestionCat.setVisible(true);
			lblReportes.setVisible(true);
			lblGestCostProd.setVisible(true);
		}
		
	}
	
	@FXML 
	private void openSectionVta(MouseEvent event) {
		 menuContainer.setVisible(false);
		 //lblGestionCat.setVisible(false);
		// lblGestCostProd.setVisible(false);
		// lblReportes.setVisible(false);
		// backgroundOnMenu.setVisible(false);
		 FXMLLoader vtaFxml = new FXMLLoader(getClass().getResource("../view/Venta.fxml"));
		 try {
			bodyContainer.getChildren().add(vtaFxml.load());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private EventHandler<MouseEvent> eventClick(String scene,boolean hasCss,boolean newWindow) {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					menuContainer.setVisible(false);
				/*	lblGestionCat.setVisible(false);
					lblGestCostProd.setVisible(false);
					lblReportes.setVisible(false);
					backgroundOnMenu.setVisible(false);
					*/
					bodyContainer.getChildren().clear();
		
			        FXMLLoader loader = //storeApp.initializeFXML("view/"+scene+".fxml");
			        		new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/"+scene+".fxml"));
			        loader.setControllerFactory(context::getBean);
			        Parent sceneHome = loader.load();
			        if(hasCss) 
			        	sceneHome.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/"+scene+".css").toExternalForm());
			        if(!newWindow){
				        ((Region)sceneHome).prefWidthProperty().bind(bodyContainer.widthProperty().multiply(1.0));
				        ((Region)sceneHome).prefHeightProperty().bind(bodyContainer.heightProperty().multiply(1.0));
				        ((Region)sceneHome).relocate(0, 0);
				        bodyContainer.getChildren().add(sceneHome);
				        switch(scene){
				        case "Venta":
				        	VentaController vtaCtrl = loader.getController();
				        	vtaCtrl.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED,returnToHome());
				        	break;
				        
			        	case "GestionProductos":
				        	GestProdController gestProdCtrl = loader.getController();
				        	gestProdCtrl.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED,returnToHome());
				        	break;
			        	case "BusqPedidoReporte":
			        		BusqPedidoRepController repCtrl  = loader.getController();
			        		repCtrl.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED,returnToHome());
			        		break;
			        	case "CostProdByClient":
			        		CostProdByClteController costProdCte = loader.getController();
			        		costProdCte.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED,returnToHome());
				        	break;
				        }
			        }
			        else{
			        	Scene scene = new Scene(sceneHome,620,460);
			        	Stage primaryStage =new Stage();
			        	primaryStage.initModality(Modality.APPLICATION_MODAL); //Evitar que otras ventanas se puedan modificar
			        	primaryStage.setScene(scene);
						primaryStage.centerOnScreen();
				        primaryStage.setTitle("Foto Estudio S.A de C.V.");
						primaryStage.setMinHeight(460.0);
						primaryStage.setMinWidth(620.0);
						primaryStage.setMaxHeight(450.0);
						primaryStage.setMaxWidth(620.0);
						primaryStage.show();
						
			        }
			       
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
		
	}

	private EventHandler<MouseEvent> returnToHome() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					menuContainer.setVisible(false);/*
					lblGestionCat.setVisible(false);
					lblReportes.setVisible(false);
					lblGestCostProd.setVisible(false);
					backgroundOnMenu.setVisible(false);*/
					bodyContainer.getChildren().clear();
		
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
		
	}
	
}
