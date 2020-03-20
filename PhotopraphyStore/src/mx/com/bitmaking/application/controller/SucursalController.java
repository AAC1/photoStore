package mx.com.bitmaking.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.iservice.IStoreSucursalService;
import mx.com.bitmaking.application.util.Flags;

@Component
public class SucursalController {
	
	@FXML private JFXButton btnAgregar;
	@FXML private JFXButton btnEditar;
	@FXML private JFXButton btnEliminar;
	@FXML private JFXButton btnAccept;
	@FXML private JFXButton btnCancel;
	@FXML private JFXButton btnSalir;
	
	@FXML private AnchorPane containerBusqResp;
	@FXML private JFXButton btnOpenBusq;
	@FXML private JFXButton btnCloseBusq;
	@FXML private JFXButton btnBuscar;
	@FXML private JFXButton btnClean;
	@FXML private JFXTextField inputBusqSucursal;
	@FXML private JFXTextField inputBusqPrefijo;
	@FXML private JFXTextField inputBusqRazonSocial;
	
	@Autowired
	@Qualifier("StoreSucursalService")
	IStoreSucursalService sucursalService;
	
	@Autowired
	@Qualifier("remoteStoreSucursalService")
	IStoreSucursalService remoteSucursalService;
	
	public JFXButton getBtnSalir() {
		return btnSalir;
	}



	public void setBtnSalir(JFXButton btnSalir) {
		this.btnSalir = btnSalir;
	}



	public void initialize() {
		
	}
	
	@FXML
	private void openSearch() {
		containerBusqResp.setVisible(true);
	}
	@FXML
	private void closeSearch() {
		containerBusqResp.setVisible(false);
		cleanBusqform();
	}
	@FXML
	private void cleanBusqform() {
		inputBusqSucursal.setText("");
		inputBusqPrefijo.setText("");
		inputBusqRazonSocial.setText("");
	}
	@FXML
	private void buscaSucursal() {
		List<Store_sucursal> lstSuc =  (Flags.remote_valid)?
				remoteSucursalService.getSuc("", "", ""):
					sucursalService.getSuc("", "", "");
	}
	
}
