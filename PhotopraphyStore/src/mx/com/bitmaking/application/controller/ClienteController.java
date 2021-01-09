package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import mx.com.bitmaking.application.dto.ClienteDTO;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.entity.Store_perfil;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.iservice.IStoreClteProdCostService;
import mx.com.bitmaking.application.iservice.IStoreFotografoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class ClienteController {
	Logger logger = Logger.getLogger(ClienteController.class);
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
	@FXML private JFXTextField inputBusqCliente;
	@FXML private TableView<ClienteDTO> tblClientes;
	@FXML private TableColumn<ClienteDTO,String>colTelefono;
	@FXML private TableColumn<ClienteDTO,String>colCorreo;
	@FXML private TableColumn<ClienteDTO,String>colEstatus;
	@FXML private TableColumn<ClienteDTO,String>colFotografo;
	
	@FXML private JFXTextField inputFotografo;
	@FXML private JFXTextField inputTelefono;
	@FXML private JFXTextField inputCorreo;
	@FXML private JFXComboBox<String> cbxStts;

	@FXML private JFXComboBox<String> cbxTipoCte;
	@Autowired
	private ApplicationContext context ;

	@Autowired
	@Qualifier("StoreFotografoService")
	private IStoreFotografoService  fotografoService;
	
	@Autowired
	@Qualifier("remoteStoreFotografoService")
	private IStoreFotografoService  remoteFotografoService;
	
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService storeCatProdService;
	@Autowired
	@Qualifier("remoteStoreCatProdService")
	IStoreCatProdService remoteStoreCatProdService;
	
	@Autowired 
	@Qualifier("StoreClteProdCostService")
	IStoreClteProdCostService clteProdCostService;
	@Autowired 
	@Qualifier("remoteStoreClteProdCostService")
	IStoreClteProdCostService remoteClteProdCostService;
	
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
		disableInputs();
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
		inputBusqCliente.setText("");
		clearInputs();
	}
	@FXML
	private void buscaCliente() {
		busqClte();
		
	}
	@FXML
	private void selectClte() {
		ClienteDTO clteDto = tblClientes.getSelectionModel().getSelectedItem();
		if(clteDto ==null)return;
		
		inputFotografo.setText(clteDto.getFotografo());
		inputCorreo.setText(clteDto.getEmail());
		inputTelefono.setText(clteDto.getTelefono());
		cbxStts.setValue(clteDto.getEstatusStr());
		
		if("A".equals(clteDto.getTipo())) {
			cbxTipoCte.setValue("AFICIONADO");
		}
		else if("F".equals(clteDto.getTipo())) {
			cbxTipoCte.setValue("FOTOGRAFO");
		}
		
		
	}
	
	@FXML
	private void addClte() {
		btnAgregar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancel.setVisible(true);
		btnAccept.setVisible(true);
		tblClientes.setDisable(true);
		tipoForm="A";
		
		inputFotografo.setText("");
		inputCorreo.setText("");
		inputTelefono.setText("");
		cbxStts.setValue("ACTIVO");
		cbxTipoCte.setValue("AFICIONADO");
		enableInputs();
	
	}
	@FXML
	private void editClte() {
		ClienteDTO clteUsr = tblClientes.getSelectionModel().getSelectedItem();
		if(clteUsr==null){
			GeneralMethods.modalMsg("INFO", "", "Seleccione un cliente");
			return;
		}
		btnAgregar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		btnCancel.setVisible(true);
		btnAccept.setVisible(true);
		tblClientes.setDisable(true);
		tipoForm ="M";
		enableInputs();
		
	}
	
	@FXML
	private void deleteClte() {
		tipoForm="D";
		ClienteDTO clteUsr = tblClientes.getSelectionModel().getSelectedItem();
		if(clteUsr==null){
			GeneralMethods.modalMsg("INFO", "", "Seleccione un usuario");
			return;
		}
		ModalConfirmController ctrl = openModalConfirm();
		if(ctrl==null)return;
		
		ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow());
		ctrl.getLblMsg().setText("¿Seguro que desea cancelar el cliente:"+clteUsr.getFotografo()+" ?");
		ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptDeleteClte(clteUsr));
		
	}
	private EventHandler<MouseEvent> acceptDeleteClte(ClienteDTO objUsr) {
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					if(Flags.remote_valid) {
						remoteClteProdCostService.deleteRowByCte(objUsr.getId_fotografo());
						remoteFotografoService.deleteCliente(objUsr.getId_fotografo());
					}
					else {
						clteProdCostService.deleteRowByCte(objUsr.getId_fotografo());
						fotografoService.deleteCliente(objUsr.getId_fotografo());
					}
					if(stageBusqProd!=null) stageBusqProd.close();
					disableInputs();
					clearInputs();
					buscaCliente();
					
		        } catch(Exception ex) {
					ex.printStackTrace();
					logger.info(ex.getMessage());
				}
			}};
	}

	@FXML
	public void cancelEditClte() {
		btnAgregar.setDisable(false);
		btnEditar.setDisable(false);
		btnEliminar.setDisable(false);
		btnCancel.setVisible(false);
		btnAccept.setVisible(false);
		tblClientes.setDisable(false);
		tipoForm="";
		disableInputs();
	}
	@FXML 
	private void acceptClte() {
		
		if(!"M".equals(tipoForm) && !"A".equals(tipoForm)) {
			GeneralMethods.modalMsg("ERROR", "", "No se pudo identificar la accion a ejecutar: "+tipoForm);
			return;
		}
		
		if(inputFotografo.getText()==null || "".equals(inputFotografo.getText().trim())) {
			GeneralMethods.modalMsg("ERROR", "", "Ingrese el nombre de cliente");
			return;
		}
		

		if(cbxTipoCte.getValue()==null || "".equals(cbxTipoCte.getValue().trim())) {
			GeneralMethods.modalMsg("ERROR", "", "Ingrese el tipo de cliente");
			return;
		}
		
		
		btnAgregar.setDisable(false);
		btnEditar.setDisable(false);
		btnEliminar.setDisable(false);
		btnCancel.setVisible(false);
		btnAccept.setVisible(false);
		tblClientes.setDisable(false);
		
		Store_fotografo usuario = new Store_fotografo();
		usuario.setEstatus("ACTIVO".equals(cbxStts.getValue())?1:0);
		usuario.setTipo("AFICIONADO".equals(cbxTipoCte.getValue())?"A":"F");
		usuario.setEmail(inputCorreo.getText());
		usuario.setFotografo(inputFotografo.getText().trim());
		
		usuario.setTelefono(inputTelefono.getText());
		
		List<Store_cat_prod> lstCatProd = null;
		
		if("M".equals(tipoForm)) {
			ClienteDTO objUsr = tblClientes.getSelectionModel().getSelectedItem();
			usuario.setId_fotografo(objUsr.getId_fotografo());
		//	usuario.setPasswd(objUsr.getPasswd());
			if(Flags.remote_valid) {
				remoteFotografoService.updateCliente(usuario);
				lstCatProd =remoteStoreCatProdService.getAllCatalogoProduct();
				remoteClteProdCostService.deleteRowByCte(usuario.getId_fotografo());
				
			}
			else {
				fotografoService.updateCliente(usuario);
				lstCatProd=storeCatProdService.getAllCatalogoProduct();
				clteProdCostService.deleteRowByCte(usuario.getId_fotografo());
				
			}
		}
		else if("A".equals(tipoForm)) {
			int idFotografo =0;
			if(Flags.remote_valid) {
				idFotografo=remoteFotografoService.saveCliente(usuario);
				lstCatProd =remoteStoreCatProdService.getAllCatalogoProduct();
				remoteClteProdCostService.deleteRowByCte(idFotografo);
			}
			else {
				idFotografo=fotografoService.saveCliente(usuario);
				lstCatProd=storeCatProdService.getAllCatalogoProduct();
				clteProdCostService.deleteRowByCte(idFotografo);
			}
			usuario.setId_fotografo(idFotografo);
		}
		
		
		
		if(lstCatProd!=null ) {
			List<Store_cat_prod> lstPRod = lstCatProd.stream().filter(e -> e.getCategoria()==0).collect(Collectors.toList());
			if(lstPRod!=null) {
				
				for(Store_cat_prod prod: lstPRod) {
					String costo = "A".equals(usuario.getTipo())?String.valueOf(prod.getCosto_aficionado()):
						String.valueOf(prod.getCosto_fotografo());
					
					saveCostosByCliente(prod.getId_prod(),usuario.getId_fotografo(),costo);
				}
			}
		}
		
		disableInputs();
		clearInputs();
		buscaCliente();
	}
	
	private void saveCostosByCliente(int idProd,int idFotografo, String costo) {
		Store_cliente_prod_cost clteProdCost = null;
		
		clteProdCost = new Store_cliente_prod_cost();
		clteProdCost.setCosto(new BigDecimal(costo));//.replaceAll(",", "")
			
		clteProdCost.setId_cliente(idFotografo);
		clteProdCost.setId_prod(idProd);
			clteProdCostService.insertRow(clteProdCost);
			if(Flags.remote_valid)remoteClteProdCostService.insertRow(clteProdCost);
		
	}
		

	
	private void initForm() {
		List<String> lstStts = new ArrayList<>();
		lstStts.add("ACTIVO");
		lstStts.add("NO ACTIVO");
		
		List<String> lstTipo = new ArrayList<>();
		lstTipo.add("AFICIONADO");//A
		lstTipo.add("FOTOGRAFO");//F
		
		
		cbxStts.getItems().removeAll(cbxStts.getItems());
		cbxStts.setItems(FXCollections.observableList(lstStts));
		
		cbxTipoCte.getItems().removeAll(cbxTipoCte.getItems());
		cbxTipoCte.setItems(FXCollections.observableList(lstTipo));
		
		//cbxBusqEstatus.getItems().removeAll(cbxBusqEstatus.getItems());
		//cbxBusqEstatus.setItems(FXCollections.observableList(lstStts));
	
		inputTelefono.textProperty().addListener(GeneralMethods.onlyNumber(inputTelefono));
		
		busqClte();
	}
	
	private void busqClte() {
		containerBusqResp.setVisible(false);
		List<ClienteDTO> lstClientes = (Flags.remote_valid)?
				remoteFotografoService.getClientsByName(inputBusqCliente.getText(),"like"):
					fotografoService.getClientsByName(inputBusqCliente.getText(),"like");
		
		tblClientes.getItems().removeAll(tblClientes.getItems());
		tblClientes.setItems(FXCollections.observableList(lstClientes));
		if(lstClientes.size()==0){
			GeneralMethods.modalMsg("", "", "No se encontraron clientes");
		}
	}
	
	private void responsiveGUI() {
		/* resize de acuerdo al tamaño del Pane padre */
		colFotografo.prefWidthProperty().bind(tblClientes.widthProperty().multiply(0.3));
		colTelefono.prefWidthProperty().bind(tblClientes.widthProperty().multiply(0.2));
		colCorreo.prefWidthProperty().bind(tblClientes.widthProperty().multiply(0.3));
		colEstatus.prefWidthProperty().bind(tblClientes.widthProperty().multiply(0.2));
		
		//COLUMNAS DE PRODUCTOS DE PEDIDO
		colFotografo.setCellValueFactory(new PropertyValueFactory<ClienteDTO, String>("fotografo"));
		colTelefono.setCellValueFactory(new PropertyValueFactory<ClienteDTO, String>("telefono"));
		colCorreo.setCellValueFactory(new PropertyValueFactory<ClienteDTO, String>("correo"));
		colEstatus.setCellValueFactory(new PropertyValueFactory<ClienteDTO, String>("estatusStr"));
		
	}
	private void disableInputs() {
		inputFotografo.setDisable(true);
		inputTelefono.setDisable(true);
		inputCorreo.setDisable(true);
		cbxStts.setDisable(true);
		cbxTipoCte.setDisable(true);
	}
	private void clearInputs() {
		inputFotografo.setText("");
		inputTelefono.setText("");
		inputCorreo.setText("");
		cbxStts.setValue("");
		cbxTipoCte.setValue("");
	}
	private void enableInputs() {
		inputFotografo.setDisable(false);
		inputTelefono.setDisable(false);
		inputTelefono.setDisable(false);
		inputCorreo.setDisable(false);
		cbxStts.setDisable(false);
		cbxTipoCte.setDisable(false);
		
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
