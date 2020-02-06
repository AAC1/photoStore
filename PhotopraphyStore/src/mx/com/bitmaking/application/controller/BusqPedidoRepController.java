package mx.com.bitmaking.application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.dto.ProdPedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
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
	IStorePedidoService pedidoService;
	@Autowired
	IStoreProdPedidoService prodPedidoService;

	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		responsiveGUI();
		initMethod();
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
		@FXML
	private void buscaPedido(MouseEvent event) {
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
		
		int lstSize = lstPedidos.size();
		/*
		for( int i=0;i<lstSize;i++) {
			pedidos.append(lstPedidos.get(i).getId_pedido());
			if((i+1)<lstSize) {
				pedidos.append(",");
			}
		}
		pedidos.append(")");
	
		consultProdByPedido(pedidos.toString());
		*/
	}
	@FXML 
	private void getProdByPedido(){
		PedidosReporteDTO objPedido = tblPedido.getSelectionModel().getSelectedItem();
		if(objPedido==null ) {
			GeneralMethods.modalMsg("", "", "No fue posible identificar pedido seleccionado");
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
	
	private String generateQry() {
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
		qry.append(GeneralMethods.validIfNull(inputBusqCliente.getText(), " AND p.cliente like '%%s%' "));
		String stts = "";
		if (cbxBusqEstatus.getValue() != null) {
			if ("ACTIVO".equals(cbxBusqEstatus.getValue().toUpperCase())) {
				stts = "1";
			} else if ("INACTIVO".equals(cbxBusqEstatus.getValue().toUpperCase())) {
				stts = "0";
			}
		}
		qry.append(GeneralMethods.validIfNull(stts, " AND p.id_estatus=%s "));
		if (dateBusqIni.getValue() != null)
			qry.append(GeneralMethods.validIfNull(dt.format(dateBusqIni.getValue()), " AND p.fec_pedido >= '%s' "));
		if (dateBusqFin.getValue() != null)
			qry.append(GeneralMethods.validIfNull(dt.format(dateBusqFin.getValue()), " AND p.fec_pedido <= '%s' "));

		System.out.println("qry:" + qry);
		return qry.toString();
	}

	@FXML
	private void cleanBusqform(MouseEvent event) {
		initMethod();
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
		cbxBusqEstatus.setItems(FXCollections.observableArrayList(arrayStts));
		cbxBusqEstatus.setValue("");
		tblPedido.getItems().removeAll(tblPedido.getItems());
		tblProducts.getItems().removeAll(tblProducts.getItems());
		
		
	//	lblPrefixFolio.setText("");

		colFolio.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("folio"));
		colCliente.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("cliente"));
		colTelCliente.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("telefono"));
		colDesc.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, String>("descripcion"));
		colFecPedido.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, Date>("fec_pedido"));
		colFecEntreg.setCellValueFactory(new PropertyValueFactory<PedidosReporteDTO, Date>("fec_entrega"));
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

	/**
	 * Tamaños de columnas en la tabla se expandan cuando la ventana se haga
	 * grande
	 */
	private void responsiveGUI() {
		/* Panel de Home resize de acuerdo al tama�o del Pane padre */

		colFolio.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colCliente.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colTelCliente.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.1));
		colDesc.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.2));
		colFecPedido.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.1));
		colFecEntreg.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.1));
		colEstatus.prefWidthProperty().bind(tblPedido.widthProperty().multiply(0.1));
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
