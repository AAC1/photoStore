package mx.com.bitmaking.application.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import mx.com.bitmaking.application.dto.TbCorteCajaDTO;
import mx.com.bitmaking.application.dto.CorteCajaResumeDTO;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.entity.Store_cargo_abono;
import mx.com.bitmaking.application.entity.Store_corte_caja;
import mx.com.bitmaking.application.iservice.IStoreCargoAbonoService;
import mx.com.bitmaking.application.iservice.IStoreCorteCajaService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;
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
	
	@Autowired
	private ApplicationContext context ;
	
	@Autowired
	@Qualifier("StoreCorteCajaService")
	IStoreCorteCajaService corteCajaService;
	@Autowired
	@Qualifier("remoteStoreCorteCajaService")
	IStoreCorteCajaService remoteCorteCajaService;
	
	@Autowired
	@Qualifier("StoreCargoAbonoService")
	IStoreCargoAbonoService storeCargoAbonoService;
	
	@Autowired
	@Qualifier("remoteStoreCargoAbonoService")
	IStoreCargoAbonoService remoteStoreCargoAbonoService;
	
	private UserSessionDTO instance = null;
	private Stage stageModalMontoIni = null;
	private Store_corte_caja corteCaja = null;
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
			fillTableResume();
			fillTableDenominacion(corteCaja);
			
			
			
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
			if(corteCaja == null){
				corteCaja = new Store_corte_caja();
				corteCaja.setImporte_ini(new BigDecimal(0));
				corteCaja.setFecha(new Date());
				corteCaja.setId_sucursal(instance.getId_sucursal());
			}
			ObservableList<TbCorteCajaDTO> listCCaja = tblCorteCaja.getItems();
			
			corteCaja.setDeno050(new BigDecimal(listCCaja.get(10).getImporte().replace(",", "")));
			corteCaja.setDeno1(new BigDecimal(listCCaja.get(9).getImporte().replace(",", "")));
			corteCaja.setDeno2(new BigDecimal(listCCaja.get(8).getImporte().replace(",", "")));
			corteCaja.setDeno5(new BigDecimal(listCCaja.get(7).getImporte().replace(",", "")));
			corteCaja.setDeno10(new BigDecimal(listCCaja.get(6).getImporte().replace(",", "")));
			corteCaja.setDeno20(new BigDecimal(listCCaja.get(5).getImporte().replace(",", "")));
			corteCaja.setDeno50(new BigDecimal(listCCaja.get(4).getImporte().replace(",", "")));
			corteCaja.setDeno100(new BigDecimal(listCCaja.get(3).getImporte().replace(",", "")));
			corteCaja.setDeno200(new BigDecimal(listCCaja.get(2).getImporte().replace(",", "")));
			corteCaja.setDeno500(new BigDecimal(listCCaja.get(1).getImporte().replace(",", "")));
			corteCaja.setDeno1000(new BigDecimal(listCCaja.get(0).getImporte().replace(",", "")));
			BigDecimal importe = new BigDecimal(0);
			importe = importe.add(corteCaja.getDeno050()).add(corteCaja.getDeno1())
					.add(corteCaja.getDeno2()).add(corteCaja.getDeno5()).add(corteCaja.getDeno10())
					.add(corteCaja.getDeno20()).add(corteCaja.getDeno100()).add(corteCaja.getDeno200())
					.add(corteCaja.getDeno500().add(corteCaja.getDeno1000()));
			corteCaja.setImporte(importe);
			
			
			if(!Flags.remote_valid){
				corteCajaService.saveCorteCaja(corteCaja);
			}else{
				remoteCorteCajaService.saveCorteCaja(corteCaja);
			}
			fillTableResume();
	}
	
	@FXML
	private void cleanCorteCaja(){
		fillTableDenominacion(null);
		fillTableResume();
	}
	
	private void fillTableResume(){
		List<CorteCajaResumeDTO> listResume = new ArrayList<>();
		List<Store_cargo_abono> listCargoAbono = null;
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		//VALIDA SI ES REMOTO O LOCAL
		if(!Flags.remote_valid){
			listCargoAbono = storeCargoAbonoService.getCargoAbonoByDateSuc(dt.format(new Date()), instance.getId_sucursal());
			corteCaja = corteCajaService.getCorteCajaByDate(dt.format(new Date()), instance.getId_sucursal());
		}else{
			listCargoAbono = remoteStoreCargoAbonoService.getCargoAbonoByDateSuc(dt.format(new Date()), instance.getId_sucursal());
			corteCaja = remoteCorteCajaService.getCorteCajaByDate(dt.format(new Date()), instance.getId_sucursal());
		}
		//Obtener CARGOS / ABONOS
		
		
		//En caso de que no se tenga registro en BD
		if(corteCaja !=null){
			
			listResume.add(new CorteCajaResumeDTO("(-) Monto inicial",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getImporte_ini())),
					GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getImporte_ini()))));
			
			listResume.add(new CorteCajaResumeDTO("GASTOS","",""));
			BigDecimal cargo = new BigDecimal(0);
			BigDecimal abono = new BigDecimal(0);
			if(listCargoAbono != null && listCargoAbono.size()>0){
				
				for(int i =0; i<listCargoAbono.size(); i++){
					if("C".equals(listCargoAbono.get(i).getTipo())){//Valida que sea cargo
						cargo = cargo.add(listCargoAbono.get(i).getMonto());
						if((i+1) == listCargoAbono.size())
							listResume.add(new CorteCajaResumeDTO("(-) "+listCargoAbono.get(i).getMotivo(),
										GeneralMethods.formatCurrentNumber(String.valueOf(listCargoAbono.get(i).getMonto())),
										GeneralMethods.formatCurrentNumber(String.valueOf(cargo))));
						else
							listResume.add(new CorteCajaResumeDTO("(-) "+listCargoAbono.get(i).getMotivo(),
									GeneralMethods.formatCurrentNumber(String.valueOf(listCargoAbono.get(i).getMonto())),""));
					}
					
				}
				
				
			}else{
				listResume.add(new CorteCajaResumeDTO("--","0.00","0.00"));
			}
			boolean hasAbono = false;
			listResume.add(new CorteCajaResumeDTO("DEVOLUCIONES","",""));
			if(listCargoAbono != null && listCargoAbono.size()>0){
				
				for(int i =0; i<listCargoAbono.size(); i++){
					if("A".equals(listCargoAbono.get(i).getTipo())){//Valida que sea abono
						hasAbono=true;
						abono = abono.add(listCargoAbono.get(i).getMonto());
						if((i+1) == listCargoAbono.size())
							listResume.add(new CorteCajaResumeDTO("(+) "+listCargoAbono.get(i).getMotivo(),
										GeneralMethods.formatCurrentNumber(String.valueOf(listCargoAbono.get(i).getMonto())),
										GeneralMethods.formatCurrentNumber(String.valueOf(abono))));
						else
							listResume.add(new CorteCajaResumeDTO("(+) "+listCargoAbono.get(i).getMotivo(),
									GeneralMethods.formatCurrentNumber(String.valueOf(listCargoAbono.get(i).getMonto())),""));
					}
					
				}
			}
			if(!hasAbono){
				
				listResume.add(new CorteCajaResumeDTO("--","0.00","0.00"));
			}
			
			listResume.add(new CorteCajaResumeDTO("DENOMINACIONES","",""));//\u00D3
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 1000",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno1000())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 500",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno500())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 200",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno200())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 100",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno100())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 50",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno50())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 20",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno20())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 10",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno10())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 5",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno5())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 2",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno2())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 1",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno1())),""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 0.50",
					GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno050())),
					GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getImporte()))));
			
			BigDecimal total = new BigDecimal(0);
			BigDecimal sum = new BigDecimal(0);
			BigDecimal rest = new BigDecimal(0);
			
			//Suma todos los ingresos
			sum = sum.add(corteCaja.getImporte()); 
			sum = sum.add(abono); 
			
			//Suma todo lo que no sea ingreso del dia
			rest = rest.add(corteCaja.getImporte_ini()); 
			rest = rest.add(cargo); 
			
			total = sum.subtract(rest);
			if(sum.compareTo(rest) <0){  //SUM es menor a REST
				listResume.add(new CorteCajaResumeDTO("","","-"+GeneralMethods.formatCurrentNumber(String.valueOf(total))));
			}else{
				listResume.add(new CorteCajaResumeDTO("","",GeneralMethods.formatCurrentNumber(String.valueOf(total))));
			}
			
			tblResume.getItems().removeAll(tblResume.getItems());
			tblResume.getItems().addAll(listResume);
		}else{
			
			
			listResume.add(new CorteCajaResumeDTO("(-) Monto inicial","0.00","0.00"));
			listResume.add(new CorteCajaResumeDTO("GASTOS","",""));
			listResume.add(new CorteCajaResumeDTO("(-) CARGO","0.00","0.00"));

			listResume.add(new CorteCajaResumeDTO("DEVOLUCIONES","",""));
			listResume.add(new CorteCajaResumeDTO("(+) Abono","0.00","0.00"));
			
			listResume.add(new CorteCajaResumeDTO("DENOMINACIONES","",""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 1000","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 500","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 200","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 100","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 50","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 20","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 10","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 5","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 2","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 1","0.00",""));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 0.50","0.00","0.0"));
	
			listResume.add(new CorteCajaResumeDTO("","","0.0"));
			tblResume.getItems().removeAll(tblResume.getItems());
			tblResume.getItems().addAll(listResume);
			
			ModalConfirmController ctrl = openModalConfirm();
			if(ctrl==null)return;
			
			ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, cancelMontoIni(stageModalMontoIni));
			ctrl.getLblMsg().setText("No se tiene registrado el monto inicial de la caja. Favor de ingresarla");
			ctrl.getLblInput().setText("Monto inicial: ");
			ctrl.getFormInput().setVisible(true);
			ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptMontoIni(stageModalMontoIni,ctrl));
		}
		
		
		
	}
	private EventHandler<MouseEvent> acceptMontoIni(Stage stage,ModalConfirmController ctrl){
		
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					
					
					Store_corte_caja objEntity = new Store_corte_caja();
					String value = ctrl.getInputValue().getText();
					
					if(value!=null && !"".equals(value.trim())){
						if(stage!=null) 
							stage.close();
						
						

						objEntity.setImporte_ini(new BigDecimal(value));
						if(corteCaja == null){
							objEntity.setFecha(new Date());
							objEntity.setId_sucursal(instance.getId_sucursal());
							
							objEntity.setImporte(new BigDecimal(0));
							objEntity.setDeno050(new BigDecimal(0));
							objEntity.setDeno1(new BigDecimal(0));
							objEntity.setDeno2(new BigDecimal(0));
							objEntity.setDeno5(new BigDecimal(0));
							objEntity.setDeno10(new BigDecimal(0));
							objEntity.setDeno20(new BigDecimal(0));
							objEntity.setDeno50(new BigDecimal(0));
							objEntity.setDeno100(new BigDecimal(0));
							objEntity.setDeno200(new BigDecimal(0));
							objEntity.setDeno500(new BigDecimal(0));
							objEntity.setDeno1000(new BigDecimal(0));
						}else{
							objEntity.setId_corte_caja(corteCaja.getId_corte_caja());
							objEntity.setFecha(corteCaja.getFecha());
							objEntity.setId_sucursal(corteCaja.getId_sucursal());
							
						}
						
						if(!Flags.remote_valid){
							corteCajaService.saveCorteCaja(objEntity);
						}else{
							remoteCorteCajaService.saveCorteCaja(objEntity);
						}
						fillTableResume();
					}else{
						GeneralMethods.modalMsg("", "", "Debes ingresar cantidad para el monto inicial");
						
					}
					
					
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
		
		
	}
	private EventHandler<MouseEvent>cancelMontoIni(Stage stage){
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println(event.getSource());
				try {
					if(stage!=null) 
						stage.close();
		        } catch(Exception ex) {
					ex.printStackTrace();
				}
			}};
	}
	private ModalConfirmController openModalConfirm(){
		ModalConfirmController ctrl = null;
		try {
			
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/TreeProduct.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/ModalConfirm.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			Parent sceneEdit= fxmlLoader.load();
			Scene scene = new Scene(sceneEdit,3013,165);
			scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/GestionProductos.css").toExternalForm());
			stageModalMontoIni = new Stage();
			stageModalMontoIni.setScene(scene);
			stageModalMontoIni.setTitle("Abre Caja");
			stageModalMontoIni.setMinHeight(200.0);
			stageModalMontoIni.setMinWidth(350.0);
			stageModalMontoIni.setMaxHeight(350.0);
			stageModalMontoIni.setMaxWidth(200.0);
			stageModalMontoIni.initModality(Modality.APPLICATION_MODAL); 
			stageModalMontoIni.show();
			ctrl = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			
	    } catch(Exception ex) {
			ex.printStackTrace();
			GeneralMethods.modalMsg("ERROR", "", ex.getMessage());
		}
		return ctrl;
	}
	public  ChangeListener<String> calculateImport(JFXTextField field,int idx){
		
		return new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			//	System.out.println(newValue);
				String decimalForm ="";
				if(tblCorteCaja.getItems().size() < idx || tblCorteCaja.getItems().size() <=0){
					return;
				}
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
	private void fillTableDenominacion(Store_corte_caja corteCaja){
		List<TbCorteCajaDTO> listDeno = new ArrayList<>();
		JFXTextField input = new JFXTextField();
		input.setId("input1000");
		//input.textProperty().addListener(GeneralMethods.formatInteger(input));
		//CorteCajaDTO obj1000 =new CorteCajaDTO("Billete","1,000.00","0.0",input);
		input.textProperty().addListener(calculateImport(input,0));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno1000().divide(new BigDecimal(1000)))));
			listDeno.add(new TbCorteCajaDTO("Billete","1,000.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno1000())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Billete","1,000.00","0.0",input));
		}
		
		input = new JFXTextField();
		input.setId("input500");
		input.textProperty().addListener(calculateImport(input,1));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno500().divide(new BigDecimal(500)))));
			listDeno.add(new TbCorteCajaDTO("Billete","500.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno500())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Billete","500.00","0.0",input));
		}
		
		
		input = new JFXTextField();
		input.setId("input200");
		input.textProperty().addListener(calculateImport(input,2));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno200().divide(new BigDecimal(200)))));
			listDeno.add(new TbCorteCajaDTO("Billete","200.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno200())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Billete","200.00","0.0",input));
		}
		
		
		input = new JFXTextField();
		input.setId("input100");
		input.textProperty().addListener(calculateImport(input,3));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno100().divide(new BigDecimal(100)))));
			listDeno.add(new TbCorteCajaDTO("Billete","100.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno100())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Billete","100.00","0.0",input));
		}
		input = new JFXTextField();
		input.setId("input50");
		input.textProperty().addListener(calculateImport(input,4));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno50().divide(new BigDecimal(50)))));
			listDeno.add(new TbCorteCajaDTO("Billete","50.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno50())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Billete","50.00","0.0",input));
		}
		input = new JFXTextField();
		input.setId("input20");
		input.textProperty().addListener(calculateImport(input,5));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno20().divide(new BigDecimal(20)))));
			listDeno.add(new TbCorteCajaDTO("Billete","20.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno20())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Billete","20.00","0.0",input));
		}
		input = new JFXTextField();
		input.setId("input10");
		input.textProperty().addListener(calculateImport(input,6));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno10().divide(new BigDecimal(10)))));
			listDeno.add(new TbCorteCajaDTO("Moneda","10.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno10())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Moneda","10.00","0.0",input));
		}
		input = new JFXTextField();
		input.setId("input5");
		input.textProperty().addListener(calculateImport(input,7));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno5().divide(new BigDecimal(5)))));
			listDeno.add(new TbCorteCajaDTO("Moneda","5.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno5())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Moneda","5.00","0.0",input));
		}
		
		input = new JFXTextField();
		input.setId("input2");
		input.textProperty().addListener(calculateImport(input,8));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno2().divide(new BigDecimal(2)))));
			listDeno.add(new TbCorteCajaDTO("Moneda","2.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno2())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Moneda","2.00","0.0",input));
		}
		
		input = new JFXTextField();
		input.setId("input1");
		input.textProperty().addListener(calculateImport(input,9));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno1().divide(new BigDecimal(1)))));
			listDeno.add(new TbCorteCajaDTO("Moneda","1.00",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno1())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Moneda","1.00","0.0",input));
		}
		
		input = new JFXTextField();
		input.setId("input050");
		input.textProperty().addListener(calculateImport(input,10));
		if(corteCaja!=null){
			input.setText(GeneralMethods.formatCurrentInteger(String.valueOf(corteCaja.getDeno050().divide(new BigDecimal(0.5)))));
			listDeno.add(new TbCorteCajaDTO("Moneda","0.50",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno050())),input));
		}else{
			listDeno.add(new TbCorteCajaDTO("Moneda","0.50","0.0",input));
		}
		
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
	private EventHandler<MouseEvent> closeWindow(Stage stage) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(stage!=null) 
					stage.close();
			}
		};


	}
	
	@FXML
	private void openGasto(){
		
		try {
			
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/TreeProduct.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mx/com/bitmaking/application/view/FormGasto.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			Parent sceneEdit= fxmlLoader.load();
			Scene scene = new Scene(sceneEdit,3013,165);
			scene.getStylesheets().add(getClass().getResource("/mx/com/bitmaking/application/assets/css/application.css").toExternalForm());
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Abono / Cargo");
			stage.setMinHeight(500.0);
			stage.setMinWidth(350.0);
			stage.setMaxHeight(500.0);
			stage.setMaxWidth(200.0);
			
			stage.initModality(Modality.APPLICATION_MODAL); 
			stage.show();
			GastoController ctrl = fxmlLoader.getController(); //Obtiene controller de la nueva ventana
			ctrl.getBtnCancel().addEventHandler(MouseEvent.MOUSE_CLICKED, closeWindow(stage));
			ctrl.getBtnAccept().addEventHandler(MouseEvent.MOUSE_CLICKED, saveGasto(stage,ctrl));
			
			
	    } catch(Exception ex) {
			ex.printStackTrace();
			GeneralMethods.modalMsg("ERROR", "", ex.getMessage());
		}
	}
	
	private EventHandler<MouseEvent> saveGasto(Stage stage,GastoController ctrl){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(stage!=null) 
					stage.close();
				Store_cargo_abono cargoObj = new Store_cargo_abono();
				cargoObj.setFecha(new Date());
				cargoObj.setMonto(new BigDecimal(ctrl.getInputMonto().getText()));
				cargoObj.setMotivo(ctrl.getInputConcepto().getText());
				cargoObj.setId_sucursal(instance.getId_sucursal());
				if("Cargo".equals(ctrl.getCbxTipoGasto().getValue())){
					cargoObj.setTipo("C");
				}
				else if("Abono".equals(ctrl.getCbxTipoGasto().getValue())){
					cargoObj.setTipo("A");
				}
				
				if(!Flags.remote_valid){
					storeCargoAbonoService.saveCargoAbono(cargoObj);
				}else{
					remoteStoreCargoAbonoService.saveCargoAbono(cargoObj);
				}
				fillTableResume();
			}
		};
		
	}
}
