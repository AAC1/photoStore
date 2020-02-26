package mx.com.bitmaking.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.service.IStoreCatEstatusService;
import mx.com.bitmaking.application.util.Flags;
@Component
public class EditPedidoController {
	@FXML
	private JFXButton btnAccept;
	@FXML
	private JFXButton btnCancel;
	@FXML
	private JFXTextField inputFolio;
	@FXML
	private JFXTextField inputClte;
	@FXML
	private JFXTextField inputDesc;
	@FXML
	private JFXTextField inputMontoTot;
	@FXML
	private JFXComboBox<String> cbxEstatus;
	
	@FXML
	private JFXTextField inputMontoAnt;
	

	@Autowired
	@Qualifier("StoreCatEstatusService")
	IStoreCatEstatusService catEstatusService;

	@Autowired
	@Qualifier("remoteStoreCatEstatusService")
	IStoreCatEstatusService remoteCatEstatusService;
	
	/**
	 * @return the btnAccept
	 */
	public JFXButton getBtnAccept() {
		return btnAccept;
	}

	/**
	 * @return the btnCancel
	 */
	public JFXButton getBtnCancel() {
		return btnCancel;
	}

	/**
	 * @return the inputFolio
	 */
	public JFXTextField getInputFolio() {
		return inputFolio;
	}

	/**
	 * @return the inputClte
	 */
	public JFXTextField getInputClte() {
		return inputClte;
	}

	/**
	 * @return the inputDesc
	 */
	public JFXTextField getInputDesc() {
		return inputDesc;
	}

	/**
	 * @return the inputMontoTot
	 */
	public JFXTextField getInputMontoTot() {
		return inputMontoTot;
	}

	/**
	 * @return the cbxEstatus
	 */
	public JFXComboBox<String> getCbxEstatus() {
		return cbxEstatus;
	}
	
	



	/**
	 * @return the inputMontoAnt
	 */
	public JFXTextField getInputMontoAnt() {
		return inputMontoAnt;
	}

	/**
	 * @param inputMontoAnt the inputMontoAnt to set
	 */
	public void setInputMontoAnt(JFXTextField inputMontoAnt) {
		this.inputMontoAnt = inputMontoAnt;
	}

	public void initialize() {
		getLstEstatus();
	}
	
	private void getLstEstatus() {
		/*
		List<Store_cat_estatus> lstEstatus = catEstatusService.getListEstatus();
		String[] arrayStts = new String[lstEstatus.size()];
		for(int i=0; i<lstEstatus.size();i++) {
			arrayStts[i] = lstEstatus.get(i).getEstatus();
		}
		cbxEstatus.setItems(FXCollections.observableArrayList(arrayStts));
		*/
		cbxEstatus.getItems().removeAll(cbxEstatus.getItems());
		List<Store_cat_estatus> lstEstatus = (Flags.remote_valid)?remoteCatEstatusService.getListEstatus():
																	catEstatusService.getListEstatus();
		String[] arrayStts = new String[lstEstatus.size()];
		for(int i=0; i<lstEstatus.size();i++) {
			arrayStts[i] = lstEstatus.get(i).getEstatus();
		}
		cbxEstatus.setItems(FXCollections.observableArrayList(arrayStts));
	}
}
