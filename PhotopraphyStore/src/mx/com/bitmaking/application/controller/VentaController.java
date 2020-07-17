package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.entity.Store_cargo_abono;
import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.iservice.IStoreCargoAbonoService;
import mx.com.bitmaking.application.iservice.IStoreCatEstatusService;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.iservice.IStoreCorteCajaService;
import mx.com.bitmaking.application.iservice.IStoreFotografoService;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.iservice.IStoreProdPedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;
import mx.com.bitmaking.application.util.PrinterService;

@Component
//@Scope("prototype")
public class VentaController  {
	
	
	@FXML private JFXButton btnConsultPedido;
	@FXML private JFXButton  btnEditarPedido;
	@FXML private JFXButton  btnSalir;
	@FXML private JFXButton  btnCancelar;
	@FXML private JFXButton  btnGuardar;
	@FXML private JFXButton btnSelectProd;
	
	@FXML private JFXComboBox<String>  cbxEstatus;
	@FXML private JFXComboBox<String> cbxCliente;
	@FXML private JFXComboBox<String>cbxEstatusProd;
	
	@FXML private JFXTextField inputFolio;
	@FXML private JFXTextField inputCliente;
	@FXML private JFXTextField inputMontoAnt;
	@FXML private JFXTextField inputMonto;
	@FXML private JFXTextField inputTelefono;
	@FXML private JFXTextField inputProd;
	@FXML private JFXTextField inputCantProd;
	@FXML private JFXTextField inputCostoProd;
	@FXML private JFXTextField inputDesc;
	@FXML private JFXTextField inputPrecioUni;
	@FXML private JFXTextField inputBarcode;
	
	
	@FXML private AnchorPane ventaBody;
	@FXML private TableView<CostProductsDTO> tbProductos;
	

	@FXML private TableColumn<CostProductsDTO,String> tbColProd ;
	@FXML private TableColumn<CostProductsDTO,String> tbColDesc;
	@FXML private TableColumn<CostProductsDTO, Integer> tbColCant;
	@FXML private TableColumn<CostProductsDTO,BigDecimal> tbColCosto;
	@FXML private TableColumn<CostProductsDTO,String>tbColEstatus;
	@FXML private Label lblTelefono;
	
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService catProdService;
	
	@Autowired
	@Qualifier("StoreFotografoService")
	IStoreFotografoService fotografoService;
	@Autowired
	@Qualifier("StoreCatEstatusService")
	IStoreCatEstatusService catEstatusService;
	@Autowired
	@Qualifier("StorePedidoService")
	IStorePedidoService pedidoService;
	@Autowired
	@Qualifier("StoreProdPedidoService")
	IStoreProdPedidoService prodPedidoService;
	@Autowired
	@Qualifier("StoreCorteCajaService")
	IStoreCorteCajaService corteCajaService;
	
	
	@Autowired
	@Qualifier("remoteStoreCatProdService")
	IStoreCatProdService remoteCatProdService;
	@Autowired
	@Qualifier("remoteStoreFotografoService")
	IStoreFotografoService remoteFotografoService;
	@Autowired
	@Qualifier("remoteStoreCatEstatusService")
	IStoreCatEstatusService remoteCatEstatusService;
	@Autowired
	@Qualifier("remoteStorePedidoService")
	IStorePedidoService remotePedidoService;
	@Autowired
	@Qualifier("remoteStoreProdPedidoService")
	IStoreProdPedidoService remoteProdPedidoService;

	@Autowired
	@Qualifier("remoteStoreCorteCajaService")
	IStoreCorteCajaService remoteCorteCajaService;
	

	@Autowired
	@Qualifier("StoreCargoAbonoService")
	IStoreCargoAbonoService storeCargoAbonoService;
	
	@Autowired
	@Qualifier("remoteStoreCargoAbonoService")
	IStoreCargoAbonoService remoteStoreCargoAbonoService;
	
	@Autowired
	private ApplicationContext context ;

	private Stage stageBusqProd = null;
	private Stage stageBusqConfirm = null;
	private List<Store_fotografo> lstFoto = null;
	private List<Store_cat_estatus> lstEstatus = null;
	private CostProductsDTO rowProd = null;
	private UserSessionDTO instance = null;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private JFXAutoCompletePopup<String> autoCompletePopup =null;
	private boolean updateVta;
	private Store_pedido updatePedido;
	private Stage stageUpdate=null;
	/**
	 * @return the tbProductos
	 */
	public TableView<CostProductsDTO> getTbProductos() {
		return tbProductos;
	}

	/**
	 * @param tbProductos the tbProductos to set
	 */
	public void setTbProductos(TableView<CostProductsDTO> tbProductos) {
		this.tbProductos = tbProductos;
	}

	
	/**
	 * @return the updatePedido
	 */
	public Store_pedido getUpdatePedido() {
		return updatePedido;
	}

	/**
	 * @param updatePedido the updatePedido to set
	 */
	public void setUpdatePedido(Store_pedido updatePedido) {
		this.updatePedido = updatePedido;
	}

	/**
	 * @return the updateVta
	 */
	public boolean isUpdateVta() {
		return updateVta;
	}

	/**
	 * @param updateVta the updateVta to set
	 */
	public void setUpdateVta(boolean updateVta) {
		this.updateVta = updateVta;
	}

	/**
	 * @return the btnSalir
	 */
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		
		instance = UserSessionDTO.getInstance();
		
		responsiveGUI();
		setFilterPopup();// PopUp para cbxCliente
		fillCbxClte(); //llena combo de clientes
		fillCbxSttsProd();
		initForm();
		autoCompletePopup.hide();
	//	btnEliminaPedido.setVisible(false);
		//btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED,modalBusqByFolio());
		btnEditarPedido.addEventHandler(MouseEvent.MOUSE_CLICKED,modalEditPedido());
		inputCantProd.textProperty().addListener(GeneralMethods.formatInteger(inputCantProd));
		inputMonto.textProperty().addListener(GeneralMethods.formatNumber(inputMonto));
		inputTelefono.textProperty().addListener(GeneralMethods.onlyNumber(inputTelefono));
		
		inputMontoAnt.textProperty().addListener(GeneralMethods.formatNumber(inputMontoAnt));
		inputCostoProd.textProperty().addListener(GeneralMethods.formatNumber(inputCostoProd));
		
		
		
	}
	
	@FXML
	private void openGasto(){
		
		try {
			
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/TreeProduct.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/FormGasto.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			Parent sceneEdit= fxmlLoader.load();
			Scene scene = new Scene(sceneEdit,3013,165);
			scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
		
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Abono / Cargo");
			stage.setMinHeight(500.0);
			stage.setMinWidth(350.0);
			stage.setMaxHeight(500.0);
			stage.setMaxWidth(350.0);
			stage.initModality(Modality.APPLICATION_MODAL);
		
			stage.show();
			GastoController ctrl = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			ctrl.getBtnCancel().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow(stage));
			ctrl.getBtnAccept().addEventHandler(MouseEvent.MOUSE_CLICKED, saveGasto(stage,ctrl));
			
			
	    } catch(Exception ex) {
			ex.printStackTrace();
			GeneralMethods.modalMsg("ERROR", "", ex.getMessage());
		}
	}
	
	private EventHandler<MouseEvent> saveGasto(Stage stage,GastoController ctrl){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(stage!=null) 
					stage.close();
				Store_cargo_abono cargoObj = new Store_cargo_abono();
				cargoObj.setFecha(new Date());
				cargoObj.setMonto(new BigDecimal(ctrl.getInputMonto().getText()));
				cargoObj.setMotivo(ctrl.getInputConcepto().getText());
				cargoObj.setId_sucursal(instance.getId_sucursal());
				if("Cargo".equals(ctrl.getCbxTipoGasto().getValue())){
					cargoObj.setTipo("C");
				}
				else if("Abono".equals(ctrl.getCbxTipoGasto().getValue())){
					cargoObj.setTipo("A");
				}
				
				if(!Flags.remote_valid){
					storeCargoAbonoService.saveCargoAbono(cargoObj);
				}else{
					remoteStoreCargoAbonoService.saveCargoAbono(cargoObj);
				}
			}
		};
		
	}
	
	private void fillCbxSttsProd(){
		
		List<String> lstStts = new ArrayList<>();
		lstStts.add("PENDIENTE");
		lstStts.add("TERMINADO");
		cbxEstatusProd.setItems(FXCollections.observableArrayList(lstStts));
		cbxEstatusProd.setValue("PENDIENTE");
	}
	private void generateFolio(){
		String prefijo =// Flags.remote_valid?
					//remotePedidoService.getCurrentNumberFolio(instance.getPrefijo()):
					pedidoService.getCurrentNumberFolio(instance.getPrefijo());
		
		inputFolio.setText(prefijo);
	}
	private void initForm() {
		
		getLstEstatus();//Llena combo de estatus
		
		if(instance !=null && !updateVta) {
			generateFolio();//Genera Folio de acuerdo a la sucursal de usr
		}
		
		inputMontoAnt.setText("0");
		
		cbxEstatus.setValue("PENDIENTE");
		inputTelefono.setText("");
		inputCliente.setText("");
	//	cbxCliente.getEditor().setText("");
		inputCliente.setDisable(false);
		inputMonto.setText("0");
		inputMontoAnt.setText("0");
		inputDesc.setText("");
		inputCantProd.setText("1");
		inputCostoProd.setText("0");
		inputProd.setText("");
		
		tbProductos.getItems().removeAll(tbProductos.getItems());
		tbColProd.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("bar_code"));
		tbColDesc.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("producto"));
		tbColCant.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, Integer>("cantidad"));
		tbColCosto.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, BigDecimal>("costo"));
		tbColEstatus.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("estatus"));
	}
	
	@FXML private void cancelPedido(){
		ModalConfirmController ctrl = openModalConfirm();
		if(ctrl==null)return;
		
		ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow(stageBusqConfirm));
		ctrl.getLblMsg().setText("¿Seguro que desea cancelar pedido?");
		ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptCancelPedido(stageBusqConfirm));
	}

	@FXML private void confirmPedido(){
		if(inputMonto.getText() ==null || "0".equals(inputMonto.getText().trim())) {
			GeneralMethods.modalMsg("ERROR", "", "El monto total es de cero pesos, agregue productos");
			return;
		}
		int idxSelected = cbxCliente.getSelectionModel().getSelectedIndex();
		if(idxSelected <0) {
			GeneralMethods.modalMsg("ERROR", "", "Seleccione un cliente y agregue productos");
			return;
		}
		/*
		if(( inputCliente.getText() ==null || "".equals(inputCliente.getText().trim()))) {
			
			GeneralMethods.modalMsg("ERROR", "", "Agregue el nombre del cliente");
			return;
		}*/
		idxSelected = cbxEstatus.getSelectionModel().getSelectedIndex();
		if(idxSelected <0) {
			GeneralMethods.modalMsg("ERROR", "", "Seleccione un estatus para el pedido");
			return;
		}
		String montoAnt=inputMontoAnt.getText().replace(",", "");
		String monto=inputMonto.getText().replace(",", "");
		if(inputMontoAnt.getText() !=null && inputMontoAnt.getText().length()>0 && Double.parseDouble(montoAnt) > Double.parseDouble(monto) ) {
			GeneralMethods.modalMsg("ERROR", "", "Valide que el monto anticipo no sea mayor al importe total");
			return;
		}
		
		ModalConfirmController ctrl = openModalConfirm();
		if(ctrl==null)return;
		
		ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow(stageBusqConfirm));
		ctrl.getLblMsg().setText("¿Confirmar Pedido?");
		ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptPedido(stageBusqConfirm));
	}
	
	private EventHandler<MouseEvent> acceptPedido(Stage stageBusqProd) {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					Store_pedido pedidoObj = new Store_pedido();
					String montoAnt = inputMontoAnt.getText();
					String montoTot = inputMonto.getText();
					if(inputMontoAnt.getText() ==null){
						montoAnt = "0";
					}
					montoAnt= montoAnt.replaceAll("[^0-9\\.]", "");
					montoTot= montoTot.replaceAll("[^0-9\\.]", "");
					if(inputMontoAnt.getText() ==null || "".equals(montoAnt.trim())) {
						montoAnt="0";
					}
					
					pedidoObj.setFolio(inputFolio.getText());
					if((Constantes.CLTE_GRAL).equals(cbxCliente.getEditor().getText())) {
						pedidoObj.setCliente(Constantes.CLTE_GRAL+" "+inputCliente.getText());
						
					}else
						pedidoObj.setCliente(inputCliente.getText());
					pedidoObj.setTelefono(inputTelefono.getText());
					pedidoObj.setDescripcion(inputDesc.getText());
					pedidoObj.setFec_pedido(sdf.parse(sdf.format(new Date())));
					pedidoObj.setMonto_ant(new BigDecimal(montoAnt));
					pedidoObj.setMonto_total(new BigDecimal(montoTot));
					pedidoObj.setId_estatus(cbxEstatus.getSelectionModel().getSelectedIndex()+1);
					
					if(pedidoObj.getId_estatus() ==1) {
						pedidoObj.setFec_entregado(sdf.parse(sdf.format(new Date())));
					}
					if(updateVta){
						if(updatePedido != null ){
							//ELIMINA REGISTROS YA EXISTENTES
							prodPedidoService.deleteByFolio(updatePedido.getFolio());
							if(Flags.remote_valid)remoteProdPedidoService.deleteByFolio(updatePedido.getFolio());
							
							pedidoObj.setFolio(updatePedido.getFolio());
							
						}
					}
					ObservableList<CostProductsDTO> lstProds = tbProductos.getItems();
					/* IMPRIME TICKET */
					String layoutPrinter= PrinterService.ticketLayout(Constantes.MAX_CHARS_TICKET, lstProds, 
												instance);
					pedidoObj.setTicket(layoutPrinter.getBytes());
					
					pedidoService.guardaPedido(pedidoObj);
					if(Flags.remote_valid)remotePedidoService.guardaPedido(pedidoObj);
					
					
					Store_prod_pedido product = null;
					for(CostProductsDTO el:lstProds) {
						product = new Store_prod_pedido();
						product.setBar_code(el.getBar_code());
						product.setCantidad(el.getCantidad());
						product.setCosto_total(el.getCosto());
						product.setEstatus(el.getEstatus());
						//System.out.println("cost_uni_adding:"+el.getCostoUnitario());
						product.setCosto_unitario(el.getCostoUnitario());
						product.setDescripcion(el.getProducto());
						product.setFolio(pedidoObj.getFolio());
						
						prodPedidoService.guardaProdsByPedido(pedidoObj.getFolio(), product);
						if(Flags.remote_valid)remoteProdPedidoService.guardaProdsByPedido(pedidoObj.getFolio(), product);
					}
					if(stageBusqProd!=null) stageBusqProd.close();
					
					/* REINICIA FORMULARIO */
					if(updateVta){
						//
						if(stageUpdate !=null)stageUpdate.close();
					}else{
						initForm();
						cbxCliente.setVisibleRowCount(10);
						cbxCliente.setMaxHeight(Double.MAX_VALUE);
						cbxCliente.getEditor().setText("");
						cbxCliente.setValue(Constantes.CLTE_GRAL);
						cbxCliente.hide();
						autoCompletePopup.hide();
					}
					System.out.println(layoutPrinter);
					PrinterService.printTicket(Constantes.PRINTER_NAME, layoutPrinter);
					
					
					
		        } catch(Exception ex) {
					ex.printStackTrace();
					GeneralMethods.modalMsg("ERROR", "", ex.getMessage());
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
			stageBusqConfirm = new Stage();
			stageBusqConfirm.setScene(scene);
			stageBusqConfirm.setTitle("Confirmación");
			stageBusqConfirm.setMinHeight(200.0);
			stageBusqConfirm.setMinWidth(350.0);
			stageBusqConfirm.setMaxHeight(200.0);
			stageBusqConfirm.setMaxWidth(350.0);
			stageBusqConfirm.initModality(Modality.APPLICATION_MODAL); 
			stageBusqConfirm.show();
			ctrl = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			
	    } catch(Exception ex) {
			ex.printStackTrace();
			GeneralMethods.modalMsg("ERROR", "", ex.getMessage());
		}
		return ctrl;
	}
	private void getLstEstatus() {
		cbxEstatus.getItems().removeAll(cbxEstatus.getItems());
		lstEstatus = (Flags.remote_valid)?remoteCatEstatusService.getListEstatus():catEstatusService.getListEstatus();
		String[] arrayStts = new String[lstEstatus.size()];
		for(int i=0; i<lstEstatus.size();i++) {
			arrayStts[i] = lstEstatus.get(i).getEstatus();
		}
		cbxEstatus.setItems(FXCollections.observableArrayList(arrayStts));
	}
	@FXML 
	private void selectCte() {
		int idxClte = cbxCliente.getSelectionModel().getSelectedIndex() ;
		inputTelefono.setText("");
		if(idxClte ==0){
			inputTelefono.setVisible(true);
			lblTelefono.setVisible(true);
		}else{
			inputTelefono.setVisible(false);
			lblTelefono.setVisible(false);
		}
		setClte();
	}
	
	private void setClte() {
		
		int idxClte = cbxCliente.getSelectionModel().getSelectedIndex() ;
		if(lstFoto ==null || idxClte<0) {
			inputCliente.setDisable(true);
			return;
		}
		String clteSelected = cbxCliente.getEditor().getText();
		inputCliente.setText("");
		System.out.println("clteSelected: "+clteSelected);
		System.out.println("idxClte: "+idxClte);
		System.out.println("cbxCliente.getEditor().getText():"+cbxCliente.getEditor().getText());
		System.out.println("cbxCliente.getPromptText():"+cbxCliente.getPromptText());
		System.out.println("cbxCliente.getSelectionModel().getSelectedIndex():"+cbxCliente.getSelectionModel().getSelectedIndex());
	//	if(idxClte ==0 ) {
		if(Constantes.CLTE_GRAL.equals(clteSelected.trim())){
			inputCliente.setDisable(false);
			cbxCliente.setValue(Constantes.CLTE_GRAL);
			cbxCliente.hide();
			autoCompletePopup.hide();
		}else {
			String item = "";
			for (Store_fotografo el : lstFoto) {
		        if (clteSelected.toUpperCase().equals(el.getFotografo().toUpperCase().trim())) {
		        	item =el.getFotografo();
		        	break;
		        }
		    }
			System.out.println("item:"+item);
			if(!"".equals(item.trim())) {
				inputCliente.setDisable(true);
				inputCliente.setText(item);//(lstFoto.get((idxClte)).getFotografo());
				cbxCliente.setValue(item);
				cbxCliente.hide();
				autoCompletePopup.hide();
			}
			
		}
	}
	/*
	@FXML
	private void inputSelectCte(KeyEvent keyEvent) {
		cbxCliente.show();
		cbxCliente.setMaxHeight(Double.MAX_VALUE);
		System.out.println("inputSelectCte 1:"+keyEvent.getCode().toString());
		System.out.println("inputSelectCte 1_1:"+keyEvent.getText());
		System.out.println("inputSelectCte 2_2:"+keyEvent.getEventType().getName());
		
       
		if (keyEvent.getCode() == KeyCode.ENTER){
			setClte();
		}
	}*/
	private void setFilterPopup() {
		autoCompletePopup = new JFXAutoCompletePopup<>();
		autoCompletePopup.setSelectionHandler(event -> {
			cbxCliente.setValue(event.getObject());
		});
		
		TextField editor = cbxCliente.getEditor();
		editor.textProperty().addListener(observable -> {
		    //The filter method uses the Predicate to filter the Suggestions defined above
		    //I choose to use the contains method while ignoring cases
		    autoCompletePopup.filter(item -> item.toLowerCase().contains(editor.getText().toLowerCase()));
		    //Hide the autocomplete popup if the filtered suggestions is empty or when the box's original popup is open
		    if (autoCompletePopup.getFilteredSuggestions().isEmpty() 
		    	|| editor.getText().trim().length()==0) {
		        autoCompletePopup.hide();
		    } 
		    else {
		    	System.out.println("show popup");
		        autoCompletePopup.show(editor);
		    }
		});
	}
	private void fillCbxClte() {
		cbxCliente.getItems().removeAll(cbxCliente.getItems());
		cbxCliente.setEditable(true);
		
		
		System.out.println("es remoto?"+Flags.remote_valid);
		lstFoto = (Flags.remote_valid)?remoteFotografoService.getActiveClients():fotografoService.getActiveClients();
		String[] arrayClte = new String[lstFoto.size()];
		
		int idx=0;
		for(Store_fotografo el: lstFoto){
			arrayClte[idx++] = el.getFotografo().trim();
		}
		cbxCliente.setItems(FXCollections.observableArrayList(arrayClte));
		cbxCliente.setValue(Constantes.CLTE_GRAL);
		
		if(autoCompletePopup.getSuggestions() !=null)
			autoCompletePopup.getSuggestions().removeAll(autoCompletePopup.getSuggestions());
		autoCompletePopup.getSuggestions().addAll(cbxCliente.getItems());
		autoCompletePopup.hide();
		/*
		FilteredList<String> filteredItems = new FilteredList<String>(FXCollections.observableArrayList(arrayClte), p -> true);
	
		cbxCliente.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			
            final TextField editor = cbxCliente.getEditor();
            final String selected = cbxCliente.getSelectionModel().getSelectedItem();
          
            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
          //  	cbxCliente.hide();
           // 	cbxCliente.show();
            	
            	cbxCliente.setMaxHeight(Double.MAX_VALUE);
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.toUpperCase().equals(editor.getText().toUpperCase())) {
                	
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if (item.toUpperCase().contains(newValue.toUpperCase())) {
                        //	cbxCliente.hide();
                        	cbxCliente.setMaxHeight(Double.MAX_VALUE);
                        	cbxCliente.setVisibleRowCount(15);
                        	
                        	cbxCliente.show();
                        	return true;
                            
                        } else {
                            return false;
                        }
                    });
                }
                
                
            });	
		});
		cbxCliente.setItems(filteredItems);
		cbxCliente.setVisibleRowCount(10);
		cbxCliente.setMaxHeight(Double.MAX_VALUE);
		//cbxCliente.getEditor().setText(Constantes.CLTE_GRAL);
		cbxCliente.setValue(Constantes.CLTE_GRAL);
		cbxCliente.show();cbxCliente.hide();
		*/
		
	}
	
	@FXML
	private void openTreProd() {
		try {
			int idxClte = cbxCliente.getSelectionModel().getSelectedIndex();
			if(idxClte <0) {
				GeneralMethods.modalMsg("", "", "Debes seleccionar un cliente");
				return;
			}
			
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/TreeProduct.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/SelectProductsVta.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			Parent sceneEdit= fxmlLoader.load();
			Scene scene = new Scene(sceneEdit,3013,165);
			scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/GestionProductos.css").toExternalForm());
			stageBusqConfirm = new Stage();
			stageBusqConfirm.setScene(scene);
			stageBusqConfirm.setTitle("Selecciona Producto ");
			stageBusqConfirm.setMinHeight(636.0);
			stageBusqConfirm.setMinWidth(565.0);
			stageBusqConfirm.setMaxHeight(536.0);
			stageBusqConfirm.setMaxWidth(765.0);
			//stageBusqProd.setMaxHeight(200.0);
		//	stageBusqProd.setMaxWidth(300.0);
			stageBusqConfirm.initModality(Modality.APPLICATION_MODAL); 
			stageBusqConfirm.show();
			SelectProductoVtaController busqProd = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			
			busqProd.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED, closeSelectProd(busqProd,stageBusqConfirm));
			
			busqProd.getBtnAcceptModif().addEventHandler(MouseEvent.MOUSE_CLICKED,getProductoSelected(busqProd,stageBusqConfirm));

			
			int idClte = 0;
			if(idxClte >0) {
				idClte=lstFoto.get((idxClte)).getId_fotografo();
			}
			busqProd.setIdCliente(idClte);
			busqProd.getTblCatProducts();
			
	    } catch(Exception ex) {
			ex.printStackTrace();
			GeneralMethods.modalMsg("ERROR", "", ex.getMessage());
		}
	}
	
	private EventHandler<MouseEvent> closeSelectProd(SelectProductoVtaController busqProd,Stage stageBusqProd) {
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					
					busqProd.getTblProducto().getItems().removeAll(busqProd.getTblProducto().getItems());
					if(stageBusqProd!=null) stageBusqProd.close();
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}

	private EventHandler<MouseEvent> acceptCancelPedido(Stage stageBusqConfirm) {
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					initForm();
					//cbxCliente.getEditor().setText(Constantes.CLTE_GRAL);
					cbxCliente.setValue(Constantes.CLTE_GRAL);
					if(stageBusqConfirm!=null) stageBusqConfirm.close();
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	
	@FXML private void addProdByBarcodeOnEnter(KeyEvent keyEvent) {
		
		if(keyEvent.getCode() == KeyCode.ENTER){
			if(inputBarcode.getText()==null || "".equals(inputBarcode.getText())){
				return;
			}
			int idxClte = cbxCliente.getSelectionModel().getSelectedIndex();
			if(idxClte <0) {
				GeneralMethods.modalMsg("", "", "Debes seleccionar un cliente");
				return;
			}
			
			CostProductsDTO obj = null;
			int idClte = 0;
			if(idxClte >0) {
				idClte=lstFoto.get((idxClte)).getId_fotografo();
			}
			
			if(!Flags.remote_valid)
				obj  =catProdService.getCatByClteAndBarcode(idClte,inputBarcode.getText());
			else 
				obj=remoteCatProdService.getCatByClteAndBarcode(idClte,inputBarcode.getText());
			
			if(obj != null){
				String costo = "0";
			
				if(obj.getCosto() ==null ||	obj.getCosto().equals(new BigDecimal(0)) ){
					costo =inputCantProd.getText().replaceAll("[^0-9\\.]", "");
					if("".equals(costo) ||
							Double.parseDouble(costo)<=0){
						GeneralMethods.modalMsg("", "", "No se ha agregado un costo al producto, ingrese el costo del producto mayor a cero.");
						return;
					}
				}else{
					costo = String.valueOf(obj.getCosto());
				}
				
				CostProductsDTO auxObj = new CostProductsDTO();
				auxObj.setCantidad(inputCantProd.getText()==null || "".equals(inputCantProd.getText().trim())?
										1:Integer.parseInt(inputCantProd.getText().trim()));
				auxObj.setCosto(new BigDecimal(Double.parseDouble(costo)*auxObj.getCantidad()).setScale(2,BigDecimal.ROUND_HALF_EVEN));
				auxObj.setProducto(obj.getProducto());
				auxObj.setCostoUnitario(obj.getCosto());
				auxObj.setBar_code(obj.getBar_code());
				auxObj.setId_prod(obj.getId_prod());
				auxObj.setId_padre_prod(obj.getId_padre_prod());
				auxObj.setId_padre_prod(obj.getId_padre_prod());
				auxObj.setEstatus(cbxEstatusProd.getValue());
				
				if(!hasProduct(auxObj)){
					tbProductos.getItems().add(auxObj);
				}
				updateCostoTotal();
			}
			else{
				GeneralMethods.modalMsg("", "", "No se encontr\u00F3 producto relacionado con el c\u00F3digo de barras");
			}
			inputBarcode.setText("");
		}
	}
	@FXML private void addProdToTableOnEnter(KeyEvent keyEvent) {
		if(keyEvent.getCode() == KeyCode.ENTER){
			addProdToTable();
		}
	}
	@FXML private void addProdToTable() {
		String cant =inputCantProd.getText();
		cant = cant.replaceAll("[^0-9]", ""); 
		if(cant==null || cant.trim().length()==0 || Integer.parseInt(cant)<=0) {
			GeneralMethods.modalMsg("", "", "Ingrese una cantidad mayor a cero.");
			return;
		}
		if(inputCostoProd.getText()==null || inputCostoProd.getText().trim().length()==0 
				
				|| !GeneralMethods.validDecimal(inputCostoProd.getText())) {
			GeneralMethods.modalMsg("", "", "Ingrese el precio del producto de acuerdo a la cantidad");
			return;
		}
		String costAux= inputCostoProd.getText();
		costAux = costAux.replaceAll("[^0-9\\.]", "");
		
		if(Double.parseDouble(costAux)<=0){
			GeneralMethods.modalMsg("", "", "Para poder agregar el producto, debe tener precio de venta");
			return;
		}
		if(rowProd==null) {
			GeneralMethods.modalMsg("", "", "No se tiene un producto seleccionado");
			return;
		}
		
		CostProductsDTO auxObj = rowProd;
		
		auxObj.setCantidad(Integer.parseInt(cant));
		auxObj.setCosto(new BigDecimal(auxObj.getCantidad()*(Double.parseDouble(costAux))).setScale(2,BigDecimal.ROUND_HALF_EVEN));
		auxObj.setProducto(inputProd.getText());
		auxObj.setCostoUnitario(new BigDecimal(inputCostoProd.getText()));
		auxObj.setBar_code(inputBarcode.getText());
		auxObj.setEstatus(cbxEstatusProd.getValue());
		//System.out.println("Costo Unitario:"+auxObj.getCostoUnitario());
		if(!hasProduct(auxObj)){
			tbProductos.getItems().add(auxObj);
		}
		updateCostoTotal();
		//
		rowProd=null;
		inputCantProd.setText("1");
		inputCostoProd.setText("");
		inputProd.setText("");
		inputCostoProd.setText("");
		inputBarcode.setText("");
	}
	
	@FXML private void quitProdToTable() {
		int idx = tbProductos.getSelectionModel().getSelectedIndex();
		System.out.println("IDX:"+idx);
		if(idx<0) {
			return;
		}
		tbProductos.getItems().remove(tbProductos.getSelectionModel().getSelectedIndex());
		updateCostoTotal();
	}
	private void updateCostoTotal() {
		ObservableList<CostProductsDTO> rowsProd = tbProductos.getItems();
		BigDecimal costTotal = new BigDecimal(0.0);
		for(CostProductsDTO el:rowsProd) {
			//System.out.println(el.getCosto());
			costTotal = costTotal.add(el.getCosto()).setScale(2,BigDecimal.ROUND_HALF_EVEN);
		}
		System.out.println(costTotal);
		inputMonto.setText(String.valueOf(costTotal));
	}

	/**
	 * Obtiene datos de la tabla de productos seleccionados
	 * @param busqProd
	 * @return
	 */
	private EventHandler<MouseEvent> getProductoSelected(SelectProductoVtaController busqProd,Stage stageBusqProd) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				int rowSelected = busqProd.getTblProducto().getSelectionModel().getSelectedIndex();
				if(rowSelected<0)return;
				
				rowProd= busqProd.getLstProd().get(rowSelected);
			//	String cost = String.valueOf(rowProd.getCosto());
				
				inputCostoProd.setText((rowProd.getCosto()==null )?"0":String.valueOf(rowProd.getCosto()));
				inputProd.setText(rowProd.getProducto());
				inputPrecioUni.setText((rowProd.getCostoUnitario()==null )?"0":String.valueOf(rowProd.getCostoUnitario()));
				inputBarcode.setText(rowProd.getBar_code());
				
				busqProd.getTblProducto().getItems().removeAll(busqProd.getTblProducto().getItems());
				
				if(stageBusqProd!=null) stageBusqProd.close();
			}
		};
	}

	private EventHandler<MouseEvent> closeWindow(Stage stageBusqProd) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Entra closeWindows");
				if(stageBusqProd!=null) 
					stageBusqProd.close();
			}
		};


	}

	public EventHandler<MouseEvent> modalEditPedido() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/BusqPedidoReporte.fxml"));
						fxmlLoader.setControllerFactory(context::getBean);
						Parent sceneEdit= fxmlLoader.load();
						Scene scene = new Scene(sceneEdit,850,570);
						scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/BusqPedidoReporte.css").toExternalForm());
						stageBusqProd = new Stage();
						stageBusqProd.setScene(scene);
						stageBusqProd.setTitle("Busqueda de Pedido ");
						stageBusqProd.setMinHeight(570.0);
						stageBusqProd.setMinWidth(850.0);
						//stageBusqProd.setWidth(850.0);
					//	stageBusqProd.setMaxHeight(470.0);
					//	stageBusqProd.setMaxWidth(770.0);
						stageBusqProd.initModality(Modality.APPLICATION_MODAL); 

						BusqPedidoRepController ctrller = fxmlLoader.getController(); //Obtiene controller de la nueva ventana

						ctrller.getBtnExportXls().setVisible(false);
						ctrller.getBtnModify().setVisible(true);
						ctrller.getBtnModifProd().setVisible(true);
						ctrller.getBtnSendSMS().setVisible(true);
						//ctrller.getContentProdPed().setVisible(false);
						ctrller.getTblProducts().setEditable(false);
						ctrller.getBtnImpTicket().setVisible(true);
						ctrller.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow(stageBusqProd));
						
						//AnchorPane.setBottomAnchor(ctrller.getContentPedido(),0.0);
						
						stageBusqProd.show();
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
		tbColProd.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.2));//0.2 --Para ocultar se pone en ceros
		tbColDesc.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.3)); //0.4 antes de ocultar barcode
		tbColCant.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.1));
		tbColCosto.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.2));
		tbColEstatus.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.2));
		
	}

	public void setValuesToUpdate(Store_pedido pedido,Stage stage) {
		if(updatePedido != null){
			stageUpdate= stage;
			cbxCliente.setValue(updatePedido.getCliente().contains(Constantes.CLTE_GRAL)?Constantes.CLTE_GRAL:updatePedido.getCliente());
			inputMonto.setText(String.valueOf(updatePedido.getMonto_total()));
			inputTelefono.setText(updatePedido.getTelefono());
			inputMontoAnt.setText(String.valueOf(updatePedido.getMonto_ant()));
			inputDesc.setText(updatePedido.getDescripcion());
			inputFolio.setText(updatePedido.getFolio());
			inputCliente.setText(updatePedido.getCliente().replace(Constantes.CLTE_GRAL, "").trim());
			
			cbxEstatus.setValue(lstEstatus.get(updatePedido.getId_estatus()-1).getEstatus()); 
			cbxEstatus.setDisable(true);
			cbxCliente.setDisable(true);
			inputCliente.setDisable(true);
			inputDesc.setDisable(true);
			inputTelefono.setDisable(true);
			btnCancelar.setDisable(true);
			autoCompletePopup.hide();
			
			List<Store_prod_pedido> rows = prodPedidoService.getListProdPedidos("('"+updatePedido.getFolio()+"')");
			if(Flags.remote_valid)rows=remoteProdPedidoService.getListProdPedidos("('"+updatePedido.getFolio()+"')");
			
			tbProductos.getItems().removeAll(tbProductos.getItems());
			CostProductsDTO product = null;
			for(Store_prod_pedido el:rows) {
				product = new CostProductsDTO();
				product.setBar_code(el.getBar_code());
				product.setCantidad(el.getCantidad());
				product.setCosto(el.getCosto_total());
				product.setCostoUnitario(el.getCosto_unitario());
				product.setProducto(el.getDescripcion());
				product.setEstatus(el.getEstatus());
			//	product.setFolio(el.getFolio());
				
				tbProductos.getItems().add(product);
			}
			
		}
		
	}

	private boolean hasProduct(CostProductsDTO product) {
		ObservableList<CostProductsDTO> items = tbProductos.getItems();
		int cantidad=0;
		
		for(int i=0; i<tbProductos.getItems().size(); i++){
			if(tbProductos.getItems().get(i).getId_prod() ==product.getId_prod() ||
					tbProductos.getItems().get(i).getBar_code().equals(product.getBar_code())){
				cantidad = tbProductos.getItems().get(i).getCantidad()+product.getCantidad();
				
				tbProductos.getItems().get(i).setCantidad(cantidad);
				
				tbProductos.getItems().get(i).setCosto(product.getCostoUnitario().
														multiply(new BigDecimal(cantidad)) );
				tbProductos.getItems().get(i).setEstatus(cbxEstatusProd.getValue());
				tbProductos.getColumns().get(0).setVisible(false);
				tbProductos.getColumns().get(0).setVisible(true);
			//	String costTot = inputMonto.getText().replaceAll("[\\,]", "");
			//	double costTotal = Double.parseDouble(costTot);
			//	inputMonto.setText(String.valueOf(product.getCostoUnitario().add(new BigDecimal(costTotal))));
				return true;
			}
		}
		return false;
	}

}
