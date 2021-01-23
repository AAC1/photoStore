package mx.com.bitmaking.application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.EmailSender;
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
	
	@Autowired
	@Qualifier("StorePedidoService")
	private IStorePedidoService pedidoService;
	@Autowired
	@Qualifier("remoteStorePedidoService")
	private IStorePedidoService remotePedidoService;
	
	@Autowired
	private Environment env;
	
	private UserSessionDTO instance = null;
	private Stage stageModalMontoIni = null;
	private Store_corte_caja corteCaja = null;
	BigDecimal cargo = new BigDecimal(0);
	BigDecimal abono = new BigDecimal(0);
	
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
	   		PseudoClass yellowColor = PseudoClass.getPseudoClass("yellow-text");
	   		
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
		                    	if(row.getDescripcion().contains("(-)") || row.getTotal().contains("-")){
			                    	
			        					pseudoClassStateChanged(greenColor,false);
			        					pseudoClassStateChanged(redColor,true);
			        					pseudoClassStateChanged(blackColor,false);
			                    		pseudoClassStateChanged(yellowColor,false);
			        				
		                    	}else if(row.getDescripcion().contains("(+)")){
		                    	
		                    		pseudoClassStateChanged(blackColor,false);
		                    		pseudoClassStateChanged(redColor,false);
		                    		pseudoClassStateChanged(greenColor,true);
		                    		pseudoClassStateChanged(yellowColor,false);
		                    	}else if(row.isGanancia()){
		                    		pseudoClassStateChanged(blackColor,false);
		                    		pseudoClassStateChanged(redColor,false);
		                    		pseudoClassStateChanged(greenColor,false);
		                    		pseudoClassStateChanged(yellowColor,true);
		                    	}else {
		                    		pseudoClassStateChanged(yellowColor,false);
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
					.add(corteCaja.getDeno20()).add(corteCaja.getDeno50()).add(corteCaja.getDeno100()).add(corteCaja.getDeno200())
					.add(corteCaja.getDeno500().add(corteCaja.getDeno1000()));
			corteCaja.setImporte(importe);
			

			BigDecimal totalPedidos = new BigDecimal(getCurrentOrders());
			corteCaja.setTotalPedidos(totalPedidos);
			
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
	
	@FXML 
	private void sendMail(){
		fillTableResume();
		if(corteCaja ==null){
			GeneralMethods.modalMsg("", "", "No se ha encontrado registro del día de hoy");
			return;
		}

		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		
		String passwd = GeneralMethods.desencriptar(env.getProperty("mail.password"), Constantes.SALT);
		String emailUser = GeneralMethods.desencriptar(env.getProperty("mail.user"), Constantes.SALT);
		
		EmailSender mailObj = new EmailSender(env.getProperty("mail.host"),env.getProperty("mail.port"),
				emailUser,passwd);
		
		StringBuilder msgHtml = new StringBuilder();
		BigDecimal total = new BigDecimal(0);
		BigDecimal sum = new BigDecimal(0);
		BigDecimal rest = new BigDecimal(0);
		BigDecimal totalEsp = new BigDecimal(0);
		String totalPed = getCurrentOrders();
		BigDecimal totalPedidos = new BigDecimal(totalPed);
		
		//Guarda de nuevo ek monto de pedidos para que se esté actualizado 
		saveMontoPedidoIni(totalPed);
		
		//Suma todo lo que no sea gasto en el dia
		sum = sum.add(totalPedidos);
		sum = sum.add(abono);
		
		//rest = rest.add(corteCaja.getImporte_ini()); 
		rest = rest.add(cargo); 
		total = sum.subtract(rest);
		
		totalEsp = totalEsp.add(corteCaja.getImporte());
		totalEsp = totalEsp.subtract(total);
		
		msgHtml.append("<table><thead><tr><td colspan=2 style='text-align:center;background-color:#505050;color:white;'>");
		msgHtml.append("<h3 syle='text-align:center;padding:0;margin:0'>Corte de Caja "+dt.format(new Date()));
		msgHtml.append("</h3></td></tr></thead><tbody >");
		/*
		msgHtml.append("<tr><td style='width:40%;text-align:right;height:30px'><strong>Caja Inicial:</strong></td>");
		msgHtml.append("<td style='min-width:40px;text-align:right'>");
		msgHtml.append(corteCaja.getImporte_ini());
		msgHtml.append("</td></tr>");
		*/
		msgHtml.append("<tr><td style='width:40%;text-align:right;height:30px'><strong>Cargos:</strong></td>");
		msgHtml.append("<td style='min-width:40px;text-align:right'>");
		msgHtml.append(GeneralMethods.formatCurrentNumber(String.valueOf(cargo)));
		msgHtml.append("</td></tr>");
		msgHtml.append("<tr><td style='width:40%;text-align:right;height:30px'><strong>Abonos:</strong></td>");
		msgHtml.append("<td style='min-width:40px;text-align:right'>");
		msgHtml.append(GeneralMethods.formatCurrentNumber(String.valueOf(abono)));
		msgHtml.append("</td></tr>");
		msgHtml.append("<tr><td style='width:40%;text-align:right;height:30px'><strong>Monto de Pedidos:</strong></td>");
		msgHtml.append("<td style='min-width:50px;text-align:right'>");
		msgHtml.append(GeneralMethods.formatCurrentNumber(String.valueOf(totalPedidos)));
		msgHtml.append("</td></tr>");
		msgHtml.append("<tr><td style='width:40%;text-align:right;height:30px'><strong>Monto Esperado:</strong></td>");
		msgHtml.append("<td style='min-width:40px;text-align:right'>");
		if(sum.compareTo(rest) <0){  //SUM es menor a REST
			msgHtml.append("-"+GeneralMethods.formatCurrentNumber(String.valueOf(total)));
		}else{
			msgHtml.append(GeneralMethods.formatCurrentNumber(String.valueOf(total)));
		}
		msgHtml.append("</td></tr>");
		msgHtml.append("<tr><td style='width:40%;text-align:right;height:30px'><strong>Denominaciones:</strong></td>");
		msgHtml.append("<td style='min-width:50px;text-align:right'>");
		msgHtml.append(GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getImporte())));
		msgHtml.append("</td></tr>");
		
		
		msgHtml.append("<tr><td style='width:40%;text-align:right;height:30px'><strong>Diferencia:</strong></td>");
		msgHtml.append("<td style='min-width:50px;text-align:right'>");
		if(corteCaja.getImporte().compareTo(total) <0){  //Total_Deno  es menor a totalesperado
			msgHtml.append("-"+GeneralMethods.formatCurrentNumber(String.valueOf(totalEsp)));
		}else{
			msgHtml.append(GeneralMethods.formatCurrentNumber(String.valueOf(totalEsp)));
		}
		msgHtml.append("</td></tr>");
		msgHtml.append("</tbody></table>");
		
		String filename = "";
		SimpleDateFormat formatoD = new SimpleDateFormat("ddMMyyyy_hhmmss");
		String pathReport=env.getProperty("exportFile.path")+"/corteCaja_"+formatoD.format(new Date())+".xls";
		System.out.println(msgHtml);
		try {
			if(exportXLS(pathReport)){
				filename = pathReport;
			}
				mailObj.sendMessageHTML(env.getProperty("mail.userTo"), msgHtml.toString(), 
					"Corte de Caja",filename, "Corte de caja.xls");
		} catch (AddressException e) {
			GeneralMethods.modalMsg("", "", e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			GeneralMethods.modalMsg("", "", e.getMessage());
			e.printStackTrace();
		}
	}
	
	private boolean exportXLS(String pathReport)  {
		boolean export = false;
		File file=null;
		FileInputStream fileInputStream = null;
		
		try {
			file = new File(env.getProperty("exportFile.pathCorteCajaRepJasper"));//loader.getFile());
			System.out.println("ABS_PATH: "+file.getAbsolutePath());
			System.out.println("PARENT: "+file.getParent());
			System.out.println("JUST_PATH: "+file.getPath());
			//String pathPlantilla = file.getAbsolutePath();
			
			//File fileToDownload = new File(pathPlantilla);
			
			SimpleDateFormat formatoSQL = new SimpleDateFormat("yyyy-MM-dd");
		
			if (file.exists() && file.isFile()) {
				fileInputStream = new FileInputStream(file);
			} else {
				GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar plantilla de reporte. se enviara sin archivo");
				return false;
			}
			
			Map<String, Object> parametrosReporte = new HashMap<>();
			parametrosReporte.put("idSucursal", String.valueOf(instance.getId_sucursal()));
			parametrosReporte.put("titulo", Constantes.COMPANY_NAME);
			parametrosReporte.put("fecha", formatoSQL.format(new Date()));
			//parametrosReporte.put("SUBREPORT_DIR", file.getParent()+"/");
			
			
			export = (Flags.remote_valid)?
					remotePedidoService.generaXLS(fileInputStream,parametrosReporte,pathReport,file.getParent()+"/"):
					pedidoService.generaXLS(fileInputStream,parametrosReporte,pathReport,file.getParent()+"/");
			
			if(!export) {
				GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error al generar reporte");
			}
		} /*catch(MalformedURLException e){
			GeneralMethods.modalMsg("ERROR", "", "No fue posible encontrar la plantilla del reporte");
			e.printStackTrace();
		}*/
		catch (Exception e) {
			GeneralMethods.modalMsg("ERROR", "", "Ha ocurrido un error al generar reporte");
			e.printStackTrace();
		}finally{
			if(fileInputStream!=null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return export;
	}
	public String getCurrentOrders(){
		BigDecimal montoTotal = new BigDecimal("0.0");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		
		String prefijo=instance.getPrefijo();//env.getProperty("macrofoto.prefijo");
		String fecha = sdf.format(new Date());
		System.out.println("prefijo:"+prefijo);
		List<BigDecimal> lstPedidos = (Flags.remote_valid)?remotePedidoService.totalPedidosByFec(fecha,prefijo):
																	pedidoService.totalPedidosByFec(fecha,prefijo);
		System.out.println("lstPedidos:"+lstPedidos.size());
		if (lstPedidos != null && lstPedidos.size() > 0) {
			
			for(BigDecimal monto : lstPedidos ) {
				System.out.println("lstPedidos_Value:"+String.valueOf(monto));
				montoTotal = montoTotal.add(monto);
			}
		}
		
		return String.valueOf(montoTotal);
	
	}
	private void fillTableResume(){
		
		List<CorteCajaResumeDTO> listResume = new ArrayList<>();
		List<Store_cargo_abono> listCargoAbono = null;
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		BigDecimal totalEsp = new BigDecimal(0);
		
		cargo = new BigDecimal(0);
		abono = new BigDecimal(0);
		
		String totalPedidos = getCurrentOrders();
		
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
			/*
			listResume.add(new CorteCajaResumeDTO("    Monto inicial",GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getImporte_ini())),
					GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getImporte_ini()))));
			
			
			
			listResume.add(new CorteCajaResumeDTO("    Pedidos",GeneralMethods.formatCurrentNumber(totalPedidos),
					GeneralMethods.formatCurrentNumber(totalPedidos)));
			*/
			listResume.add(new CorteCajaResumeDTO("CARGOS","","",false));
			
			if(listCargoAbono != null && listCargoAbono.size()>0){
				
				List<Store_cargo_abono> lstCargo= (listCargoAbono.stream().
						filter(e -> "C".contentEquals(e.getTipo()))).collect((Collectors.toList()));

				for(int i =0; i<lstCargo.size(); i++){
					if("C".equals(lstCargo.get(i).getTipo())){//Valida que sea cargo
						cargo = cargo.add(lstCargo.get(i).getMonto());
						if((i+1) == lstCargo.size())
							listResume.add(new CorteCajaResumeDTO("(-) "+lstCargo.get(i).getMotivo(),
										GeneralMethods.formatCurrentNumber(String.valueOf(lstCargo.get(i).getMonto())),
										GeneralMethods.formatCurrentNumber(String.valueOf(cargo)),false));
						else
							listResume.add(new CorteCajaResumeDTO("(-) "+lstCargo.get(i).getMotivo(),
									GeneralMethods.formatCurrentNumber(String.valueOf(lstCargo.get(i).getMonto())),"",false));
					}
					
				}
				
				
			}else{
				listResume.add(new CorteCajaResumeDTO("--","0.00","0.00",false));
			}
			boolean hasAbono = false;
			listResume.add(new CorteCajaResumeDTO("ABONOS","","",false));
			if(listCargoAbono != null && listCargoAbono.size()>0){
				List<Store_cargo_abono> lstAbono= (listCargoAbono.stream().
										filter(e -> "A".contentEquals(e.getTipo()))).collect((Collectors.toList()));
				
				for(int i =0; i<lstAbono.size(); i++){
					if("A".equals(lstAbono.get(i).getTipo())){//Valida que sea abono
						hasAbono=true;
						abono = abono.add(lstAbono.get(i).getMonto());
						if((i+1) == lstAbono.size())
							listResume.add(new CorteCajaResumeDTO("(+) "+lstAbono.get(i).getMotivo(),
										GeneralMethods.formatCurrentNumber(String.valueOf(lstAbono.get(i).getMonto())),
										GeneralMethods.formatCurrentNumber(String.valueOf(abono)),false));
						else
							listResume.add(new CorteCajaResumeDTO("(+) "+lstAbono.get(i).getMotivo(),
									GeneralMethods.formatCurrentNumber(String.valueOf(lstAbono.get(i).getMonto())),"",false));
					}
					
				}
			}
			if(!hasAbono){
				
				listResume.add(new CorteCajaResumeDTO("--","0.00","0.00",false));
			}
			
			listResume.add(new CorteCajaResumeDTO("Monto de Pedidos",GeneralMethods.formatCurrentNumber(String.valueOf(totalPedidos)),
									GeneralMethods.formatCurrentNumber(String.valueOf(totalPedidos)),false));
			
			BigDecimal total = new BigDecimal(0);
			BigDecimal sum = new BigDecimal(0);
			BigDecimal rest = new BigDecimal(0);
			
			//Suma todos los ingresos
			sum = sum.add(new BigDecimal(totalPedidos)); 
			sum = sum.add(abono); 
			
			//Suma todo lo que no sea ingreso del dia
			//rest = rest.add(corteCaja.getImporte_ini()); 
			rest = rest.add(cargo); 
			
			total = sum.subtract(rest);
			if(sum.compareTo(rest) <0){  //SUM es menor a REST
				listResume.add(new CorteCajaResumeDTO("Monto Esperado","","-"+GeneralMethods.formatCurrentNumber(String.valueOf(total)),false));
			}else{
				listResume.add(new CorteCajaResumeDTO("Monto Esperado","",GeneralMethods.formatCurrentNumber(String.valueOf(total)),false));
			}
			

			listResume.add(new CorteCajaResumeDTO("","","",false));//linea vacia
			listResume.add(new CorteCajaResumeDTO("DENOMINACIONES","","",false));//\u00D3
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 1000",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno1000())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 500",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno500())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 200",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno200())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 100",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno100())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 50",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno50())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 20",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno20())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 10",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno10())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 5",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno5())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 2",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno2())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 1",
						GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno1())),"",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 0.50",
					GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getDeno050())),
					GeneralMethods.formatCurrentNumber(String.valueOf(corteCaja.getImporte())),false));

			
			
			totalEsp = totalEsp.add(corteCaja.getImporte());
			totalEsp = totalEsp.subtract(total);
			
			if(corteCaja.getImporte().compareTo(total) <0){  //Total_Deno es menor a total esperado
				listResume.add(new CorteCajaResumeDTO("Diferencia","","-"+GeneralMethods.formatCurrentNumber(String.valueOf(totalEsp)),false));
			}else{
				listResume.add(new CorteCajaResumeDTO("Diferencia","",GeneralMethods.formatCurrentNumber(String.valueOf(totalEsp)),true));
			}
		//	listResume.add(new CorteCajaResumeDTO("Diferencia","",GeneralMethods.formatCurrentNumber(String.valueOf(totalEsp))));
			
			tblResume.getItems().removeAll(tblResume.getItems());
			tblResume.getItems().addAll(listResume);
		}else{
			
			totalEsp = totalEsp.subtract(new BigDecimal(totalPedidos));
			
			
		//	listResume.add(new CorteCajaResumeDTO("    Monto inicial","0.00","0.00"));
		/*
			listResume.add(new CorteCajaResumeDTO("    Pedidos",GeneralMethods.formatCurrentNumber(totalPedidos),
					GeneralMethods.formatCurrentNumber(totalPedidos)));
		*/
			listResume.add(new CorteCajaResumeDTO("GASTOS","","",false));
			listResume.add(new CorteCajaResumeDTO("(-) CARGO","0.00","0.00",false));

			listResume.add(new CorteCajaResumeDTO("DEVOLUCIONES","","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Abono","0.00","0.00",false));
			
			listResume.add(new CorteCajaResumeDTO("Monto de Pedidos",GeneralMethods.formatCurrentNumber(totalPedidos),GeneralMethods.formatCurrentNumber(totalPedidos),false));
			
			if(new BigDecimal(0).compareTo(new BigDecimal(totalPedidos)) <0){  //Total es menor a total de pedidos
				listResume.add(new CorteCajaResumeDTO("Monto Esperado","","-"+GeneralMethods.formatCurrentNumber(String.valueOf(totalPedidos)),false));
			}else{
				listResume.add(new CorteCajaResumeDTO("Monto Esperado","",GeneralMethods.formatCurrentNumber(String.valueOf(totalPedidos)),true));
			}
			
		//	listResume.add(new CorteCajaResumeDTO("Monto Actual","","0.0",false));

			listResume.add(new CorteCajaResumeDTO("","","",false));
			listResume.add(new CorteCajaResumeDTO("DENOMINACIONES","","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 1000","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 500","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 200","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 100","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 50","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Billetes de 20","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 10","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 5","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 2","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 1","0.00","",false));
			listResume.add(new CorteCajaResumeDTO("(+) Monedas de 0.50","0.00","0.0",false));
	
			if(new BigDecimal(0).compareTo(new BigDecimal(totalPedidos)) <0){  //Total es menor a total de pedidos
				listResume.add(new CorteCajaResumeDTO("Diferencia","","-"+GeneralMethods.formatCurrentNumber(String.valueOf(totalEsp)),false));
			}else{
				listResume.add(new CorteCajaResumeDTO("Diferencia","",GeneralMethods.formatCurrentNumber(String.valueOf(totalEsp)),true));
			}
		//	listResume.add(new CorteCajaResumeDTO("Diferencia ","",GeneralMethods.formatCurrentNumber(String.valueOf(totalEsp))));
			
			tblResume.getItems().removeAll(tblResume.getItems());
			tblResume.getItems().addAll(listResume);
			
			saveMontoPedidoIni(totalPedidos);
			/*
			ModalConfirmController ctrl = openModalConfirm();
			if(ctrl==null)return;
			
			ctrl.getBtnCancelar().addEventHandler(MouseEvent.MOUSE_CLICKED, cancelMontoIni(stageModalMontoIni));
			ctrl.getLblMsg().setText("No se tiene registrado el monto inicial de la caja. Favor de ingresarla");
			ctrl.getLblInput().setText("Monto inicial: ");
			ctrl.getFormInput().setVisible(true);
			ctrl.getBtnConfirm().addEventHandler(MouseEvent.MOUSE_CLICKED,acceptMontoIni(stageModalMontoIni,ctrl));
			*/
		}
		
		
		
	}
	
	private void saveMontoPedidoIni(String totalPedidos) {
		
		Store_corte_caja objEntity = new Store_corte_caja();
		
		if(corteCaja == null){
			objEntity.setImporte_ini(new BigDecimal(0));
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
			objEntity = corteCaja;
			objEntity.setId_corte_caja(corteCaja.getId_corte_caja());
			objEntity.setFecha(corteCaja.getFecha());
			objEntity.setId_sucursal(corteCaja.getId_sucursal());
			
		}
		objEntity.setTotalPedidos(new BigDecimal(totalPedidos));
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		
		if(!Flags.remote_valid){
			
			corteCajaService.saveCorteCaja(objEntity);
			corteCaja = corteCajaService.getCorteCajaByDate(dt.format(new Date()), instance.getId_sucursal());
		}else{
			remoteCorteCajaService.saveCorteCaja(objEntity);
			corteCaja = remoteCorteCajaService.getCorteCajaByDate(dt.format(new Date()), instance.getId_sucursal());
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
						System.out.println(value);
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
			stageModalMontoIni.setMinWidth(500.0);
			stageModalMontoIni.setMaxHeight(200.0);
			stageModalMontoIni.setMaxWidth(500.0);
			stageModalMontoIni.initModality(Modality.WINDOW_MODAL); 
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
			stage.setMaxWidth(350.0);
			
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
