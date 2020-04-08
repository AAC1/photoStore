package mx.com.bitmaking.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
@Component
public class EditSttsProdController {
	@FXML
	private JFXButton btnAccept;
	@FXML
	private JFXButton btnCancel;
	@FXML
	private JFXTextField inputBarcode;
	@FXML
	private JFXTextField inputCantidad;
	@FXML
	private JFXTextField inputDesc;
	@FXML
	private JFXTextField inputMontoTot;
	@FXML
	private JFXComboBox<String> cbxEstatus;
	
	
	/**
	 * @return the inputBarcode
	 */
	public JFXTextField getInputBarcode() {
		return inputBarcode;
	}
	/**
	 * @param inputBarcode the inputBarcode to set
	 */
	public void setInputBarcode(JFXTextField inputBarcode) {
		this.inputBarcode = inputBarcode;
	}
	/**
	 * @return the inputCantidad
	 */
	public JFXTextField getInputCantidad() {
		return inputCantidad;
	}
	/**
	 * @param inputCantidad the inputCantidad to set
	 */
	public void setInputCantidad(JFXTextField inputCantidad) {
		this.inputCantidad = inputCantidad;
	}
	/**
	 * @return the inputDesc
	 */
	public JFXTextField getInputDesc() {
		return inputDesc;
	}
	/**
	 * @param inputDesc the inputDesc to set
	 */
	public void setInputDesc(JFXTextField inputDesc) {
		this.inputDesc = inputDesc;
	}
	/**
	 * @return the inputMontoTot
	 */
	public JFXTextField getInputMontoTot() {
		return inputMontoTot;
	}
	/**
	 * @param inputMontoTot the inputMontoTot to set
	 */
	public void setInputMontoTot(JFXTextField inputMontoTot) {
		this.inputMontoTot = inputMontoTot;
	}
	/**
	 * @return the cbxEstatus
	 */
	public JFXComboBox<String> getCbxEstatus() {
		return cbxEstatus;
	}
	/**
	 * @param cbxEstatus the cbxEstatus to set
	 */
	public void setCbxEstatus(JFXComboBox<String> cbxEstatus) {
		this.cbxEstatus = cbxEstatus;
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
	
	public void initialize(){
		List<String>lstStts = new ArrayList<>();
		lstStts.add("PENDIENTE"); 
		lstStts.add("TERMINADO");
		cbxEstatus.getItems().addAll(FXCollections.observableArrayList(lstStts));
	}
}
