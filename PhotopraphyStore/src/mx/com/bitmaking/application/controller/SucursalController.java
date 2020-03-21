package mx.com.bitmaking.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.iservice.IStoreSucursalService;
import mx.com.bitmaking.application.util.Flags;

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
	@Qualifier("StoreSucursalService")
	IStoreSucursalService sucursalService;
	
	@Autowired
	@Qualifier("remoteStoreSucursalService")
	IStoreSucursalService remoteSucursalService;
	
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
}
