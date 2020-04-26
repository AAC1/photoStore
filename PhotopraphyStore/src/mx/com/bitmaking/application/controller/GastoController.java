package mx.com.bitmaking.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;

@Controller
public class GastoController {
	
	@FXML private JFXComboBox<String>  cbxTipoGasto;
	@FXML private JFXButton  btnCancel;
	@FXML private JFXButton  btnAccept;
	@FXML private JFXTextField inputMonto;
	
	
	
	/**
	 * @return the btnCancel
	 */
	public JFXButton getBtnCancel() {
		return btnCancel;
	}


	/**
	 * @param btnCancel the btnCancel to set
	 */
	public void setBtnCancel(JFXButton btnCancel) {
		this.btnCancel = btnCancel;
	}


	@FXML 
	private void saveGasto(){
		
	}
}
