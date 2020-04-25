package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import mx.com.bitmaking.application.dto.TbCorteCajaDTO;
import mx.com.bitmaking.application.dto.CorteCajaResumeDTO;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.GeneralMethods;

@Controller
public class CorteCajaController {
	
	@FXML private TableView<TbCorteCajaDTO> tblCorteCaja;
	@FXML private TableColumn<TbCorteCajaDTO,String> colTipo;
	@FXML private TableColumn<TbCorteCajaDTO,String> colDenominacion;
	@FXML private TableColumn<TbCorteCajaDTO,JFXTextField> colCantidad;
	@FXML private TableColumn<TbCorteCajaDTO,Double> colImporte;
	
	@FXML private JFXButton btnCargoAbono;
	@FXML private JFXButton btnSendMail;
	@FXML private JFXButton btnCerrarCaja;
	@FXML private JFXButton btnCancelar;
	@FXML private JFXButton btnSave;
	@FXML private JFXButton btnClean;
	
	//@FXML private JFXTextField inputTotal;
	@FXML private Label lblUsr;
	@FXML private Label lblFecha;
	
	@FXML private TableView<CorteCajaResumeDTO> tblResume;
	@FXML private TableColumn<CorteCajaResumeDTO,String> colResumeDesc;
	@FXML private TableColumn<CorteCajaResumeDTO,String> colResumeImporte;
	@FXML private TableColumn<CorteCajaResumeDTO,String> colResumeTotal;
	
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
			fillTableDenominacion();
			fillTableResume();
			
			
			PseudoClass redColor = PseudoClass.getPseudoClass("red-text");
	   		PseudoClass greenColor = PseudoClass.getPseudoClass("green-text");
	   		PseudoClass blackColor = PseudoClass.getPseudoClass("black-text");
	   		PseudoClass blueColor = PseudoClass.getPseudoClass("blue-text");
			tblResume.setRowFactory(new Callback<TableView<CorteCajaResumeDTO>, TableRow<CorteCajaResumeDTO>>() {
		        @Override
		        public TableRow<CorteCajaResumeDTO> call(TableView<CorteCajaResumeDTO> tableView) {
		        	
		            final TableRow<CorteCajaResumeDTO> rowTbl = new TableRow<CorteCajaResumeDTO>() {
		                @Override
		                protected void updateItem(CorteCajaResumeDTO row, boolean empty) {
		                    super.updateItem(row, empty);
		                    if (!empty){
		                    	/*if(isSelected()){
		                    		pseudoClassStateChanged(blueColor,true);
		                    	//	setStyle("-fx-background-color:#0467A9; ");
		                    	//	setTextFill(Color.WHITE);
		                    	
		                    	}*/
		                    	if(row.getDescripcion().contains("(-)")){
			                    	
			        					pseudoClassStateChanged(greenColor,false);
			        					pseudoClassStateChanged(redColor,true);
			        					pseudoClassStateChanged(blackColor,false);
			        				
		                    	}else if(row.getDescripcion().contains("(+)")){
		                    	
		                    		pseudoClassStateChanged(blackColor,false);
		                    		pseudoClassStateChanged(redColor,false);
		                    		pseudoClassStateChanged(greenColor,true);
		                    	}else{
		                    		pseudoClassStateChanged(blackColor,true);
		                    		pseudoClassStateChanged(redColor,false);
		                    		pseudoClassStateChanged(greenColor,false);
		                    	}
		                    }
		                    	
		                }
		            };
		            return rowTbl;
		        }
		    });
		}else{
			GeneralMethods.modalMsg("", "", "No fue posible obtener el usuario. Inicie sesi\u00F3n e intentelo de nuevo");
		}
		
		
		
	}
	
	@FXML
	private void guardaCorteCaja(){
		
	}
	
	@FXML
	private void cleanCorteCaja(){
		fillTableDenominacion();
		fillTableResume();
	}
	
	private void fillTableResume(){
		List<CorteCajaResumeDTO> listResume = new ArrayList<>();

		listResume.add(new CorteCajaResumeDTO("(-) Monto inicial","500.00",""));
		listResume.add(new CorteCajaResumeDTO("","",""));
		listResume.add(new CorteCajaResumeDTO("(-) CARGO","500.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Abono","500.00",""));
		
		listResume.add(new CorteCajaResumeDTO("DENOMINACI\u00D3N","",""));
		listResume.add(new CorteCajaResumeDTO("(+) Billetes de 1000","0.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Billetes de 500","2,500.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Billetes de 200","4,500.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Billetes de 100","500.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Billetes de 50","1,000.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Billetes de 20","500.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Monedas de 10","50.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Monedas de 5","40.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Monedas de 2","18.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Monedas de 1","67.00",""));
		listResume.add(new CorteCajaResumeDTO("(+) Monedas de 0.50","25.50",""));

		listResume.add(new CorteCajaResumeDTO("","","15,500.50"));
		
		
		tblResume.getItems().removeAll(tblResume.getItems());
		tblResume.getItems().addAll(listResume);
		
	}
	/*
	private EventHandler<KeyEvent> calculateImport(CorteCajaDTO objRow) {
			return new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent event) {
					System.out.println(event.getCode());
					//GeneralMethods.formatInteger(objRow.getCantidad());
					String val = objRow.getCantidad().getText().trim().replaceAll("[^0-9]", "");
					System.out.println("val:"+val);
					
					//Valida que tenga valor
					if(val!=null && !"".equals(val) ){
						
						double importe= Double.parseDouble(val) * 
									Double.parseDouble(objRow.getDenominacion().trim().replace(",","")); 
						System.out.println("importe:"+importe);
						objRow.setImporte(GeneralMethods.formatCurrentNumber(String.valueOf(importe)));
						
					}else{
						objRow.setImporte("0.0");
					}
					//formatea numero ##,###
					objRow.getCantidad().setText(String.format("%,d", Integer.parseInt(val)));
				}
				
			};
	}
	*/
	public  ChangeListener<String> calculateImport(JFXTextField field,int idx){
		
		return new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			//	System.out.println(newValue);
				String decimalForm ="";
				TbCorteCajaDTO objRow = tblCorteCaja.getItems().get(idx);
				if(!newValue.equals("")) {
				
					newValue = newValue.replaceAll("[^0-9]", "");
					if(!newValue.equals("") ) {
						decimalForm = String.format("%,d",Integer.parseInt(newValue));
						newValue = decimalForm;
					}
					
					if(newValue.length() > Constantes.NUMBER_LENGTH-3) {
						field.setText(oldValue);
					}else {
						if(!newValue.equals("")) {
							BigDecimal importe= new BigDecimal(newValue.trim().replace(",",""));
							importe = importe.multiply(new BigDecimal(objRow.getDenominacion().trim().replace(",","")));
							
							objRow.setImporte(GeneralMethods.formatCurrentNumber(String.valueOf(importe)));
						//	System.out.println("importe:"+importe);	
						}else{
							objRow.setImporte("0.0");
						}
						
						field.setText(newValue);
					}
					
				}else{
					objRow.setImporte("0.0");
				}
				tblCorteCaja.setVisible(false);
				tblCorteCaja.getItems().set(idx, objRow);
				tblCorteCaja.setVisible(true);
			}
			
		};
		
	}
	private void fillTableDenominacion(){
		List<TbCorteCajaDTO> listDeno = new ArrayList<>();
		JFXTextField input = new JFXTextField();
		input.setId("input1000");
		//input.textProperty().addListener(GeneralMethods.formatInteger(input));
		//CorteCajaDTO obj1000 =new CorteCajaDTO("Billete","1,000.00","0.0",input);
		input.textProperty().addListener(calculateImport(input,0));
		listDeno.add(new TbCorteCajaDTO("Billete","1,000.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input500");
		input.textProperty().addListener(calculateImport(input,1));
		listDeno.add(new TbCorteCajaDTO("Billete","500.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input200");
		input.textProperty().addListener(calculateImport(input,2));
		listDeno.add(new TbCorteCajaDTO("Billete","200.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input100");
		input.textProperty().addListener(calculateImport(input,3));
		listDeno.add(new TbCorteCajaDTO("Billete","100.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input50");
		input.textProperty().addListener(calculateImport(input,4));
		listDeno.add(new TbCorteCajaDTO("Billete","50.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input20");
		input.textProperty().addListener(calculateImport(input,5));
		listDeno.add(new TbCorteCajaDTO("Billete","20.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input10");
		input.textProperty().addListener(calculateImport(input,6));
		listDeno.add(new TbCorteCajaDTO("Moneda","10.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input5");
		input.textProperty().addListener(calculateImport(input,7));
		listDeno.add(new TbCorteCajaDTO("Moneda","5.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input2");
		input.textProperty().addListener(calculateImport(input,8));
		listDeno.add(new TbCorteCajaDTO("Moneda","2.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input1");
		input.textProperty().addListener(calculateImport(input,9));
		listDeno.add(new TbCorteCajaDTO("Moneda","1.00","0.0",input));
		
		input = new JFXTextField();
		input.setId("input050");
		input.textProperty().addListener(calculateImport(input,10));
		listDeno.add(new TbCorteCajaDTO("Moneda","0.50","0.0",input));

		tblCorteCaja.getItems().removeAll(tblCorteCaja.getItems());
		tblCorteCaja.getItems().addAll(listDeno);
	}
	
	private void responsiveGUI() {
		/* Panel de Home resize de acuerdo al tama�o del Pane padre*/
		colTipo.prefWidthProperty().bind(tblCorteCaja.widthProperty().multiply(0.2));
		colDenominacion.prefWidthProperty().bind(tblCorteCaja.widthProperty().multiply(0.3));
		colCantidad.prefWidthProperty().bind(tblCorteCaja.widthProperty().multiply(0.2));
		colImporte.prefWidthProperty().bind(tblCorteCaja.widthProperty().multiply(0.3));
		
		colTipo.setCellValueFactory(new PropertyValueFactory<TbCorteCajaDTO, String>("tipo"));
		colDenominacion.setCellValueFactory(new PropertyValueFactory<TbCorteCajaDTO, String>("denominacion"));
		colCantidad.setCellValueFactory(new PropertyValueFactory<TbCorteCajaDTO, JFXTextField>("cantidad"));
		colImporte.setCellValueFactory(new PropertyValueFactory<TbCorteCajaDTO, Double>("importe"));
		
		
		colResumeDesc.prefWidthProperty().bind(tblResume.widthProperty().multiply(0.6));
		colResumeImporte.prefWidthProperty().bind(tblResume.widthProperty().multiply(0.2));
		colResumeTotal.prefWidthProperty().bind(tblResume.widthProperty().multiply(0.2));
		
		colResumeDesc.setCellValueFactory(new PropertyValueFactory<CorteCajaResumeDTO, String>("descripcion"));
		colResumeImporte.setCellValueFactory(new PropertyValueFactory<CorteCajaResumeDTO, String>("importe"));
		colResumeTotal.setCellValueFactory(new PropertyValueFactory<CorteCajaResumeDTO, String>("total"));
		
	}
}
