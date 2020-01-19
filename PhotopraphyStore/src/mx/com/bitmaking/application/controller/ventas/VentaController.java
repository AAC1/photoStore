package mx.com.bitmaking.application.controller.ventas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class VentaController {
	@FXML private JFXButton btnEliminaPedido;
	@FXML private JFXButton  btnEditarPedido;
	@FXML private JFXButton  btnSalir;
	@FXML private JFXButton  btnCancelar;
	@FXML private JFXButton  btnGuardar;
	
	@FXML private JFXComboBox  cbxCatProd;
	@FXML private JFXComboBox<Store_cat_prod>  cbxEstatus;
	
	@FXML private JFXTextField inputFolio;
	@FXML private JFXTextField inputClienteName;
	@FXML private JFXTextField inputDescrip;
	@FXML private JFXTextField inputMontoAnt;
	@FXML private JFXTextField inputMonto;
	
	@FXML private AnchorPane ventaBody;
	@FXML private TableView tbProductos;
	@FXML private TableColumn tbColProd ;
	@FXML private TableColumn tbColDesc;
	@FXML private TableColumn tbColCant;
	@FXML private TableColumn tbColCosto;
	
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService catProdService;
	
	Stage stageBusqProd = null;
	
	/**
	 * @return the btnSalir
	 */
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		responsiveGUI();
		fillCbxProd();
		btnEliminaPedido.setVisible(false);
		//btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED,modalBusqByFolio());
		btnEditarPedido.addEventHandler(MouseEvent.MOUSE_CLICKED,modalBusqByFolio());
		
	}
	
	private void fillCbxProd() {
		if(catProdService ==null){
			GeneralMethods.modalMsg("ERROR", "Ha ocurrido un error", "Servicio no disponible");
			return;
		}
		List<Store_cat_prod> lstProd = catProdService.getCatalogoProduct();
		for(Store_cat_prod el: lstProd){
			System.out.println("id: "+el.getId_prod()+" producto:"+el.getProducto());
		}
		//cbxCatProd.setItems(FXCollections.observableArrayList(catProdService.getCatalogoProduct()));
		
		
	}

	public EventHandler<MouseEvent> modalBusqByFolio() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/ventas/BusquedaPedido.fxml"));
						
						Parent sceneEdit= fxmlLoader.load();
						Scene scene = new Scene(sceneEdit,3013,165);
						scene.getStylesheets().add(getClass().getResource("../../assets/css/ventas/Venta.css").toExternalForm());
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
		tbColProd.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.2));
		tbColDesc.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.4));
		tbColCant.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.2));
		tbColCosto.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.2));
		
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
