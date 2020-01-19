package mx.com.bitmaking.application.controller.gestionProducto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.controller.ventas.BusqVentaController;

@Component
//@Scope("prototype")
public class GestProdController {
	@FXML private JFXButton btnAddProd;
	@FXML private JFXButton btnEliminarProd;
	@FXML private JFXButton btnEdtProd;
	@FXML private JFXButton btnSalir;
	@FXML private TableView tblProducts;
	@FXML private TableColumn colProd;
	@FXML private TableColumn colEstatus;
	@FXML private AnchorPane bodyCatProd;
	Stage stageProd = null;
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		responsiveGUI();
		
		btnAddProd.addEventHandler(MouseEvent.MOUSE_CLICKED,modalEditProd("A"));
		btnEdtProd.addEventHandler(MouseEvent.MOUSE_CLICKED,modalEditProd("M"));
		
	}
	
	/**
	 * 
	 * @param typeForm: saber si es alta o edici�n
	 * @return
	 */
	private EventHandler modalEditProd(String typeForm) {
		return new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			//System.out.println(event.getSource());
			try {
				
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/gestionProducto/EditProducto.fxml"));
					
					Parent sceneEdit= fxmlLoader.load();
					Scene scene = new Scene(sceneEdit,300,300);
					scene.getStylesheets().add(getClass().getResource("../../assets/css/gestionProducto/GestionProductos.css").toExternalForm());
					stageProd = new Stage();
					stageProd.setScene(scene);
					stageProd.setTitle("Editar Producto");
					stageProd.setMinHeight(350.0);
					stageProd.setMinWidth(300.0);
					stageProd.setMaxHeight(350.0);
					stageProd.setMaxWidth(300.0);
					stageProd.initModality(Modality.APPLICATION_MODAL); 
					stageProd.show();
					EditaProdController edtProd = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
					
					edtProd.getBtnCancel().addEventHandler(MouseEvent.MOUSE_CLICKED, closeModalEditProd());

					edtProd.getBtnAccept().addEventHandler(MouseEvent.MOUSE_CLICKED, acceptEditProd(edtProd,typeForm));
				
	        } catch(Exception ex) {
				ex.printStackTrace();
			}
		}};
	}
	private EventHandler<MouseEvent> acceptEditProd(EditaProdController edtProd,String typeForm) {
		return new EventHandler<MouseEvent>() {
	
			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					if("A".equals(typeForm)) {
						
					}else if("M".equals(typeForm)) {
						
					}else {
						//Mensaje de error
					}
					//Hacer metodo para actualizar productos
					stageProd.close();
					
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	
	private EventHandler<MouseEvent> closeModalEditProd() {
		return new EventHandler<MouseEvent>() {
	
			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					stageProd.close();
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	private void responsiveGUI() {
		/* Panel de Home resize de acuerdo al tama�o del Pane padre*/
		colProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.6));
		colEstatus.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.4));
		
	}
}
