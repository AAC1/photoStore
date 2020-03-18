package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_perfil;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.iservice.IStorePerfilService;
import mx.com.bitmaking.application.iservice.IStoreUsuarioService;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class UsuarioController {
	
	@FXML private JFXButton btnAgregar;
	@FXML private JFXButton btnEditar;
	@FXML private JFXButton btnEliminar;
	@FXML private JFXButton btnDesbloquear;
	@FXML private JFXButton btnAccept;
	@FXML private JFXButton btnCancel;
	@FXML private JFXButton btnSalir;
	@FXML private TableView<UsuariosDTO> tblUsr;
	@FXML private TableColumn<UsuariosDTO, String> colUsuario;
	@FXML private TableColumn<UsuariosDTO, String> colLogin;
	@FXML private TableColumn<UsuariosDTO, String> colTelefono;
	@FXML private TableColumn<UsuariosDTO, String> colCorreo;
	@FXML private TableColumn<UsuariosDTO, String> colDireccion;
	@FXML private TableColumn<UsuariosDTO, String> colSucursal;
	@FXML private TableColumn<UsuariosDTO, String> colPerfil;
	@FXML private TableColumn<UsuariosDTO, String> colEstatus;
	@FXML private JFXTextField inputLogin;
	@FXML private JFXPasswordField inputPasswd;
	@FXML private JFXTextField inputEmail;
	@FXML private JFXTextField inputTelefono;
	@FXML private JFXTextField inputUsr;
	@FXML private JFXTextField inputDir;
	@FXML private JFXTextField inputSucursal;
	@FXML private JFXComboBox<String> cbxPerfil;
	@FXML private JFXComboBox<String> cbxStts;
	@FXML private JFXComboBox<String> cbxBloqueado;
	
	@FXML private AnchorPane containerBusqResp;
	@FXML private JFXButton btnOpenBusq;
	@FXML private JFXButton btnCloseBusq;
	@FXML private JFXButton btnBuscar;
	@FXML private JFXButton btnClean;
	@FXML private JFXTextField inputBusqLogin;
	@FXML private JFXTextField inputBusqUsuario;
	@FXML private JFXTextField inputBusqSucursal;
	@FXML private JFXComboBox<String> cbxBusqEstatus;
	@FXML private JFXComboBox<String> cbxBusqPerfil;
	
	@Autowired
	@Qualifier("StoreUsuarioService")
	IStoreUsuarioService storeUsuarioService;

	@Autowired
	@Qualifier("remoteStoreUsuarioService")
	IStoreUsuarioService remoteStoreUsuarioService;
	
	
	@Autowired
	@Qualifier("StorePerfilService")
	IStorePerfilService storePerfilService;

	@Autowired
	@Qualifier("remoteStorePerfilService")
	IStorePerfilService remoteStorePerfilService;
	
	private List<String> lstProfile = null;
	
	
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
	
	private void initForm() {
		List<String> lstStts = new ArrayList<>();
		lstStts.add("Activo");
		lstStts.add("Inactivo");
		
		cbxStts.getItems().removeAll(cbxStts.getItems());
		cbxStts.setItems(FXCollections.observableList(lstStts));
		
		cbxBusqEstatus.getItems().removeAll(cbxStts.getItems());
		cbxBusqEstatus.setItems(FXCollections.observableList(lstStts));
		
		List<Store_perfil> lst = (Flags.remote_valid)?remoteStorePerfilService.getAllProfiles():storePerfilService.getAllProfiles();
		lstProfile = new ArrayList<>();
		for(Store_perfil el:lst) {
			lstProfile.add(el.getPerfil());
		}
		
		cbxBusqPerfil.getItems().removeAll(cbxBusqPerfil.getItems());
		cbxBusqPerfil.setItems(FXCollections.observableList(lstProfile));
		
	}
	private void responsiveGUI() {
		/* resize de acuerdo al tamaño del Pane padre */
		colUsuario.prefWidthProperty().bind(tblUsr.widthProperty().multiply(0.3));
		colLogin.prefWidthProperty().bind(tblUsr.widthProperty().multiply(0.2));
		colTelefono.prefWidthProperty().bind(tblUsr.widthProperty().multiply(0.2));
		colCorreo.prefWidthProperty().bind(tblUsr.widthProperty().multiply(0.2));
		colDireccion.prefWidthProperty().bind(tblUsr.widthProperty().multiply(0.3));
		colSucursal.prefWidthProperty().bind(tblUsr.widthProperty().multiply(0.2));
		colPerfil.prefWidthProperty().bind(tblUsr.widthProperty().multiply(0.2));
		colEstatus.prefWidthProperty().bind(tblUsr.widthProperty().multiply(0.2));
		
		//COLUMNAS DE PRODUCTOS DE PEDIDO
		colUsuario.setCellValueFactory(new PropertyValueFactory<UsuariosDTO, String>("nombre"));
		colLogin.setCellValueFactory(new PropertyValueFactory<UsuariosDTO, String>("login"));
		colTelefono.setCellValueFactory(new PropertyValueFactory<UsuariosDTO, String>("telefono"));
		colCorreo.setCellValueFactory(new PropertyValueFactory<UsuariosDTO, String>("correo"));
		colDireccion.setCellValueFactory(new PropertyValueFactory<UsuariosDTO, String>("direccion"));
		colSucursal.setCellValueFactory(new PropertyValueFactory<UsuariosDTO, String>("sucursal"));
		colPerfil.setCellValueFactory(new PropertyValueFactory<UsuariosDTO, String>("perfil"));
		colEstatus.setCellValueFactory(new PropertyValueFactory<UsuariosDTO, String>("estatus"));
		
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
		inputBusqLogin.setText("");
		inputBusqUsuario.setText("");
		inputBusqSucursal.setText("");
		cbxBusqEstatus.setValue("");
		cbxBusqPerfil.setValue("");
	}
	@FXML
	private void buscaUsuario() {
		List<UsuariosDTO> lstUsrs = storeUsuarioService.getUsrsByFilter(inputBusqLogin.getText(),
				inputBusqUsuario.getText(),cbxBusqEstatus.getValue(),
				inputBusqSucursal.getText(),cbxBusqPerfil.getValue());
		
		tblUsr.setItems(FXCollections.observableList(lstUsrs));
		
	}
}
