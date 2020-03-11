package mx.com.bitmaking.application.controller;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

@Component
public class SucursalController {
	
	@FXML private JFXButton btnAgregar;
	@FXML private JFXButton btnEditar;
	@FXML private JFXButton btnEliminar;
	@FXML private JFXButton btnAccept;
	@FXML private JFXButton btnCancel;
	@FXML private JFXButton btnSalir;
	
	
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}



	public void setBtnSalir(JFXButton btnSalir) {
		this.btnSalir = btnSalir;
	}



	public void initialize() {
		
	}
	
	
	
}
