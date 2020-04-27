package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import mx.com.bitmaking.application.entity.Store_cargo_abono;
import mx.com.bitmaking.application.iservice.IStoreCargoAbonoService;
import mx.com.bitmaking.application.util.Flags;

@Controller
public class GastoController {
	
	@FXML private JFXComboBox<String>  cbxTipoGasto;
	@FXML private JFXButton  btnCancel;
	@FXML private JFXButton  btnAccept;
	@FXML private JFXTextField inputMonto;
	@FXML private JFXTextField inputConcepto;
	

	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	
	public void initialize(){
		List<String>lstTipoGasto = new ArrayList<>();
		lstTipoGasto.add("Cargo");
		lstTipoGasto.add("Abono");
		cbxTipoGasto.getItems().addAll(lstTipoGasto);
		
		
	}
	
	
	
	/**
	 * @return the cbxTipoGasto
	 */
	public JFXComboBox<String> getCbxTipoGasto() {
		return cbxTipoGasto;
	}



	/**
	 * @param cbxTipoGasto the cbxTipoGasto to set
	 */
	public void setCbxTipoGasto(JFXComboBox<String> cbxTipoGasto) {
		this.cbxTipoGasto = cbxTipoGasto;
	}



	/**
	 * @return the btnAccept
	 */
	public JFXButton getBtnAccept() {
		return btnAccept;
	}



	/**
	 * @param btnAccept the btnAccept to set
	 */
	public void setBtnAccept(JFXButton btnAccept) {
		this.btnAccept = btnAccept;
	}



	/**
	 * @return the inputMonto
	 */
	public JFXTextField getInputMonto() {
		return inputMonto;
	}



	/**
	 * @param inputMonto the inputMonto to set
	 */
	public void setInputMonto(JFXTextField inputMonto) {
		this.inputMonto = inputMonto;
	}



	/**
	 * @return the inputConcepto
	 */
	public JFXTextField getInputConcepto() {
		return inputConcepto;
	}



	/**
	 * @param inputConcepto the inputConcepto to set
	 */
	public void setInputConcepto(JFXTextField inputConcepto) {
		this.inputConcepto = inputConcepto;
	}



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
