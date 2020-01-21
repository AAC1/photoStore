package mx.com.bitmaking.application.controller;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class ModalConfirmController {
	@FXML private JFXButton btnConfirm;
	@FXML private JFXButton btnCancelar;
	@FXML private Label lblMsg;
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
