package mx.com.bitmaking.application.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.com.bitmaking.application.dto.CorteCajaDTO;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.util.GeneralMethods;

@Controller
public class CorteCajaController {
	
	@FXML private TableView<CorteCajaDTO> tblCorteCaja;
	@FXML private TableColumn<CorteCajaDTO,String> colTipo;
	@FXML private TableColumn<CorteCajaDTO,String> colDenominacion;
	@FXML private TableColumn<CorteCajaDTO,JFXTextField> colCantidad;
	@FXML private TableColumn<CorteCajaDTO,Double> colImporte;
	
	@FXML private JFXButton btnCargoAbono;
	@FXML private JFXButton btnSendMail;
	@FXML private JFXButton btnCerrarCaja;
	@FXML private JFXButton btnCancelar;
	
	@FXML private JFXTextField inputTotal;
	@FXML private Label lblUsr;
	@FXML private Label lblFecha;
	
	private UserSessionDTO instance = null;
	
	/**
	 * @return the btnCancelar
	 */
	public JFXButton getBtnCancelar() {
		return btnCancelar;
	}
	/**
	 * @param btnCancelar the btnCancelar to set
	 */
	public void setBtnCancelar(JFXButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}
	
	
	
	public void initialize(){
		instance = UserSessionDTO.getInstance();
		if(instance!=null){
			SimpleDateFormat dt =  new SimpleDateFormat("dd/MM/yyyy");
			lblUsr.setText(instance.getNombre());
			lblFecha.setText(dt.format(new Date()));
			
			responsiveGUI();
			fillTable();
		}else{
			GeneralMethods.modalMsg("", "", "No fue posible obtener el usuario. Inicie sesi\u00F3n e intentelo de nuevo");
		}
		
		
		
	}
	
	private void fillTable(){
		List<CorteCajaDTO> listDeno = new ArrayList<>();
		JFXTextField input = new JFXTextField();
		input.setId("input1000");
		listDeno.add(new CorteCajaDTO("Billete","1,000.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input500");
		listDeno.add(new CorteCajaDTO("Billete","500.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input200");
		listDeno.add(new CorteCajaDTO("Billete","200.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input100");
		listDeno.add(new CorteCajaDTO("Billete","100.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input50");
		listDeno.add(new CorteCajaDTO("Billete","50.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input20");
		listDeno.add(new CorteCajaDTO("Billete","20.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input10");
		listDeno.add(new CorteCajaDTO("Moneda","10.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input5");
		listDeno.add(new CorteCajaDTO("Moneda","5.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input2");
		listDeno.add(new CorteCajaDTO("Moneda","2.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input1");
		listDeno.add(new CorteCajaDTO("Moneda","1.00",0.0,input));
		
		input = new JFXTextField();
		input.setId("input050");
		listDeno.add(new CorteCajaDTO("Moneda","0.50",0.0,input));

		tblCorteCaja.getItems().removeAll(tblCorteCaja.getItems());
		tblCorteCaja.getItems().addAll(listDeno);
	}
	
	private void responsiveGUI() {
		/* Panel de Home resize de acuerdo al tama�o del Pane padre*/
		colTipo.prefWidthProperty().bind(tblCorteCaja.widthProperty().multiply(0.2));
		colDenominacion.prefWidthProperty().bind(tblCorteCaja.widthProperty().multiply(0.3));
		colCantidad.prefWidthProperty().bind(tblCorteCaja.widthProperty().multiply(0.3));
		colImporte.prefWidthProperty().bind(tblCorteCaja.widthProperty().multiply(0.2));
		
		colTipo.setCellValueFactory(new PropertyValueFactory<CorteCajaDTO, String>("tipo"));
		colDenominacion.setCellValueFactory(new PropertyValueFactory<CorteCajaDTO, String>("denominacion"));
		colCantidad.setCellValueFactory(new PropertyValueFactory<CorteCajaDTO, JFXTextField>("cantidad"));
		colImporte.setCellValueFactory(new PropertyValueFactory<CorteCajaDTO, Double>("importe"));
	}
}
