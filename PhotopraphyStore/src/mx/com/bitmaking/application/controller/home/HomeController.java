package mx.com.bitmaking.application.controller.home;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gluonhq.charm.glisten.control.CardPane;
import com.gluonhq.charm.glisten.control.ExpansionPanelContainer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.MystoreApplication;
import mx.com.bitmaking.application.controller.gestionProducto.GestProdController;
import mx.com.bitmaking.application.controller.ventas.VentaController;

@Component
//@Scope("prototype")
public class HomeController {
	
	
	@FXML private Label titleHome;
	@FXML private Label itemAdmin;
	@FXML private Label lblGestionCat;
	@FXML private Label lblReportes;
	@FXML private Label itemVenta;
	
	@FXML private JFXHamburger menuHamburger;
	@FXML private AnchorPane menuContainer;
	@FXML private AnchorPane bodyContainer;
	@FXML private AnchorPane backgroundOnMenu;
	
//	@Autowired
  //	private MystoreApplication storeApp;
	 @Autowired
	 private ApplicationContext context ;
	 
	public void initialize() {
		titleHome.getStyleClass().add("label-title");
		menuContainer.getStyleClass().add("rootMenu");
		menuContainer.setStyle("-fx-background-color: transparent;");
		
		itemVenta.addEventHandler(MouseEvent.MOUSE_CLICKED,eventClick("ventas/Venta",true,false));
		lblGestionCat.addEventHandler(MouseEvent.MOUSE_CLICKED,eventClick("gestionProducto/GestionProductos",true,false));
	//	menuHamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, showMenuContainer());
		
	}
	
	/**
	 * Aparece 
	 * @return
	 */
	@FXML
	private void showMenuContainer(MouseEvent event){
		if(menuContainer.isVisible()) {
			menuContainer.setVisible(false);
			lblGestionCat.setVisible(false);
			lblReportes.setVisible(false);
			backgroundOnMenu.setVisible(false);
		}else {
			menuContainer.setVisible(true);
			backgroundOnMenu.setVisible(true);
		}
		
	}
	
	/**
	 * Oculta menu al dar click en otro lado
	 * @param event
	 */
	@FXML
	private void hideMenuContainer(MouseEvent event){
		menuContainer.setVisible(false);
		lblGestionCat.setVisible(false);
		lblReportes.setVisible(false);
		backgroundOnMenu.setVisible(false);
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
			
		}else {
			lblGestionCat.setVisible(true);
			lblReportes.setVisible(true);
		}
		
	}
	
	@FXML 
	private void openSectionVta(MouseEvent event) {
		 menuContainer.setVisible(false);
		 lblGestionCat.setVisible(false);
		 lblReportes.setVisible(false);
		 backgroundOnMenu.setVisible(false);
		 FXMLLoader vtaFxml = new FXMLLoader(getClass().getResource("../view/ventas/Venta.fxml"));
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
					lblGestionCat.setVisible(false);
					lblReportes.setVisible(false);
					backgroundOnMenu.setVisible(false);
					bodyContainer.getChildren().clear();
		
			        FXMLLoader loader = //storeApp.initializeFXML("view/"+scene+".fxml");
			        		new FXMLLoader(getClass().getResource("../../view/"+scene+".fxml"));
			        loader.setControllerFactory(context::getBean);
			        Parent sceneHome = loader.load();
			        if(hasCss) 
			        	sceneHome.getStylesheets().add(getClass().getResource("../../assets/css/"+scene+".css").toExternalForm());
			        if(!newWindow){
				        ((Region)sceneHome).prefWidthProperty().bind(bodyContainer.widthProperty().multiply(1.0));
				        ((Region)sceneHome).prefHeightProperty().bind(bodyContainer.heightProperty().multiply(1.0));
				        ((Region)sceneHome).relocate(0, 0);
				        bodyContainer.getChildren().add(sceneHome);
				        switch(scene){
				        case "ventas/Venta":
				        	VentaController vtaCtrl = loader.getController();
				        	vtaCtrl.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED,returnToHome());
				        	break;
				        
			        	case "gestionProducto/GestionProductos":
				        	GestProdController gestProdCtrl = loader.getController();
				        	gestProdCtrl.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED,returnToHome());
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
					menuContainer.setVisible(false);
					lblGestionCat.setVisible(false);
					lblReportes.setVisible(false);
					backgroundOnMenu.setVisible(false);
					bodyContainer.getChildren().clear();
		
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
		
	}
	
}
