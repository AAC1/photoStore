package mx.com.bitmaking.application.controller;

import java.io.File;
import java.io.FileInputStream;
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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class BusqPedidoRepController {

	@FXML
	private Label lblPrefixFolio;

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

	/* Elementos de tabla */
	@FXML
	private TableView<Store_pedido> tblPedido;
	@FXML
	private TableColumn colFolio;
	@FXML
	private TableColumn colCliente;
	@FXML
	private TableColumn colTelCliente;
	@FXML
	private TableColumn colDesc;
	@FXML
	private TableColumn colFecPedido;
	@FXML
	private TableColumn colFecEntreg;
	@FXML
	private TableColumn colEstatus;
	@FXML
	private TableColumn colMontoAnt;
	@FXML
	private TableColumn colMontoTotal;
	
	@Autowired
	IStorePedidoService pedidoService;

	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	@FXML
	private void exportXLS() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("reportePedidos.jasper").getFile());
		System.out.println(file.getAbsolutePath());
		String pathPlantilla = file.getAbsolutePath();//"/src/resources/reportes/reportePedidos.jasper";
		File fileToDownload = new File(pathPlantilla);
		FileInputStream fileInputStream = null;
		SimpleDateFormat formatoD = new SimpleDateFormat("ddMMyyyy_hhmmss");
		try {
			if (fileToDownload.exists() && fileToDownload.isFile()) {
				fileInputStream = new FileInputStream(fileToDownload);
			} else {
				GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar plantilla de reporte");
				return;
			}
			String pathReport=Constantes.PATH_XLS+"reporte_"+formatoD.format(new Date())+".xls";
			String qry = generateQry();
			String titulo="MACROFOTO S.A de C.V.";
			boolean export = pedidoService.generaXLS(fileInputStream,qry,titulo,pathReport);
			if(export)
				GeneralMethods.modalMsg("", "Exportación Terminada.", " Vaya a la ruta: "+pathReport
						);
			else
				GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error al generar reporte");
		} catch (Exception e) {
			GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error al generar reporte");
			e.printStackTrace();
		}

	}

	public void initialize() {
		responsiveGUI();
		initMethod();
	}

	@FXML
	private void buscaPedido(MouseEvent event) {
		System.out.println("Clicle btn Buscar");
		String qry = generateQry();
		List<Store_pedido> lstPedidos = pedidoService.consultPedido(qry);

		if (lstPedidos == null || lstPedidos.size() == 0) {
			GeneralMethods.modalMsg("", "", "No se encontraron pedidos");
			return;
		}
		tblPedido.setItems(FXCollections.observableList(lstPedidos));
	}

	private String generateQry() {
		StringBuilder qry = new StringBuilder();
		qry.delete(0, qry.length());
		qry.append("SELECT p.id_pedido, p.folio, p.cliente, p.telefono, p.descripcion, p.fec_pedido,");
		qry.append(" p.fec_entregado, p.monto_ant, p.monto_total,");
		qry.append(" (select estatus from Store_cat_estatus where id_estatus=p.id_estatus) as estatus");
		qry.append(" FROM Store_pedido p ");

		qry.append("WHERE p.folio>='" + lblPrefixFolio.getText());
		// SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		qry.append(GeneralMethods.validIfNull(inputBusqFolio.getText(), "-%s"));
		qry.append("' ");// Cierra Folio
		qry.append(GeneralMethods.validIfNull(inputBusqCliente.getText(), " AND p.cliente>='%s' "));
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
		tblPedido.getItems().remove(tblPedido.getItems());
		lblPrefixFolio.setText("MCIRP");
	}

	/**
	 * Tamaños de columnas en la tabla se expandan cuando la ventana se haga grande
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

	}
}
