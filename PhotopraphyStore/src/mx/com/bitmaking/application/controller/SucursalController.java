package mx.com.bitmaking.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.iservice.IStoreSucursalService;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class SucursalController {
	
	@FXML private JFXButton btnAgregar;
	@FXML private JFXButton btnEditar;
	@FXML private JFXButton btnEliminar;
	@FXML private JFXButton btnAccept;
	@FXML private JFXButton btnCancel;
	@FXML private JFXButton btnSalir;
	
	@FXML private AnchorPane containerBusqResp;
	@FXML private JFXButton btnOpenBusq;
	@FXML private JFXButton btnCloseBusq;
	@FXML private JFXButton btnBuscar;
	@FXML private JFXButton btnClean;
	@FXML private JFXTextField inputBusqSucursal;
	@FXML private JFXTextField inputBusqPrefijo;
	@FXML private JFXTextField inputBusqRazonSocial;
	@FXML private TableView<Store_sucursal> tblSucursal;
	@FXML private TableColumn<Store_sucursal,String>colRazonSocial;
	@FXML private TableColumn<Store_sucursal,String>colTelefono;
	@FXML private TableColumn<Store_sucursal,String>colDireccion;
	@FXML private TableColumn<Store_sucursal,String>colPrefijo;
	@FXML private TableColumn<Store_sucursal,String>colSucursal;
	
	@FXML private JFXTextField inputSucursal;
	@FXML private JFXTextField inputRazonSocial;
	@FXML private JFXTextField inputTelefono;
	@FXML private JFXTextField inputDir;
	@FXML private JFXTextField inputPrefijo;
	
	@Autowired
	private ApplicationContext context ;
	
	@Autowired
	@Qualifier("StoreSucursalService")
	IStoreSucursalService sucursalService;
	
	@Autowired
	@Qualifier("remoteStoreSucursalService")
	IStoreSucursalService remoteSucursalService;
	
	private String tipoForm = "";
	private Stage stageBusqProd = null;
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JFXButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public void initialize() {
		responsiveGUI();
		initForm();
	}
	
	private void initForm(){
		
	}
	
	@FXML
	private void openSearch() {
		containerBusqResp.setVisible(true);
	}
	@FXML
	private void closeSearch() {
		containerBusqResp.setVisible(false);
		cleanBusqform();
	}
	@FXML
	private void cleanBusqform() {
		inputBusqSucursal.setText("");
		inputBusqPrefijo.setText("");
		inputBusqRazonSocial.setText("");
	}
	@FXML
	private void buscaSucursal() {
		List<Store_sucursal> lstSuc =  (Flags.remote_valid)?
				remoteSucursalService.getSuc(inputBusqSucursal.getText(), 
								inputBusqPrefijo.getText(), inputBusqRazonSocial.getText()):
					sucursalService.getSuc(inputBusqSucursal.getText(), 
							inputBusqPrefijo.getText(), inputBusqRazonSocial.getText());
				
		tblSucursal.getItems().removeAll(tblSucursal.getItems());
		tblSucursal.setItems(FXCollections.observableList(lstSuc));
	}
	
	@FXML
	private void addSuc() {
		btnAgregar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancel.setVisible(true);
		btnAccept.setVisible(true);
		tblSucursal.setDisable(true);
		tipoForm="A";
		
		inputSucursal.setText("");
		inputRazonSocial.setText("");
		inputTelefono.setText("");
		inputPrefijo.setText("");
		inputDir.setText("");
		enableInputs();
	
	}
	@FXML
	private void editSuc() {
		Store_sucursal objSuc = tblSucursal.getSelectionModel().getSelectedItem();
		if(objSuc==null){
			GeneralMethods.modalMsg("INFO", "", "Seleccione una sucursal");
			return;
		}
		btnAgregar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancel.setVisible(true);
		btnAccept.setVisible(true);
		tblSucursal.setDisable(true);
		tipoForm ="M";
		enableInputs();
		
	}
	
	@FXML
	private void deleteSuc() {
		tipoForm="D";
		Store_sucursal objSuc = tblSucursal.getSelectionModel().getSelectedItem();
		if(objSuc==null){
			GeneralMethods.modalMsg("INFO", "", "Seleccione una sucursal");
			return;
		}
		ModalConfirmController ctrl = openModalConfirm();
		if(ctrl==null)return;
		
		ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow());
		ctrl.getLblMsg().setText("¿Seguro que desea cancelar la sucursal: "+objSuc.getSucursal()+" ?");
		ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptDeleteSuc(objSuc));
		
	}
	private EventHandler<MouseEvent> acceptDeleteSuc(Store_sucursal objSuc) {
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					if(Flags.remote_valid)
						remoteSucursalService.delete(objSuc.getId_sucursal());
					else 
						sucursalService.delete(objSuc.getId_sucursal());
					if(stageBusqProd!=null) stageBusqProd.close();
					disableInputs();
					clearInputs();
					buscaSucursal();
					
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	
	
	private ModalConfirmController openModalConfirm(){
		ModalConfirmController ctrl = null;
		try {
			
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/TreeProduct.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/ModalConfirm.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			Parent sceneEdit= fxmlLoader.load();
			Scene scene = new Scene(sceneEdit,3013,165);
			scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/GestionProductos.css").toExternalForm());
			stageBusqProd = new Stage();
			stageBusqProd.setScene(scene);
			stageBusqProd.setTitle("Confirmación");
			stageBusqProd.setMinHeight(200.0);
			stageBusqProd.setMinWidth(350.0);
			stageBusqProd.setMaxHeight(350.0);
			stageBusqProd.setMaxWidth(200.0);
			stageBusqProd.initModality(Modality.APPLICATION_MODAL); 
			stageBusqProd.show();
			ctrl = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			
	    } catch(Exception ex) {
			ex.printStackTrace();
			GeneralMethods.modalMsg("ERROR", "", ex.getMessage());
		}
		return ctrl;
	}
	private EventHandler<MouseEvent> closeWindow() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(stageBusqProd!=null) stageBusqProd.close();
			}
		};


	}
	private void responsiveGUI() {
		/* resize de acuerdo al tamaño del Pane padre */
		colSucursal.prefWidthProperty().bind(tblSucursal.widthProperty().multiply(0.2));
		colRazonSocial.prefWidthProperty().bind(tblSucursal.widthProperty().multiply(0.2));
		colTelefono.prefWidthProperty().bind(tblSucursal.widthProperty().multiply(0.2));
		colDireccion.prefWidthProperty().bind(tblSucursal.widthProperty().multiply(0.2));
		colPrefijo.prefWidthProperty().bind(tblSucursal.widthProperty().multiply(0.2));
		
		//COLUMNAS DE PRODUCTOS DE PEDIDO
		colSucursal.setCellValueFactory(new PropertyValueFactory<Store_sucursal, String>("sucursal"));
		colRazonSocial.setCellValueFactory(new PropertyValueFactory<Store_sucursal, String>("razon_social"));
		colTelefono.setCellValueFactory(new PropertyValueFactory<Store_sucursal, String>("telefono"));
		colDireccion.setCellValueFactory(new PropertyValueFactory<Store_sucursal, String>("direccion"));
		colPrefijo.setCellValueFactory(new PropertyValueFactory<Store_sucursal, String>("prefijo"));
		
	}
	private void disableInputs() {
		inputSucursal.setDisable(true);
		inputRazonSocial.setDisable(true);
		inputPrefijo.setDisable(true);
		inputTelefono.setDisable(true);
		inputDir.setDisable(true);
	}
	private void clearInputs() {
		inputSucursal.setText("");
		inputRazonSocial.setText("");
		inputTelefono.setText("");
		inputDir.setText("");
		inputPrefijo.setText("");
	}
	private void enableInputs() {
		inputSucursal.setDisable(false);
		inputRazonSocial.setDisable(false);
		inputTelefono.setDisable(false);
		inputPrefijo.setDisable(false);
		inputDir.setDisable(false);
		
	}
}
