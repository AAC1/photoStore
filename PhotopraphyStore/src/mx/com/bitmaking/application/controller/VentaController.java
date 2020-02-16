package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.service.IStoreCatEstatusService;
import mx.com.bitmaking.application.service.IStoreCatProdService;
import mx.com.bitmaking.application.service.IStoreFotografoService;
import mx.com.bitmaking.application.service.IStorePedidoService;
import mx.com.bitmaking.application.service.IStoreProdPedidoService;
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
	
	@Autowired
	@Qualifier("StoreCatProdService")
	IStoreCatProdService catProdService;
	
	@Autowired
	IStoreFotografoService fotografoService;
	@Autowired
	IStoreCatEstatusService catEstatusService;
	@Autowired
	IStorePedidoService pedidoService;
	@Autowired
	IStoreProdPedidoService prodPedidoService;
	
	@Autowired
	 private ApplicationContext context ;
	
	Stage stageBusqProd = null;
	List<Store_fotografo> lstFoto = null;
	List<Store_cat_estatus> lstEstatus = null;
	CostProductsDTO rowProd = null;
	UserSessionDTO instance = null;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @return the btnSalir
	 */
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		instance = UserSessionDTO.getInstance();
		responsiveGUI();
		initForm();
		
	//	btnEliminaPedido.setVisible(false);
		//btnSalir.addEventHandler(MouseEvent.MOUSE_CLICKED,modalBusqByFolio());
		btnEditarPedido.addEventHandler(MouseEvent.MOUSE_CLICKED,modalEditPedido());
		inputCantProd.textProperty().addListener(GeneralMethods.formatInteger(inputCantProd));
		inputMonto.textProperty().addListener(GeneralMethods.formatNumber(inputMonto));
		inputTelefono.textProperty().addListener(GeneralMethods.onlyNumber(inputTelefono));
		
		inputMontoAnt.textProperty().addListener(GeneralMethods.formatNumber(inputMontoAnt));
		inputCostoProd.textProperty().addListener(GeneralMethods.formatNumber(inputCostoProd));
	}
	
	private void generateFolio(){
		String prefijo = pedidoService.getCurrentNumberFolio(instance.getPrefijo());
		inputFolio.setText(prefijo);
	}
	private void initForm() {
		fillCbxClte(); //llena combo de clientes
		getLstEstatus();//Llena combo de estatus
		if(instance !=null ) {
			generateFolio();//Genera Folio de acuerdo a la sucursal de usr
		}
		
		inputMontoAnt.setText("0");
		cbxEstatus.setValue("");
		//cbxEstatus.setValue("Pendiente");
		inputTelefono.setText("");
		inputCliente.setText("");
		inputMonto.setText("0");
		inputMontoAnt.setText("0");
		inputDesc.setText("");
		inputCantProd.setText("0");
		inputCostoProd.setText("0");
		inputProd.setText("");
		
		tbProductos.getItems().removeAll(tbProductos.getItems());
		tbColProd.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("bar_code"));
		tbColDesc.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, String>("producto"));
		tbColCant.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, Integer>("cantidad"));
		tbColCosto.setCellValueFactory(new PropertyValueFactory<CostProductsDTO, BigDecimal>("costo"));
	}
	
	@FXML private void cancelPedido(){
		ModalConfirmController ctrl = openModalConfirm();
		if(ctrl==null)return;
		
		ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow());
		ctrl.getLblMsg().setText("¿Seguro que desea cancelar pedido?");
		ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptCancelPedido());
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
		if(idxSelected ==0 &&( inputCliente.getText() ==null || "".equals(inputCliente.getText().trim()))) {
			GeneralMethods.modalMsg("ERROR", "", "Agregue el nombre del cliente");
			return;
		}
		idxSelected = cbxEstatus.getSelectionModel().getSelectedIndex();
		if(idxSelected <0) {
			GeneralMethods.modalMsg("ERROR", "", "Seleccione un estatus para el pedido");
			return;
		}
		
		ModalConfirmController ctrl = openModalConfirm();
		if(ctrl==null)return;
		
		ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow());
		ctrl.getLblMsg().setText("¿Confirmar Pedido?");
		ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptPedido());
	}
	
	private EventHandler<MouseEvent> acceptPedido() {
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
					pedidoService.guardaPedido(pedidoObj);
					
					ObservableList<CostProductsDTO> lstProds = tbProductos.getItems();
					Store_prod_pedido product = null;
					for(CostProductsDTO el:lstProds) {
						product = new Store_prod_pedido();
						product.setBar_code(el.getBar_code());
						product.setCantidad(el.getCantidad());
						product.setCosto_total(el.getCosto());
						product.setCosto_unitario(el.getCostoUnitario());
						product.setDescripcion(el.getProducto());
						
						prodPedidoService.guardaProdsByPedido(pedidoObj.getFolio(), product);
					}
					initForm();
					if(stageBusqProd!=null) stageBusqProd.close();
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
		}
		return ctrl;
	}
	private void getLstEstatus() {
		cbxEstatus.getItems().removeAll(cbxEstatus.getItems());
		lstEstatus = catEstatusService.getListEstatus();
		String[] arrayStts = new String[lstEstatus.size()];
		for(int i=0; i<lstEstatus.size();i++) {
			arrayStts[i] = lstEstatus.get(i).getEstatus();
		}
		cbxEstatus.setItems(FXCollections.observableArrayList(arrayStts));
	}
	@FXML 
	private void selectCte() {
		
		int idxClte = cbxCliente.getSelectionModel().getSelectedIndex() ;
		if(lstFoto ==null || idxClte<0) {
			inputCliente.setDisable(true);
			return;
		}
		
		inputCliente.setText("");
		System.out.println("idxClte: "+idxClte);
		if(idxClte ==0 ) {
			inputCliente.setDisable(false);
		}else {
			inputCliente.setDisable(true);
			inputCliente.setText(lstFoto.get((idxClte)).getFotografo());
		}
	}
	
	private void fillCbxClte() {
		cbxCliente.getItems().removeAll(cbxCliente.getItems());
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
			
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/TreeProduct.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/SelectProductsVta.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			Parent sceneEdit= fxmlLoader.load();
			Scene scene = new Scene(sceneEdit,3013,165);
			scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/GestionProductos.css").toExternalForm());
			stageBusqProd = new Stage();
			stageBusqProd.setScene(scene);
			stageBusqProd.setTitle("Selecciona Producto ");
			stageBusqProd.setMinHeight(636.0);
			stageBusqProd.setMinWidth(565.0);
			stageBusqProd.setMaxHeight(536.0);
			stageBusqProd.setMaxWidth(765.0);
			//stageBusqProd.setMaxHeight(200.0);
		//	stageBusqProd.setMaxWidth(300.0);
			stageBusqProd.initModality(Modality.APPLICATION_MODAL); 
			stageBusqProd.show();
			SelectProductoVtaController busqProd = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			
			busqProd.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED, closeSelectProd(busqProd));
			
			busqProd.getBtnAcceptModif().addEventHandler(MouseEvent.MOUSE_CLICKED,getProductoSelected(busqProd));

			
			int idClte = 0;
			if(idxClte >0) {
				idClte=lstFoto.get((idxClte)).getId_fotografo();
			}
			busqProd.setIdCliente(idClte);
			busqProd.getTblCatProducts();
			
	    } catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private EventHandler<MouseEvent> closeSelectProd(SelectProductoVtaController busqProd) {
		
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

	private EventHandler<MouseEvent> acceptCancelPedido() {
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					initForm();
					if(stageBusqProd!=null) stageBusqProd.close();
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}

	@FXML private void addProdToTable() {
		String cant =inputCantProd.getText();
		cant = cant.replaceAll("[^0-9]", ""); 
		if(cant==null || cant.trim().length()==0) {
			GeneralMethods.modalMsg("", "", "Ingrese una cantidad");
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
		auxObj.setCosto(new BigDecimal(auxObj.getCantidad()*(Double.parseDouble(costAux))));
		auxObj.setProducto(inputProd.getText());
		auxObj.setCostoUnitario(new BigDecimal(inputPrecioUni.getText()));
		auxObj.setBar_code(inputBarcode.getText());
		
		tbProductos.getItems().add(auxObj);
		rowProd=null;
		inputCantProd.setText("");
		inputCostoProd.setText("");
		inputProd.setText("");
		inputCostoProd.setText("");
		updateCostoTotal();
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
			System.out.println(el.getCosto());
			costTotal = costTotal.add(el.getCosto());
		}
		System.out.println(costTotal);
		inputMonto.setText(String.valueOf(costTotal));
	}

	/**
	 * Obtiene datos de la tabla de productos seleccionados
	 * @param busqProd
	 * @return
	 */
	private EventHandler<MouseEvent> getProductoSelected(SelectProductoVtaController busqProd) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				int rowSelected = busqProd.getTblProducto().getSelectionModel().getSelectedIndex();
				rowProd= busqProd.getLstProd().get(rowSelected);
				String cost = String.valueOf(rowProd.getCosto());
				
				inputCostoProd.setText((rowProd.getCosto()==null )?"0":String.valueOf(rowProd.getCosto()));
				inputProd.setText(rowProd.getProducto());
				inputPrecioUni.setText((rowProd.getCostoUnitario()==null )?"0":String.valueOf(rowProd.getCostoUnitario()));
				inputBarcode.setText(rowProd.getBar_code());
				
				busqProd.getTblProducto().getItems().removeAll(busqProd.getTblProducto().getItems());
				
				if(stageBusqProd!=null) stageBusqProd.close();
			}
		};
	}

	private EventHandler<MouseEvent> closeWindow() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(stageBusqProd!=null) stageBusqProd.close();
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
						Scene scene = new Scene(sceneEdit,3013,165);
						scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/BusqPedidoReporte.css").toExternalForm());
						stageBusqProd = new Stage();
						stageBusqProd.setScene(scene);
						stageBusqProd.setTitle("Busqueda de Pedido ");
						stageBusqProd.setMinHeight(470.0);
						stageBusqProd.setMinWidth(850.0);
						stageBusqProd.setWidth(850.0);
					//	stageBusqProd.setMaxHeight(470.0);
					//	stageBusqProd.setMaxWidth(770.0);
						stageBusqProd.initModality(Modality.APPLICATION_MODAL); 
						
						BusqPedidoRepController ctrller = fxmlLoader.getController(); //Obtiene controller de la nueva ventana

						ctrller.getBtnExportXls().setVisible(false);
						ctrller.getBtnModify().setVisible(true);
						ctrller.getContentProdPed().setVisible(false);
						Node child = ctrller.getVentaBody();
						AnchorPane.setBottomAnchor(ctrller.getContentPedido(),0.0);
						//ctrller.getContentPedido().setBottomAnchor(child , new Double(30));
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
		tbColProd.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.0));//0.2 --Para ocultar se pone en ceros
		tbColDesc.prefWidthProperty().bind(tbProductos.widthProperty().multiply(0.6)); //0.4 antes de ocultar barcode
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
