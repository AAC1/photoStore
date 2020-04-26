package mx.com.bitmaking.application.controller;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

@Component
public class ModalConfirmController {
	@FXML private JFXButton btnConfirm;
	@FXML private JFXButton btnCancelar;
	@FXML private Label lblMsg;
	@FXML private AnchorPane formInput;
	@FXML private Label lblInput;
	@FXML private JFXTextField inputValue;
	
	
	
	/**
	 * @return the formInput
	 */
	public AnchorPane getFormInput() {
		return formInput;
	}
	/**
	 * @param formInput the formInput to set
	 */
	public void setFormInput(AnchorPane formInput) {
		this.formInput = formInput;
	}
	/**
	 * @return the lblInput
	 */
	public Label getLblInput() {
		return lblInput;
	}
	/**
	 * @param lblInput the lblInput to set
	 */
	public void setLblInput(Label lblInput) {
		this.lblInput = lblInput;
	}
	/**
	 * @return the inputValue
	 */
	public JFXTextField getInputValue() {
		return inputValue;
	}
	/**
	 * @param inputValue the inputValue to set
	 */
	public void setInputValue(JFXTextField inputValue) {
		this.inputValue = inputValue;
	}
	public JFXButton getBtnConfirm() {
		return btnConfirm;
	}
	public void setBtnConfirm(JFXButton btnConfirm) {
		this.btnConfirm = btnConfirm;
	}
	public JFXButton getBtnCancelar() {
		return btnCancelar;
	}
	public void setBtnCancelar(JFXButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}
	public Label getLblMsg() {
		return lblMsg;
	}
	public void setLblMsg(Label lblMsg) {
		this.lblMsg = lblMsg;
	}
	
	
}
