package mx.com.bitmaking.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.service.IStoreUsuarioService;

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
	
	@Autowired
	@Qualifier("StoreUsuarioService")
	IStoreUsuarioService storeUsuarioService;

	@Autowired
	@Qualifier("remoteStoreUsuarioService")
	IStoreUsuarioService remoteStoreUsuarioService;
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JFXButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public void initialize() {
		responsiveGUI();
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
		
	}
	
}
