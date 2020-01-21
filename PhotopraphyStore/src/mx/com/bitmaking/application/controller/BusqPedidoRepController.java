package mx.com.bitmaking.application.controller;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Component
public class BusqPedidoRepController {

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
	@FXML private TableView tblPedido;
	@FXML private TableColumn colFolio;
	@FXML private TableColumn colCliente;
	@FXML private TableColumn colTelCliente;
	@FXML private TableColumn colDesc;
	@FXML private TableColumn colFecPedido;
	@FXML private TableColumn colFecEntreg;
	@FXML private TableColumn colEstatus;
	@FXML private TableColumn colMontoAnt;
	@FXML private TableColumn colMontoTotal;
	
	
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}



	public void initialize() {
		
	}
	
}
