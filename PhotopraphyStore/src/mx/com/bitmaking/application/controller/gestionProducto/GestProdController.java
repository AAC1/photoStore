package mx.com.bitmaking.application.controller.gestionProducto;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

public class GestProdController {
	@FXML private JFXButton btnAddProd;
	@FXML private JFXButton btnEliminarProd;
	@FXML private JFXButton btnEdtProd;
	@FXML private JFXButton btnSalir;
	
	
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}

	public void initialize() {
		
	}
}
