package mx.com.bitmaking.application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.aspectj.bridge.AbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.iservice.IStoreCatEstatusService;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.iservice.IStoreProdPedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.EmailSender;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;
import mx.com.bitmaking.application.util.PrinterService;
import mx.com.bitmaking.application.util.SMS;

@Component
public class BusqPedidoRepController {

	//@FXML
	//private Label lblPrefixFolio;

	@FXML
	private JFXTextField inputBusqFolio;
	@FXML
	private JFXTextField inputBusqCliente;
	@FXML
	private JFXComboBox<String> cbxBusqEstatus;
	@FXML
	private JFXDatePicker dateBusqIni;
	@FXML
	private JFXDatePicker dateBusqFin;

	/* Botones */
	@FXML
	private JFXButton btnExportXls;
	@FXML
	private JFXButton btnSalir;
	@FXML
	private JFXButton btnSendSMS;
	
	@FXML
	private JFXButton btnClean;
	@FXML
	private JFXButton btnBuscar;
	@FXML
	private JFXButton btnModify;
	@FXML
	private JFXButton btnModifProd;
	@FXML
	private JFXButton btnImpTicket;
	
	@FXML AnchorPane contentProdPed;
	@FXML AnchorPane contentPedido;
	@FXML AnchorPane ventaBody;
	/* Elementos de tabla de pedidos */
	@FXML
	private TableView<PedidosReporteDTO> tblPedido;
	@FXML
	private TableColumn<PedidosReporteDTO, String> colFolio;
	@FXML
	private TableColumn<PedidosReporteDTO, String> colCliente;
	@FXML
	private TableColumn<PedidosReporteDTO, String> colTelCliente;
	@FXML
	private TableColumn<PedidosReporteDTO, String> colDesc;
	@FXML
	private TableColumn<PedidosReporteDTO, Date> colFecPedido;
	@FXML
	private TableColumn<PedidosReporteDTO, Date> colFecEntreg;
	@FXML
	private TableColumn<PedidosReporteDTO, String> colEstatus;
	@FXML
	private TableColumn<PedidosReporteDTO, BigDecimal> colMontoAnt;
	@FXML
	private TableColumn<PedidosReporteDTO, BigDecimal> colMontoTotal;
	@FXML
	private TableColumn<PedidosReporteDTO, BigDecimal> colMontoPendiente;
	
	//PARA TABLA DE PRODUCTOS DE PEDIDOS
	@FXML
	private TableView<Store_prod_pedido> tblProducts;
	@FXML
	private TableColumn<Store_prod_pedido, String> colBarcodeProd;
	@FXML
	private TableColumn<Store_prod_pedido, String> colDescProd;
	@FXML
	private TableColumn<Store_prod_pedido, Integer> colCantidadProd;
	@FXML
	private TableColumn<Store_prod_pedido, BigDecimal> colCostUniProd;
	@FXML
	private TableColumn<Store_prod_pedido, BigDecimal> colCostTotalProd;
	@FXML
	private TableColumn<Store_prod_pedido, String>colSttsProd;
	
	@Autowired
	@Qualifier("StoreCatEstatusService")
	IStoreCatEstatusService catEstatusService;
	@Autowired
	@Qualifier("StorePedidoService")
	private IStorePedidoService pedidoService;
	@Autowired
	@Qualifier("StoreProdPedidoService")
	private IStoreProdPedidoService prodPedidoService;

	@Autowired
	@Qualifier("remoteStoreCatEstatusService")
	IStoreCatEstatusService remoteCatEstatusService;
	
	@Autowired
	@Qualifier("remoteStorePedidoService")
	private IStorePedidoService remotePedidoService;
	@Autowired
	@Qualifier("remoteStoreProdPedidoService")
	private IStoreProdPedidoService remoteProdPedidoService;

	@Autowired
	private ApplicationContext context ;
	@Autowired
	private Environment env;
	
	Stage stage = null;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	PedidosReporteDTO objPedido = null;
	
	
	
	/**
	 * @return the btnSendSMS
	 */
	public JFXButton getBtnSendSMS() {
		return btnSendSMS;
	}

	/**
	 * @param btnSendSMS the btnSendSMS to set
	 */
	public void setBtnSendSMS(JFXButton btnSendSMS) {
		this.btnSendSMS = btnSendSMS;
	}

	public TableView<Store_prod_pedido> getTblProducts() {
		return tblProducts;
	}

	public void setTblProducts(TableView<Store_prod_pedido> tblProducts) {
		this.tblProducts = tblProducts;
	}

	public JFXButton getBtnImpTicket() {
		return btnImpTicket;
	}

	public void setBtnImpTicket(JFXButton btnImpTicket) {
		this.btnImpTicket = btnImpTicket;
	}

	/**
	 * @return the ventaBody
	 */
	public AnchorPane getVentaBody() {
		return ventaBody;
	}

	/**
	 * @return the contentPedido
	 */
	public AnchorPane getContentPedido() {
		return contentPedido;
	}

	public JFXButton getBtnSalir() {
		return btnSalir;
	}
	
	/**
	 * @return the btnExportXls
	 */
	public JFXButton getBtnExportXls() {
		return btnExportXls;
	}

	/**
	 * @return the btnModify
	 */
	public JFXButton getBtnModify() {
		return btnModify;
	}

	/**
	 * @return the contentProdPed
	 */
	public AnchorPane getContentProdPed() {
		return contentProdPed;
	}

	/**
	 * @return the btnModifProd
	 */
	public JFXButton getBtnModifProd() {
		return btnModifProd;
	}

	/**
	 * @param btnModifProd the btnModifProd to set
	 */
	public void setBtnModifProd(JFXButton btnModifProd) {
		this.btnModifProd = btnModifProd;
	}

	public void initialize() {
		responsiveGUI();
		initMethod();
		btnModify.addEventHandler(MouseEvent.MOUSE_CLICKED,modalEditPedido());
		
		PseudoClass redColor = PseudoClass.getPseudoClass("red-text");
   		PseudoClass greenColor = PseudoClass.getPseudoClass("green-text");
   		PseudoClass blackColor = PseudoClass.getPseudoClass("black-text");
   		PseudoClass blueColor = PseudoClass.getPseudoClass("blue-text");
   		 
		tblPedido.setRowFactory(new Callback<TableView<PedidosReporteDTO>, TableRow<PedidosReporteDTO>>() {
	        @Override
	        public TableRow<PedidosReporteDTO> call(TableView<PedidosReporteDTO> tableView) {
	        	
	            final TableRow<PedidosReporteDTO> rowTbl = new TableRow<PedidosReporteDTO>() {
	                @Override
	                protected void updateItem(PedidosReporteDTO row, boolean empty) {
	                    super.updateItem(row, empty);
	                    if (!empty){
	                    	/*if(isSelected()){
	                    		pseudoClassStateChanged(blueColor,true);
	                    	//	setStyle("-fx-background-color:#0467A9; ");
	                    	//	setTextFill(Color.WHITE);
	                    	
	                    	}*/
	                    	if("PENDIENTE".equals(row.getEstatus().toUpperCase().trim())){
		                    	if(hasPendientProds("("+String.valueOf(row.getId_pedido())+")")){
		                    		
		                    	//	getStyleClass().add("red-text");
		                    	//	setStyle("-fx-background-color:#E1AAAA; -fx-text-fill:white; -fx-fill:white;");
		                    		pseudoClassStateChanged(redColor,true);
		                    		pseudoClassStateChanged(greenColor,false);
		                    		pseudoClassStateChanged(blackColor,false);
		                    	}else{
		                    //		getStyleClass().add("green-text");
		        					pseudoClassStateChanged(greenColor,true);
		        					pseudoClassStateChanged(redColor,false);
		        					pseudoClassStateChanged(blackColor,false);
		        				//	setStyle("-fx-background-color:#2AA804; ");
		        				}
	                    	}else{
	                    	//	setStyle("-fx-background-color:transparent");
	                    	//	getStyleClass().add("black-text");
	                    		pseudoClassStateChanged(blackColor,true);
	                    		pseudoClassStateChanged(redColor,false);
	                    		pseudoClassStateChanged(greenColor,false);
	                    	}
	                    }
	                    	
	                }
	            };
	            return rowTbl;
	        }
	    });
	}
	
	@FXML
	private void btnModifSttsProd(){
		try {
			Store_prod_pedido obj = tblProducts.getSelectionModel().getSelectedItem();
			if(obj==null){
				GeneralMethods.modalMsg("", "", "Debes seleccionar un producto;");
				return;
			}
			if("TERMINADO".equals(obj.getEstatus().trim().toUpperCase())){
				GeneralMethods.modalMsg("", "", "Producto se encuentra terminado, no puedes cambiar el estatus.");
				return;
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/EditSttsProd.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			Parent sceneEdit= fxmlLoader.load();
			Scene scene = new Scene(sceneEdit,3013,165);
			scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Edici\u00F3n de Producto ");
			stage.setMinHeight(600.0);
			stage.setMinWidth(450.0);
			stage.setMaxHeight(600.0);
			stage.setMaxWidth(450.0);
			stage.initModality(Modality.APPLICATION_MODAL); 
			
			EditSttsProdController ctrller = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			
			ctrller.getBtnCancel().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow(stage));
			ctrller.getBtnAccept().addEventHandler(MouseEvent.MOUSE_CLICKED, acceptEditSttsProd(ctrller,stage));
			
			
			ctrller.getInputCantidad().setText(String.valueOf(obj.getCantidad()));
			ctrller.getInputDesc().setText(obj.getDescripcion());
			ctrller.getInputBarcode().setText(obj.getBar_code());
			ctrller.getCbxEstatus().setValue(obj.getEstatus());
			ctrller.getInputMontoTot().setText(String.valueOf(obj.getCosto_total()));
			stage.show();
			
	    } catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private EventHandler<MouseEvent>acceptEditSttsProd(EditSttsProdController ctrller,Stage stage) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//PedidosReporteDTO objPedido = tblPedido.getSelectionModel().getSelectedItem();
				Store_prod_pedido obj = tblProducts.getSelectionModel().getSelectedItem();
				if(obj==null ) {
					GeneralMethods.modalMsg("", "", "No fue posible identificar el producto seleccionado");
					return;
				}
				try {
					System.out.println("Idx Estatus:"+ctrller.getCbxEstatus().getSelectionModel().getSelectedIndex());
					
					obj.setEstatus(ctrller.getCbxEstatus().getValue());
					
					prodPedidoService.editProd(obj);
					if(Flags.remote_valid)remoteProdPedidoService.editProd(obj);
					searchPedido();
					
					if(stage!=null) stage.close();
					
					if("DEVOLUCION".equals(ctrller.getCbxEstatus().getSelectionModel().getSelectedItem().toUpperCase())){
						openVta(objPedido);
					}
					
				} catch (Exception e) {
					GeneralMethods.modalMsg("", "", "Ocurrió un error: "+e.getMessage());
					e.printStackTrace();
				}
			}
		};
		
	};
	
	private EventHandler<MouseEvent> modalEditPedido() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/EditPedido.fxml"));
						fxmlLoader.setControllerFactory(context::getBean);
						Parent sceneEdit= fxmlLoader.load();
						Scene scene = new Scene(sceneEdit,3013,165);
						scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Edición de Pedido ");
						stage.setMinHeight(600.0);
						stage.setMinWidth(450.0);
						stage.setMaxHeight(600.0);
						stage.setMaxWidth(450.0);
						stage.initModality(Modality.APPLICATION_MODAL); 
						
						EditPedidoController ctrller = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
						
						ctrller.getBtnCancel().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow(stage));
						ctrller.getBtnAccept().addEventHandler(MouseEvent.MOUSE_CLICKED, acceptEditPedido(ctrller,stage));
						PedidosReporteDTO obj = tblPedido.getSelectionModel().getSelectedItem();
						if(obj==null){
							GeneralMethods.modalMsg("", "", "Debes seleccionar un pedido");
							return;
						}
						if("ENTREGADO".equals(obj.getEstatus().toUpperCase())){
							GeneralMethods.modalMsg("", "", "Pedido entregado, solo puedes modificar pedidos pendientes, cancelados o con devoluci\u00F3n");
							return;
						}
						ctrller.getInputClte().setText(obj.getCliente());
						ctrller.getInputFolio().setText(obj.getFolio());
						ctrller.getInputDesc().setText(obj.getDescripcion());
						ctrller.getCbxEstatus().setValue(obj.getEstatus());
						ctrller.getInputMontoTot().setText(String.valueOf(obj.getMonto_total()));
						ctrller.getInputMontoAnt().setText(String.valueOf(obj.getMonto_ant()));
						stage.show();
						
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}

			private EventHandler<MouseEvent>acceptEditPedido(EditPedidoController ctrller,Stage stage) {
				return new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						//PedidosReporteDTO objPedido = tblPedido.getSelectionModel().getSelectedItem();
						if(objPedido==null ) {
							GeneralMethods.modalMsg("", "", "No fue posible identificar pedido seleccionado");
							return;
						}
						try {
							System.out.println("Idx Estatus:"+ctrller.getCbxEstatus().getSelectionModel().getSelectedIndex());
							objPedido.setId_estatus(ctrller.getCbxEstatus().getSelectionModel().getSelectedIndex()+1);
							if(objPedido.getId_estatus()==1){
								objPedido.setFec_entregado(sdf.parse(sdf.format(new Date())));
							}
							objPedido.setDescripcion(ctrller.getInputDesc().getText());
							System.out.println("ticket: "+objPedido.getTicket());
							pedidoService.editPedido(objPedido);
							if(Flags.remote_valid)remotePedidoService.editPedido(objPedido);
							searchPedido();
							
							if(stage!=null) stage.close();
							
							if("DEVOLUCION".equals(ctrller.getCbxEstatus().getSelectionModel().getSelectedItem().toUpperCase())){
								openVta(objPedido);
							}
							
						} catch (ParseException e) {
							GeneralMethods.modalMsg("", "", "Ocurrió un error en fechas");
							e.printStackTrace();
						}
					}
				};
				
				
				
				
			}};
	}
	
	private void openVta(PedidosReporteDTO objPedido) {
		
				try {
					
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/Venta.fxml"));
						fxmlLoader.setControllerFactory(context::getBean);
						
			        	Parent sceneEdit= fxmlLoader.load();
			        	
			        	Scene scene = new Scene(sceneEdit,750,600);
						scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Edición de Pedido ");
						stage.setMinHeight(600.0);
						stage.setMinWidth(750);
						stage.initModality(Modality.APPLICATION_MODAL); 
						sceneEdit.setStyle("-fx-background-color: rgba(255,255,2550.9); ");
						
						VentaController vtaCtrl = fxmlLoader.getController();
			        	vtaCtrl.setUpdateVta(false);
			        	vtaCtrl.getBtnSalir().addEventHandler(MouseEvent.MOUSE_CLICKED,closeWindow(stage));
			        	
			        	Store_pedido pedido = new Store_pedido();
			        	pedido.setCliente(objPedido.getCliente());
			        	pedido.setDescripcion(objPedido.getDescripcion());
			        	pedido.setFec_entregado(objPedido.getFec_entregado());
			        	pedido.setFec_pedido(objPedido.getFec_pedido());
			        	pedido.setFolio(objPedido.getFolio());
			        	pedido.setId_estatus(objPedido.getId_estatus());
			        	pedido.setId_pedido(objPedido.getId_pedido());
			        	pedido.setMonto_ant(objPedido.getMonto_ant());
			        	pedido.setMonto_total(objPedido.getMonto_total());
			        	pedido.setTelefono(objPedido.getTelefono());
			        	pedido.setTicket(objPedido.getTicket());
			        	vtaCtrl.setUpdatePedido(pedido);
			        	vtaCtrl.setUpdateVta(true);
			        	vtaCtrl.setValuesToUpdate(pedido,stage);
						
						stage.show();
						
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
	}

	@FXML
	private void exportXLS()  {
		File file=null;
		FileInputStream fileInputStream = null;
		//ClassLoader classLoader = getClass().getClassLoader();
		//URL loader = BusqPedidoRepController.class.getClassLoader().getResource("reportePedidos.jasper");
		
		try {
		//	if(loader==null){
		//		GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar directorio de la plantilla para el reporte");
		//		return;
		//	}
			
			file = new File(env.getProperty("exportFile.pathPedidoRepJasper"));//loader.getFile());
			System.out.println("ABS_PATH: "+file.getAbsolutePath());
			System.out.println("PARENT: "+file.getParent());
			System.out.println("JUST_PATH: "+file.getPath());
			//String pathPlantilla = file.getAbsolutePath();
			
			//File fileToDownload = new File(pathPlantilla);
			SimpleDateFormat formatoD = new SimpleDateFormat("ddMMyyyy_hhmmss");
		
			if (file.exists() && file.isFile()) {
				fileInputStream = new FileInputStream(file);
			} else {
				GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar plantilla de reporte");
				return;
			}
			String pathReport=env.getProperty("exportFile.path")+"/reporte_"+formatoD.format(new Date())+".xls";
			String qry = generateQry();
			String titulo=Constantes.COMPANY_NAME;
			// Preparamos los valores que se van a escribir en el reporte
			Map<String, Object> parametrosReporte = new HashMap<>();
			parametrosReporte.put("qry", qry);
			parametrosReporte.put("titulo", titulo);
			parametrosReporte.put("SUBREPORT_DIR", file.getParent()+"/");
			
			
			boolean export = (Flags.remote_valid)?
					remotePedidoService.generaXLS(fileInputStream,parametrosReporte,pathReport,file.getParent()+"/"):
					pedidoService.generaXLS(fileInputStream,parametrosReporte,pathReport,file.getParent()+"/");
			
			if(export) {
				File fataExported = new File(pathReport);
			//	GeneralMethods.modalMsg("", "Exportación Terminada.", " Vaya a la ruta: "+pathReport);
				GeneralMethods.saveFile(stage, fataExported.toPath(),"XLS files (*.xls)", "*.xls");
			}else
				GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error al generar reporte");
		} /*catch(MalformedURLException e){
			GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar la plantilla del reporte");
			e.printStackTrace();
		}*/
		catch (Exception e) {
			GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error al generar reporte");
			e.printStackTrace();
		}finally{
			if(fileInputStream!=null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	
	
	public void searchPedido(){
		tblPedido.getItems().removeAll(tblPedido.getItems());
		tblProducts.getItems().removeAll(tblProducts.getItems());
		
		String qry = generateQry();
		List<PedidosReporteDTO> lstPedidos = (Flags.remote_valid)?remotePedidoService.consultPedido(qry):
																	pedidoService.consultPedido(qry);
		
		if (lstPedidos == null || lstPedidos.size() == 0) {
			GeneralMethods.modalMsg("", "", "No se encontraron pedidos");
			return;
		}
		
		
		tblPedido.setItems(FXCollections.observableList(lstPedidos));
	
	}
	@FXML
	private void buscaPedido(MouseEvent event) {
		searchPedido();
	}
	@FXML 
	private void getProdByPedido(){
		objPedido = tblPedido.getSelectionModel().getSelectedItem();
		if(objPedido==null ) {
			//GeneralMethods.modalMsg("", "", "No fue posible identificar pedido seleccionado");
			return;
		}
		
		String pedido="("+objPedido.getId_pedido()+") " ;
		consultProdByPedido(pedido);
		
	}
	private void consultProdByPedido(String inPedido) {
		if(inPedido==null || "()".equals(inPedido) ) {
			GeneralMethods.modalMsg("", "", "No fue posible identificar el pedido para mostrar su detalle");
			return;
		}
		
		System.out.println("Pedidos: "+inPedido);
		List<Store_prod_pedido> lstProductos=(Flags.remote_valid)?
							remoteProdPedidoService.getListProdPedidos(inPedido):
									prodPedidoService.getListProdPedidos(inPedido);
		tblProducts.getItems().removeAll(tblProducts.getItems());
		tblProducts.setItems(FXCollections.observableList(lstProductos));
		
		for(Store_prod_pedido el: tblProducts.getItems()){
			if("PENDIENTE".equals(el.getEstatus().trim())){
				
			}
		}
	}
	private boolean hasPendientProds(String inPedido) {
		boolean resp=false;
		
		if(inPedido==null || "()".equals(inPedido) ) {
			return false;
		}
		
		System.out.println("Pedidos: "+inPedido);
		List<Store_prod_pedido> lstProductos=(Flags.remote_valid)?
							remoteProdPedidoService.getListProdPedidos(inPedido):
									prodPedidoService.getListProdPedidos(inPedido);
		
		for(Store_prod_pedido el: lstProductos){
			if("PENDIENTE".equals(el.getEstatus().trim())){
				resp=true;
				break;
			}
		}
		return resp;
	}
	
	/**
	 * Consulta de pedido 
	 * @return
	 */
	private String generateQry() {
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dtEnd = DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59");
		StringBuilder qry = new StringBuilder();
		qry.delete(0, qry.length());
		qry.append("SELECT p.id_pedido, p.folio, p.cliente, p.telefono, p.descripcion, p.fec_pedido,");
		qry.append(" p.fec_entregado, p.monto_ant, p.monto_total, (IFNULL(p.monto_total,0) - IFNULL(p.monto_ant,0) ) monto_pendiente, ");
		qry.append(" (select s.estatus from Store_cat_estatus s where s.id_estatus=p.id_estatus) as estatus, p.ticket ");
		qry.append(" FROM Store_pedido p ");

		//qry.append("WHERE p.folio like'%" + lblPrefixFolio.getText());
		//qry.append(GeneralMethods.validIfNull(inputBusqFolio.getText(), "%s"));
		qry.append("WHERE p.folio like '%");
		qry.append(GeneralMethods.validIfNull(inputBusqFolio.getText(), "%s"));
		qry.append("%' ");// Cierra Folio
		qry.append(GeneralMethods.validIfNull(inputBusqCliente.getText(), " AND p.cliente like \'%%%s%%\' "));
		String stts = "";
		if (cbxBusqEstatus.getValue() != null) {
			if ("TERMINADO".equals(cbxBusqEstatus.getValue().toUpperCase())) {
				stts = "1";
			} else if ("PENDIENTE".equals(cbxBusqEstatus.getValue().toUpperCase())) {
				stts = "2";
			}else if ("CANCELADO".equals(cbxBusqEstatus.getValue().toUpperCase())) {
				stts = "3";
			}
		}
		qry.append(GeneralMethods.validIfNull(stts, " AND p.id_estatus=%s "));
		if (dateBusqIni.getValue() != null)
			qry.append(GeneralMethods.validIfNull(dt.format(dateBusqIni.getValue()), " AND p.fec_pedido >= \'%s\' "));
		if (dateBusqFin.getValue() != null)
			qry.append(GeneralMethods.validIfNull(dtEnd.format(dateBusqFin.getValue()), " AND p.fec_pedido <= \'%s\' "));

		System.out.println("qry:" + qry);
		return qry.toString();
	}

	@FXML
	private void cleanBusqform(MouseEvent event) {
		initMethod();
	}
	private void getLstEstatus() {
		
		cbxBusqEstatus.getItems().removeAll(cbxBusqEstatus.getItems());
		List<Store_cat_estatus> lstEstatus = (Flags.remote_valid)?
												remoteCatEstatusService.getListEstatus():
												catEstatusService.getListEstatus();
		String[] arrayStts = new String[lstEstatus.size()];
		for(int i=0; i<lstEstatus.size();i++) {
			arrayStts[i] = lstEstatus.get(i).getEstatus();
		}
		cbxBusqEstatus.setItems(FXCollections.observableArrayList(arrayStts));
	}
	/**
	 * Formatea y asignacion de valores a los componentes
	 */
	private void initMethod() {
		String[] arrayStts = { "", "Activo", "Inactivo" };
		dateBusqIni.setValue(null);// (LocalDate.now());
		dateBusqFin.setValue(null);// (LocalDate.now());
		inputBusqFolio.setText("");
		inputBusqCliente.setText("");
		getLstEstatus();
		//cbxBusqEstatus.setItems(FXCollections.observableArrayList(arrayStts));
		cbxBusqEstatus.setValue("");
		tblPedido.getItems().removeAll(tblPedido.getItems());
		tblProducts.getItems().removeAll(tblProducts.getItems());
		objPedido =null;
		
	//	lblPrefixFolio.setText("");

		colFolio.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("folio"));
		colCliente.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("cliente"));
		colTelCliente.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("telefono"));
		colDesc.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("descripcion"));
		colFecPedido.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, Date>("fec_pedido"));
		colFecEntreg.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, Date>("fec_entregado"));
		colEstatus.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("estatus"));
		colMontoAnt.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, BigDecimal>("monto_ant"));
		colMontoTotal.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, BigDecimal>("monto_total"));
		colMontoPendiente.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, BigDecimal>("monto_pendiente"));
		
		//COLUMNAS DE PRODUCTOS DE PEDIDO
		colBarcodeProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, String>("bar_code"));
		colCantidadProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, Integer>("cantidad"));
		colCostUniProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, BigDecimal>("costo_unitario"));
		colCostTotalProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, BigDecimal>("costo_total"));
		colDescProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, String>("descripcion"));
		colSttsProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, String>("estatus"));
		
		
	}
	private EventHandler<MouseEvent> closeWindow(Stage stage) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(stage!=null) stage.close();
				searchPedido();
			}
		};


	}
	/**
	 * Tamaños de columnas en la tabla se expandan cuando la ventana se haga
	 * grande
	 */
	private void responsiveGUI() {
		/* Panel de Home resize de acuerdo al tama�o del Pane padre */

		colFolio.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colCliente.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.3));
		colTelCliente.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colDesc.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.3));
		colFecPedido.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colFecEntreg.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colEstatus.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colMontoAnt.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colMontoTotal.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colMontoPendiente.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		
		//TABLA PARA PRODUCTOS DE PEDIDOS:
		colBarcodeProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.2));
		colDescProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.2));
		colCantidadProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.1));
		colCostUniProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.1));
		colCostTotalProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.2));
		colSttsProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.2));
		
	}
	
	@FXML 
	private void selectByArrow(KeyEvent e) {
		System.out.println("entra:"+e.getCode().toString());
		if("DOWN".equals(e.getCode().toString()) || "UP".equals(e.getCode().toString())){
			getProdByPedido();
		}
	}
	
	@FXML
	private void printTicket() {
		PedidosReporteDTO obj = tblPedido.getSelectionModel().getSelectedItem();
		if(obj==null){
			GeneralMethods.modalMsg("", "", "Debes seleccionar un pedido");
			return;
		}
		byte[] byteTicket = obj.getTicket();
		if(byteTicket ==null) {
			GeneralMethods.modalMsg("", "", "No hay ticket guardado");
			return;
		}
		String ticket =  new String(byteTicket);
		System.out.println("ticket:");
		System.out.println(ticket);
		
		try {
			PrinterService.printTicket(Constantes.PRINTER_NAME,ticket);
		} catch (Exception e) {
			GeneralMethods.modalMsg("ERROR", "", "Ocurrio un error: "+e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	@FXML
	private void sendMessage(){
		try{
		//	SMS.sendMessage("4427376162", "4427376162", "Pruena SMS");
			try {
				String passwd = GeneralMethods.desencriptar(env.getProperty("mail.password"), Constantes.SALT);
				String emailUser = GeneralMethods.desencriptar(env.getProperty("mail.user"), Constantes.SALT);
				
				EmailSender mailObj = new EmailSender(env.getProperty("mail.host"),env.getProperty("mail.port"),
						emailUser,passwd);
				
				StringBuilder msgHtml = new StringBuilder();
				
				
				
				
				String filename = "";
				SimpleDateFormat formatoD = new SimpleDateFormat("ddMMyyyy_hhmmss");
				String pathReport=env.getProperty("exportFile.path")+"/corteCaja_"+formatoD.format(new Date())+".xls";
				System.out.println(msgHtml);
				try {
					PedidosReporteDTO obj = tblPedido.getSelectionModel().getSelectedItem();
					if(obj==null){
						GeneralMethods.modalMsg("", "", "Debes seleccionar un pedido");
						return;
					}
					msgHtml.append("<html><head><style>.card {");
					msgHtml.append("box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);transition: 0.3s;");
					msgHtml.append("min-width: 450px;width:50%;text-align:center;margin-left: 20%;}");
					msgHtml.append(".card:hover { box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);}");
					msgHtml.append(".container {text-align:center;padding: 2px 16px;}");
					msgHtml.append("</style></head><body >");
					msgHtml.append("<div style='width:100%; text-align:center'>");
					msgHtml.append("<div class='card' ><div><h3>Estimado(a) "+obj.getCliente()+"</h3></div>");
					msgHtml.append("<div class='container'>");
					msgHtml.append("<h4>Su pedido "+obj.getFolio()+" se encuentra listo para ser entregado. Favor de pasar a recogerlo. </h4>");
					msgHtml.append("<p><b>Horarios de atenci&oacute;n:</b> <br/>");
					msgHtml.append("lunes - viernes	9:00–20:30<br/>s&aacute;bado	10:00–23:00<br/>");
					msgHtml.append("domingo	10:00–17:00<br/></p></div></div>");
					msgHtml.append("</body></html> ");
					
					msgHtml.append("</td></tr>");
					msgHtml.append("</tbody></table>");
					if("PENDIENTE".equals(obj.getEstatus().toUpperCase())){
						if(!hasPendientProds("("+String.valueOf(obj.getId_pedido())+")")) {
							mailObj.sendMessageHTML(env.getProperty("mail.userTo"), msgHtml.toString(), "Estatus de Pedido",null, null);
						}else {
							GeneralMethods.modalMsg("", "", "No es posible enviar notificaci\u00F3n, el pedido tiene productos pendientes ");
						}
						
						
					}else {
						GeneralMethods.modalMsg("", "", "Pedido debe estar como estatus PENDIENTE y tener productos terminados ");
					}
						
				} catch (AddressException e) {
					GeneralMethods.modalMsg("", "", e.getMessage());
					e.printStackTrace();
				} catch (MessagingException e) {
					GeneralMethods.modalMsg("", "", e.getMessage());
					e.printStackTrace();
				}
				
	        } catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
