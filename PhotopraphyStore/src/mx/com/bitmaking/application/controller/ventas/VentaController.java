package mx.com.bitmaking.application.controller.ventas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class VentaController {
	@FXML private JFXButton btnEliminaPedido;
	@FXML private JFXButton  btnEditarPedido;
	@FXML private JFXButton  btnSalir;
	@FXML private JFXButton  btnCancelar;
	@FXML private JFXButton  btnGuardar;
	
	@FXML private ComboBox<?>  cbxCatProd;
	@FXML private ComboBox<?>  cbxEstatus;
	
	@FXML private JFXTextField inputFolio;
	@FXML private JFXTextField inputClienteName;
	@FXML private JFXTextField inputDescrip;
	@FXML private JFXTextField inputMontoAnt;
	@FXML private JFXTextField inputMonto;
	
	@FXML private AnchorPane ventaBody;
	@FXML private TableColumn tbColProd ;
	@FXML private TableColumn tbColDesc;
	@FXML private TableColumn tbColCant;
	@FXML private TableColumn tbColCosto;
	
	Stage stageBusqProd = null;
	
	
	
	/**
	 * @return the btnSalir
	 */
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		responsiveGUI();
		btnEliminaPedido.setVisible(false);
		//btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED,modalBusqByFolio());
		btnEditarPedido.addEventHandler(MouseEvent.MOUSE_CLICKED,modalBusqByFolio());
		
	}
	
	public EventHandler<MouseEvent> modalBusqByFolio() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ventas/BusquedaPedido.fxml"));
						
						Parent sceneEdit= fxmlLoader.load();
						Scene scene = new Scene(sceneEdit,3013,165);
						scene.getStylesheets().add(getClass().getResource("../assets/css/Venta.css").toExternalForm());
						stageBusqProd = new Stage();
						stageBusqProd.setScene(scene);
						stageBusqProd.setTitle("Busqueda de Pedido ");
						stageBusqProd.setMinHeight(200.0);
						stageBusqProd.setMinWidth(300.0);
						stageBusqProd.setMaxHeight(200.0);
						stageBusqProd.setMaxWidth(300.0);
						stageBusqProd.initModality(Modality.APPLICATION_MODAL); 
						stageBusqProd.show();
						BusqVentaController busqProd = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
						
						busqProd.getBtnBusqByFolio().addEventHandler(MouseEvent.MOUSE_CLICKED, busqPedido(busqProd));
					
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}

	
	public EventHandler<MouseEvent> busqPedido(BusqVentaController busqProd) {
		return new EventHandler<MouseEvent>() {
	
			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					String folio=busqProd.getInputBusqFolio().getText();
					if(folio !=null && !"".equals(folio)){
						System.out.println("Folio: "+folio);
						stageBusqProd.close();
					}
					
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}

	
	private void responsiveGUI() {
		/* Panel de Home resize de acuerdo al tama�o del Pane padre*/
		tbColProd.prefWidthProperty().bind(ventaBody.widthProperty().multiply(0.09));
		tbColDesc.prefWidthProperty().bind(ventaBody.widthProperty().multiply(0.18));
		tbColCant.prefWidthProperty().bind(ventaBody.widthProperty().multiply(0.09));
		tbColCosto.prefWidthProperty().bind(ventaBody.widthProperty().multiply(0.09));
		
	}
	private EventHandler<MouseEvent> closeVta() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					
			       
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
		
	}
}
