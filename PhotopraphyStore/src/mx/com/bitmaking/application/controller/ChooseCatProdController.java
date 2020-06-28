package mx.com.bitmaking.application.controller;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.fxml.FXML;

@Controller
public class ChooseCatProdController {

	@FXML
	private JFXButton btnConfirm;
	@FXML
	private JFXButton btnCancelar;
	@FXML
	private JFXRadioButton radCategoria;
	@FXML
	private JFXRadioButton radProducto;
	
	
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
	public JFXRadioButton getRadCategoria() {
		return radCategoria;
	}
	public void setRadCategoria(JFXRadioButton radCategoria) {
		this.radCategoria = radCategoria;
	}
	public JFXRadioButton getRadProducto() {
		return radProducto;
	}
	public void setRadProducto(JFXRadioButton radProducto) {
		this.radProducto = radProducto;
	}
	@FXML 
	private void selectCat() {
		radProducto.setSelected(false);
	}
	@FXML 
	private void selectProd() {
		radCategoria.setSelected(false);
	}
	
	
}
