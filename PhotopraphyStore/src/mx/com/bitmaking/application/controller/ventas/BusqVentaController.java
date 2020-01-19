package mx.com.bitmaking.application.controller.ventas;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;

@Component
public class BusqVentaController {
	
	@FXML private JFXButton btnBusqByFolio;
	@FXML private JFXTextField inputBusqFolio;
	/**
	 * @return the btnBusqByFolio
	 */
	public JFXButton getBtnBusqByFolio() {
		return btnBusqByFolio;
	}
	/**
	 * @return the inputBusqFolio
	 */
	public JFXTextField getInputBusqFolio() {
		return inputBusqFolio;
	}
	
	
	
	
}
