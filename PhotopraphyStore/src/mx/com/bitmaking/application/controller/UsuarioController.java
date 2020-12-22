package mx.com.bitmaking.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
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
import mx.com.bitmaking.application.entity.Store_perfil;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.iservice.IStorePerfilService;
import mx.com.bitmaking.application.iservice.IStoreSucursalService;
import mx.com.bitmaking.application.iservice.IStoreUsuarioService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class UsuarioController {
	Logger logger = Logger.getLogger(UsuarioController.class);
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
	@FXML private JFXComboBox<String> cbxSucursal;
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
	private ApplicationContext context ;
	
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
	
	
	@Autowired
	@Qualifier("StoreSucursalService")
	IStoreSucursalService sucursalService;
	
	@Autowired
	@Qualifier("remoteStoreSucursalService")
	IStoreSucursalService remoteSucursalService;
	private Stage stageBusqProd = null;
	private List<String> lstProfile = null;
	private List<String>lstSucursal = null;
	List<Store_perfil> lstPerfil = null;
	List<Store_sucursal> lstSuc = null;
	private String tipoForm = "";
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
		lstStts.add("ACTIVO");
		lstStts.add("INACTIVO");
		
		List<String> lstBloq = new ArrayList<>();
		lstBloq.add("BLOQUEADO");
		lstBloq.add("NO BLOQUEADO");
		
		cbxStts.getItems().removeAll(cbxStts.getItems());
		cbxStts.setItems(FXCollections.observableList(lstStts));
		
		cbxBusqEstatus.getItems().removeAll(cbxBusqEstatus.getItems());
		cbxBusqEstatus.setItems(FXCollections.observableList(lstStts));
		
		cbxBloqueado.getItems().removeAll(cbxBloqueado.getItems());
		cbxBloqueado.setItems(FXCollections.observableList(lstBloq));
		
		lstPerfil = (Flags.remote_valid)?remoteStorePerfilService.getAllProfiles():storePerfilService.getAllProfiles();
		lstProfile = new ArrayList<>();
		if(lstPerfil ==null)lstPerfil=new ArrayList<>();
		for(Store_perfil el:lstPerfil) {
			lstProfile.add(el.getPerfil());
		}

		cbxBusqPerfil.getItems().removeAll(cbxBusqPerfil.getItems());
		cbxBusqPerfil.setItems(FXCollections.observableList(lstProfile));

		cbxPerfil.getItems().removeAll(cbxPerfil.getItems());
		cbxPerfil.setItems(FXCollections.observableList(lstProfile));
		
		lstSuc =  (Flags.remote_valid)?
				remoteSucursalService.getSuc("", "", ""):sucursalService.getSuc("", "", "");
				
		lstSucursal = new ArrayList<>();
		if(lstSuc==null)lstSuc = new ArrayList<>();
		
		for(Store_sucursal el:lstSuc) {
			lstSucursal.add(el.getSucursal());
		}
	
		cbxSucursal.getItems().removeAll(cbxSucursal.getItems());
		cbxSucursal.setItems(FXCollections.observableList(lstSucursal));
		
		inputTelefono.textProperty().addListener(GeneralMethods.onlyNumber(inputTelefono));
		
		busqUsr();
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
	
	private void busqUsr() {
		containerBusqResp.setVisible(false);
		List<UsuariosDTO> lstUsrs = (Flags.remote_valid)?
				remoteStoreUsuarioService.getUsrsByFilter(inputBusqLogin.getText(),
				inputBusqUsuario.getText(),cbxBusqEstatus.getValue(),
				inputBusqSucursal.getText(),cbxBusqPerfil.getValue()):
				storeUsuarioService.getUsrsByFilter(inputBusqLogin.getText(),
							inputBusqUsuario.getText(),cbxBusqEstatus.getValue(),
							inputBusqSucursal.getText(),cbxBusqPerfil.getValue());
		
		tblUsr.getItems().removeAll(tblUsr.getItems());
		tblUsr.setItems(FXCollections.observableList(lstUsrs));
		if(lstUsrs.size()==0){
			GeneralMethods.modalMsg("", "", "No se encontraron registros");
		}
	}
	
	@FXML
	private void openSearch() {
		cleanBusqform();
		containerBusqResp.setVisible(true);
	}
	@FXML
	private void closeSearch() {
		containerBusqResp.setVisible(false);
		
	}
	@FXML
	private void cleanBusqform() {
		inputBusqLogin.setText("");
		inputBusqUsuario.setText("");
		inputBusqSucursal.setText("");
		cbxBusqEstatus.setValue("");
		cbxBusqPerfil.setValue("");
		clearInputs();
	}
	@FXML
	private void buscaUsuario() {
		busqUsr();
		
	}
	@FXML
	private void selectUsr() {
		UsuariosDTO usrDto = tblUsr.getSelectionModel().getSelectedItem();
		if(usrDto ==null)return;
		
		inputLogin.setText(usrDto.getLogin());
		inputPasswd.setText(usrDto.getPasswd());
		inputUsr.setText(usrDto.getNombre());
		inputEmail.setText(usrDto.getCorreo());
		inputTelefono.setText(usrDto.getTelefono());
		inputDir.setText(usrDto.getDireccion());
		cbxPerfil.setValue(usrDto.getPerfil());
		cbxStts.setValue(usrDto.getEstatus());
		cbxSucursal.setValue(usrDto.getSucursal());
		cbxBloqueado.setValue(usrDto.getBloqueado()==1?"BLOQUEADO":"NO BLOQUEADO");
		
		
	}
	
	@FXML
	private void addUsr() {
		btnAgregar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancel.setVisible(true);
		btnAccept.setVisible(true);
		tblUsr.setDisable(true);
		tipoForm="A";
		
		inputLogin.setText("");
		inputPasswd.setText("");
		inputUsr.setText("");
		inputEmail.setText("");
		inputTelefono.setText("");
		inputDir.setText("");
		cbxPerfil.setValue("");
		cbxStts.setValue("ACTIVO");
		cbxSucursal.setValue("");
		cbxBloqueado.setValue("NO BLOQUEADO");
		enableInputs();
	
	}
	@FXML
	private void editUsr() {
		UsuariosDTO objUsr = tblUsr.getSelectionModel().getSelectedItem();
		if(objUsr==null){
			GeneralMethods.modalMsg("INFO", "", "Seleccione un usuario");
			return;
		}
		btnAgregar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancel.setVisible(true);
		btnAccept.setVisible(true);
		tblUsr.setDisable(true);
		tipoForm ="M";
		enableInputs();
		inputPasswd.setDisable(true);
		
	}
	
	@FXML
	private void deleteUsr() {
		tipoForm="D";
		UsuariosDTO objUsr = tblUsr.getSelectionModel().getSelectedItem();
		if(objUsr==null){
			GeneralMethods.modalMsg("INFO", "", "Seleccione un usuario");
			return;
		}
		ModalConfirmController ctrl = openModalConfirm();
		if(ctrl==null)return;
		
		ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow());
		ctrl.getLblMsg().setText("¿Seguro que desea cancelar el usuario:"+objUsr.getLogin()+" ?");
		ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptDeleteUsr(objUsr));
		
	}
	private EventHandler<MouseEvent> acceptDeleteUsr(UsuariosDTO objUsr) {
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					if(Flags.remote_valid)
						remoteStoreUsuarioService.deleteUser(objUsr.getId_usr());
					else 
						storeUsuarioService.deleteUser(objUsr.getId_usr());
					if(stageBusqProd!=null) stageBusqProd.close();
					disableInputs();
					clearInputs();
					buscaUsuario();
					
		        } catch(Exception ex) {
					ex.printStackTrace();
					logger.info(ex.getMessage());
				}
			}};
	}

	@FXML
	public void cancelEditUsr() {
		btnAgregar.setDisable(false);
		btnEditar.setDisable(false);
		btnEliminar.setDisable(false);
		btnCancel.setVisible(false);
		btnAccept.setVisible(false);
		tblUsr.setDisable(false);
		tipoForm="";
		disableInputs();
	}
	@FXML 
	private void acceptUsr() {
		
		if(!"M".equals(tipoForm) && !"A".equals(tipoForm)) {
			GeneralMethods.modalMsg("ERROR", "", "No se pudo identificar la accion a ejecutar: "+tipoForm);
			return;
		}
		
		if(inputLogin.getText()==null || "".equals(inputLogin.getText().trim())) {
			GeneralMethods.modalMsg("ERROR", "", "Ingrese un usuario para la cuenta del empleado");
			return;
		}
		if(inputPasswd.getText()==null || "".equals(inputPasswd.getText().trim())) {
			GeneralMethods.modalMsg("ERROR", "", "Ingrese una contraseña para la cuenta del empleado");
			return;
		}
		if(inputUsr.getText()==null || "".equals(inputUsr.getText().trim())) {
			GeneralMethods.modalMsg("ERROR", "", "Ingrese el nombre del empleado");
			return;
		}
		int rowPerfil=cbxPerfil.getSelectionModel().getSelectedIndex();
		if(rowPerfil<0) {
			GeneralMethods.modalMsg("ERROR", "", "Seleccione un perfil para el usuario");
			return;
		}
		int rowSuc = cbxSucursal.getSelectionModel().getSelectedIndex();
		if(rowSuc<0) {
			GeneralMethods.modalMsg("ERROR", "", "Seleccione una sucursal para el usuario");
			return;
		}
		
		btnAgregar.setDisable(false);
		btnEditar.setDisable(false);
		btnEliminar.setDisable(false);
		btnCancel.setVisible(false);
		btnAccept.setVisible(false);
		tblUsr.setDisable(false);
		
		Store_usuario usuario = new Store_usuario();
		usuario.setActivo("ACTIVO".equals(cbxStts.getValue())?1:0);
		usuario.setBloqueado("BLOQUEADO".equals(cbxBloqueado.getValue())?1:0);
		usuario.setCorreo(inputEmail.getText());
		usuario.setDireccion(inputDir.getText());
		usuario.setId_perfil(lstPerfil.get(rowPerfil).getId_perfil());
		usuario.setId_sucursal(lstSuc.get(rowSuc).getId_sucursal());
		usuario.setIntentos(0);
		usuario.setLogin(inputLogin.getText().trim());
		
		usuario.setTelefono(inputTelefono.getText());
		usuario.setNombre(inputUsr.getText());
		if("M".equals(tipoForm)) {
			UsuariosDTO objUsr = tblUsr.getSelectionModel().getSelectedItem();
			usuario.setPasswd(inputPasswd.getText());
			usuario.setId_usr(objUsr.getId_usr());
		//	usuario.setPasswd(objUsr.getPasswd());
		}else {
			usuario.setPasswd( GeneralMethods.cifraSha256(inputPasswd.getText(), Constantes.SALT));
		}
		
		if(Flags.remote_valid)
			remoteStoreUsuarioService.saveUser(usuario);
		else 
			storeUsuarioService.saveUser(usuario);
		
		disableInputs();
		clearInputs();
		buscaUsuario();
	}
	
	private void disableInputs() {
		inputLogin.setDisable(true);
		inputPasswd.setDisable(true);
		inputUsr.setDisable(true);
		inputEmail.setDisable(true);
		inputTelefono.setDisable(true);
		inputDir.setDisable(true);
		cbxPerfil.setDisable(true);
		cbxStts.setDisable(true);
		cbxSucursal.setDisable(true);
		cbxBloqueado.setDisable(true);
	}
	private void clearInputs() {
		inputLogin.setText("");
		inputPasswd.setText("");
		inputUsr.setText("");
		inputEmail.setText("");
		inputTelefono.setText("");
		inputDir.setText("");
		cbxPerfil.setValue("");
		cbxStts.setValue("");
		cbxSucursal.setValue("");
		cbxBloqueado.setValue("");
	}
	private void enableInputs() {
		inputLogin.setDisable(false);
		inputPasswd.setDisable(false);
		inputUsr.setDisable(false);
		inputEmail.setDisable(false);
		inputTelefono.setDisable(false);
		inputDir.setDisable(false);
		cbxPerfil.setDisable(false);
		cbxStts.setDisable(false);
		cbxSucursal.setDisable(false);
		cbxBloqueado.setDisable(false);
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
			stageBusqProd.setMinWidth(450.0);
			stageBusqProd.setMaxWidth(450.0);
			stageBusqProd.setMaxHeight(200.0);
			stageBusqProd.initModality(Modality.APPLICATION_MODAL); 
			stageBusqProd.show();
			ctrl = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			
	    } catch(Exception ex) {
			ex.printStackTrace();
			GeneralMethods.modalMsg("ERROR", "", ex.getMessage());

			logger.info(ex.getMessage());
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
}
