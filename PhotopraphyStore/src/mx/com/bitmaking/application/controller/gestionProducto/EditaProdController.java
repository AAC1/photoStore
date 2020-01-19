package mx.com.bitmaking.application.controller.gestionProducto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

@Component
//@Scope("prototype")
public class EditaProdController {
	
	@FXML private JFXButton btnAccept;
	@FXML private JFXButton btnCancel;
	@FXML private JFXComboBox  cbxEstatusProd;
	@FXML private JFXTextField inputProdName;
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
	/**
	 * @return the cbxEstatusProd
	 */
	public JFXComboBox getCbxEstatusProd() {
		return cbxEstatusProd;
	}
	/**
	 * @param cbxEstatusProd the cbxEstatusProd to set
	 */
	public void setCbxEstatusProd(JFXComboBox cbxEstatusProd) {
		this.cbxEstatusProd = cbxEstatusProd;
	}
	/**
	 * @return the inputProdName
	 */
	public JFXTextField getInputProdName() {
		return inputProdName;
	}
	/**
	 * @param inputProdName the inputProdName to set
	 */
	public void setInputProdName(JFXTextField inputProdName) {
		this.inputProdName = inputProdName;
	}
	
	
}
