package mx.com.bitmaking.application.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import mx.com.bitmaking.application.util.GeneralMethods;

@Component
public class BusqPedidoRepController {

	@FXML private Label lblPrefixFolio;
	
	@FXML private JFXTextField inputBusqFolio;
	@FXML private JFXTextField inputBusqCliente;
	@FXML private JFXComboBox<String> cbxBusqEstatus;
	@FXML private JFXDatePicker dateBusqIni;
	@FXML private JFXDatePicker dateBusqFin;
	
	/*Botones*/
	@FXML private JFXButton btnExportXls;
	@FXML private JFXButton btnSalir;
	@FXML private JFXButton btnClean;
	@FXML private JFXButton btnBuscar;
	
	/*Elementos de tabla*/
	@FXML private TableView<Store_pedido> tblPedido;
	@FXML private TableColumn colFolio;
	@FXML private TableColumn colCliente;
	@FXML private TableColumn colTelCliente;
	@FXML private TableColumn colDesc;
	@FXML private TableColumn colFecPedido;
	@FXML private TableColumn colFecEntreg;
	@FXML private TableColumn colEstatus;
	@FXML private TableColumn colMontoAnt;
	@FXML private TableColumn colMontoTotal;
	
	@Autowired
	IStorePedidoService pedidoService;
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}



	public void initialize() {
		responsiveGUI();
		initMethod();
	}
	
	@FXML
	private void buscaPedido(MouseEvent event) {
		System.out.println("Clicle btn Buscar");
		StringBuilder qry = new StringBuilder();
		qry.append("From Store_pedido p ");
		
		qry.append("WHERE p.folio>='"+lblPrefixFolio.getText());
	//	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		qry.append(GeneralMethods.validIfNull(inputBusqFolio.getText(),"-%s'"));
		qry.append(GeneralMethods.validIfNull(inputBusqCliente.getText()," AND p.cliente>='%s' "));
		String stts="";
		if(cbxBusqEstatus.getValue()!=null ) {
			if("ACTIVO".equals(cbxBusqEstatus.getValue().toUpperCase())) {
				stts ="1";
			}
			else if("INACTIVO".equals(cbxBusqEstatus.getValue().toUpperCase())) {
				stts ="0";
			}
		}
		qry.append(GeneralMethods.validIfNull(stts," AND p.id_estatus=%s "));
		
		qry.append(GeneralMethods.validIfNull(dt.format(dateBusqIni.getValue())," AND p.fec_pedido >= '%s' "));
		qry.append(GeneralMethods.validIfNull(dt.format(dateBusqFin.getValue())," AND p.fec_pedido <= '%s' "));
		
		System.out.println("qry:"+qry);
		List<Store_pedido> lstPedidos= pedidoService.consultPedido(qry.toString());
		
		tblPedido.setItems(FXCollections.observableList(lstPedidos));
	}
	
	
	
	@FXML
	private void cleanBusqform(MouseEvent event) {
		initMethod();
	}
	/**
	 * Formatea y asignacion de valores a los componentes
	 */
	private void initMethod() {
		String[] arrayStts= {"","Activo","Inactivo"};
		dateBusqIni.setValue(LocalDate.now());
		dateBusqFin.setValue(LocalDate.now());
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
		/* Panel de Home resize de acuerdo al tama�o del Pane padre*/
		
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
