package mx.com.bitmaking.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
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
import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.iservice.IStoreCatEstatusService;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.iservice.IStoreFotografoService;
import mx.com.bitmaking.application.service.StoreCatProdService;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
//@Scope("prototype")
public class VentaController {
	@FXML private JFXButton btnConsultPedido;
	@FXML private JFXButton  btnEditarPedido;
	@FXML private JFXButton  btnSalir;
	@FXML private JFXButton  btnCancelar;
	@FXML private JFXButton  btnGuardar;
	@FXML private JFXButton btnSelectProd;
	
	@FXML private JFXComboBox<String>  cbxEstatus;
	@FXML private JFXComboBox<String> cbxCliente;
	
	@FXML private JFXTextField inputFolio;
	@FXML private JFXTextField inputCliente;
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
	
	@Autowired
	IStoreFotografoService fotografoService;
	
	@Autowired
	IStoreCatEstatusService catEstatusService;
	
	@Autowired
	 private ApplicationContext context ;
	
	Stage stageBusqProd = null;
	List<Store_fotografo> lstFoto = null;
	List<Store_cat_estatus> lstEstatus = null;
	/**
	 * @return the btnSalir
	 */
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		
		responsiveGUI();
		initForm();
		
	//	btnEliminaPedido.setVisible(false);
		//btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED,modalBusqByFolio());
		btnEditarPedido.addEventHandler(MouseEvent.MOUSE_CLICKED,modalBusqByFolio());
		
	}
	
	private void initForm() {
		fillCbxClte(); //llena combo de clientes
		getLstEstatus();//Llena combo de estatus
	}
	private void getLstEstatus() {
		lstEstatus = catEstatusService.getListEstatus();
		String[] arrayStts = new String[lstEstatus.size()];
		for(int i=0; i<lstEstatus.size();i++) {
			arrayStts[i] = lstEstatus.get(i).getEstatus();
		}
		cbxEstatus.setItems(FXCollections.observableArrayList(arrayStts));
	}
	@FXML 
	private void selectCte() {
		if(lstFoto ==null) {
			return;
		}
		
		int idxClte = cbxCliente.getSelectionModel().getSelectedIndex() ;
		inputCliente.setText("");
		if(idxClte ==0) {
			inputCliente.setDisable(false);
		}else {
			inputCliente.setDisable(true);
			inputCliente.setText(lstFoto.get((idxClte)).getFotografo());
		}
	}
	
	private void fillCbxClte() {
		if(fotografoService ==null){
			GeneralMethods.modalMsg("ERROR", "Ha ocurrido un error", "Servicio no disponible");
			return;
		}
		lstFoto = fotografoService.getActiveClients();
		String[] arrayClte = new String[lstFoto.size()];
		
		int idx=0;
		for(Store_fotografo el: lstFoto){
			arrayClte[idx++] = el.getFotografo();
		}
		cbxCliente.setItems(FXCollections.observableArrayList(arrayClte));
		
		
	}
	
	@FXML
	private void openTreProd() {
		try {
			int idxClte = cbxCliente.getSelectionModel().getSelectedIndex();
			if(idxClte <0) {
				GeneralMethods.modalMsg("", "", "Debes seleccionar un cliente");
				return;
			}
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/TreeProduct.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			Parent sceneEdit= fxmlLoader.load();
			Scene scene = new Scene(sceneEdit,3013,165);
			scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/GestionProductos.css").toExternalForm());
			stageBusqProd = new Stage();
			stageBusqProd.setScene(scene);
			stageBusqProd.setTitle("Selecciona Producto ");
			stageBusqProd.setMinHeight(636.0);
			stageBusqProd.setMinWidth(765.0);
			stageBusqProd.setMaxHeight(636.0);
			stageBusqProd.setMaxWidth(765.0);
			//stageBusqProd.setMaxHeight(200.0);
		//	stageBusqProd.setMaxWidth(300.0);
			stageBusqProd.initModality(Modality.APPLICATION_MODAL); 
			stageBusqProd.show();
			TreeProductoController busqProd = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			
			busqProd.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow());
			
			
			System.out.println("idxClte:"+idxClte);
			int idClte = 0;
			if(idxClte >0) {
				idClte=lstFoto.get((idxClte)).getId_fotografo();
			}
			busqProd.getTblCatProducts(idClte);
			
	    } catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

	private EventHandler<MouseEvent> closeWindow() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(stageBusqProd!=null) stageBusqProd.close();
			}
		};


	}

	public EventHandler<MouseEvent> modalBusqByFolio() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/BusquedaPedido.fxml"));
						
						Parent sceneEdit= fxmlLoader.load();
						Scene scene = new Scene(sceneEdit,3013,165);
						scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/Venta.css").toExternalForm());
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
