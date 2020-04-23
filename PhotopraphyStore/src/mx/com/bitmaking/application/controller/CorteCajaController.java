package mx.com.bitmaking.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mx.com.bitmaking.application.dto.CorteCajaDTO;

public class CorteCajaController {
	
	@FXML private TableView<CorteCajaDTO> tblCorteCaja;
	@FXML private TableColumn<CorteCajaDTO,String> colTipo;
	@FXML private TableColumn<CorteCajaDTO,String> colDenominacion;
	@FXML private TableColumn<CorteCajaDTO,String> colCantidad;
	@FXML private TableColumn<CorteCajaDTO,Double> colImporte;
	
	
}
