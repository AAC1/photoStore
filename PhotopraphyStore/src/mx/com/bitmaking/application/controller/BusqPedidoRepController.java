package mx.com.bitmaking.application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.dto.ProdPedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.service.IStoreCatEstatusService;
import mx.com.bitmaking.application.service.IStorePedidoService;
import mx.com.bitmaking.application.service.IStoreProdPedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.GeneralMethods;

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
	private JFXButton btnClean;
	@FXML
	private JFXButton btnBuscar;
	@FXML
	private JFXButton btnModify;
	
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
	
	@Autowired
	IStoreCatEstatusService catEstatusService;
	
	@Autowired
	private IStorePedidoService pedidoService;
	@Autowired
	private IStoreProdPedidoService prodPedidoService;

	@Autowired
	private ApplicationContext context ;
	
	Stage stage = null;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	PedidosReporteDTO objPedido = null;
	
	
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

	public void initialize() {
		responsiveGUI();
		initMethod();
		btnModify.addEventHandler(MouseEvent.MOUSE_CLICKED,modalEditPedido());
	}
	
	
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
						stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Edición de Pedido ");
						stage.setMinHeight(600.0);
						stage.setMinWidth(450.0);
						stage.setMaxHeight(600.0);
						stage.setMaxWidth(450.0);
						stage.initModality(Modality.APPLICATION_MODAL); 
						
						EditPedidoController ctrller = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
						
						ctrller.getBtnCancel().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow());
						ctrller.getBtnAccept().addEventHandler(MouseEvent.MOUSE_CLICKED, acceptEditPedido(ctrller));
						PedidosReporteDTO obj = tblPedido.getSelectionModel().getSelectedItem();
						if(obj==null){
							GeneralMethods.modalMsg("", "", "Debes seleccionar un pedido");
							return;
						}
						if("TERMINADO".equals(obj.getEstatus().toUpperCase())){
							GeneralMethods.modalMsg("", "", "Pedido terminado, solo puedes modificar pedidos pendientes o cancelados");
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

			private EventHandler<MouseEvent>acceptEditPedido(EditPedidoController ctrller) {
				return new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						//PedidosReporteDTO objPedido = tblPedido.getSelectionModel().getSelectedItem();
						if(objPedido==null ) {
							GeneralMethods.modalMsg("", "", "No fue posible identificar pedido seleccionado");
							return;
						}
						try {
							objPedido.setId_estatus(ctrller.getCbxEstatus().getSelectionModel().getSelectedIndex()+1);
							if(objPedido.getId_estatus()==1){
								objPedido.setFec_entregado(sdf.parse(sdf.format(new Date())));
							}
							objPedido.setDescripcion(ctrller.getInputDesc().getText());
							pedidoService.editPedido(objPedido);
							searchPedido();
							if(stage!=null) stage.close();
						} catch (ParseException e) {
							GeneralMethods.modalMsg("", "", "Ocurrió un error en fechas");
							e.printStackTrace();
						}
					}
				};
				
				
				
				
			}};
	}

	@FXML
	private void exportXLS()  {
		File file=null;
		FileInputStream fileInputStream = null;
		ClassLoader classLoader = getClass().getClassLoader();
		URL loader = BusqPedidoRepController.class.getClassLoader().getResource("reportePedidos.jasper");
		//classLoader.getResource("reportePedidos.jasper");
		try {
			if(loader==null){
				GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar directorio de la plantilla para el reporte");
				return;
			}
			
			file = new File(loader.getFile());
			System.out.println("ABS_PATH: "+file.getAbsolutePath());
			System.out.println("PARENT: "+file.getParent());
			System.out.println("JUST_PATH: "+file.getPath());
			String pathPlantilla = file.getAbsolutePath();
			
			File fileToDownload = new File(pathPlantilla);
			SimpleDateFormat formatoD = new SimpleDateFormat("ddMMyyyy_hhmmss");
		
			if (fileToDownload.exists() && fileToDownload.isFile()) {
				fileInputStream = new FileInputStream(fileToDownload);
			} else {
				GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar plantilla de reporte");
				return;
			}
			String pathReport=Constantes.PATH_XLS+"reporte_"+formatoD.format(new Date())+".xls";
			String qry = generateQry();
			String titulo="MACROFOTO S.A de C.V.";
			boolean export = pedidoService.generaXLS(fileInputStream,qry,titulo,pathReport,file.getParent()+"/");
			if(export)
				GeneralMethods.modalMsg("", "Exportación Terminada.", " Vaya a la ruta: "+pathReport
						);
			else
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
		List<PedidosReporteDTO> lstPedidos = pedidoService.consultPedido(qry);
		List<Store_prod_pedido> lstProductos = null;
		
		if (lstPedidos == null || lstPedidos.size() == 0) {
			GeneralMethods.modalMsg("", "", "No se encontraron pedidos");
			return;
		}
		
		tblPedido.setItems(FXCollections.observableList(lstPedidos));
		
		StringBuilder pedidos = new StringBuilder("(");
		
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
		List<Store_prod_pedido> lstProductos=prodPedidoService.getListProdPedidos(inPedido);
		tblProducts.getItems().removeAll(tblProducts.getItems());
		tblProducts.setItems(FXCollections.observableList(lstProductos));
		
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
		qry.append(" p.fec_entregado, p.monto_ant, p.monto_total,");
		qry.append(" (select s.estatus from Store_cat_estatus s where s.id_estatus=p.id_estatus) as estatus");
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
		List<Store_cat_estatus> lstEstatus = catEstatusService.getListEstatus();
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
		
		//COLUMNAS DE PRODUCTOS DE PEDIDO
		colBarcodeProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, String>("bar_code"));
		colCantidadProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, Integer>("cantidad"));
		colCostUniProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, BigDecimal>("costo_unitario"));
		colCostTotalProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, BigDecimal>("costo_total"));
		colDescProd.setCellValueFactory(new PropertyValueFactory<Store_prod_pedido, String>("descripcion"));
		
		
	}
	private EventHandler<MouseEvent> closeWindow() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(stage!=null) stage.close();
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
		
		
		//TABLA PARA PRODUCTOS DE PEDIDOS:
		colBarcodeProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.2));
		colDescProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.3));
		colCantidadProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.1));
		colCostUniProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.2));
		colCostTotalProd.prefWidthProperty().bind(tblProducts.widthProperty().multiply(0.2));
		
	}
}
