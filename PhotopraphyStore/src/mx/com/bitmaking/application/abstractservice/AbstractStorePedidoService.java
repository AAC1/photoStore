package mx.com.bitmaking.application.abstractservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.idao.IClteProdCostDAO;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.GeneralMethods;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

//@Service("StorePedidoService")
public abstract class AbstractStorePedidoService implements IStorePedidoService {
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("ClteProdCostDAO")
	private IClteProdCostDAO clteProdCostoDao;
	
	@Autowired
	@Qualifier("PedidoDAO")
	private IPedidoDAO pedidoDao;
	*/
	private boolean export =false;
	
	
	public abstract IClteProdCostDAO getClteProdCostoDao();
	public abstract IPedidoDAO getPedidoDao();
	public abstract SessionFactory getSessionFactory();
	
	@Override
	public List<PedidosReporteDTO> consultPedido(String qry) {
		
		List<PedidosReporteDTO> resp = new ArrayList<>();
		try {
			resp = getClteProdCostoDao().consultaPedido(qry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@Override
	public boolean generaXLS(FileInputStream fileInputStream, String qry, String titulo,
				String pathReport,String pathParent) throws JRException{
		export=false;
		Session session = getSessionFactory().getCurrentSession();
		session.doWork(connection -> {
			
				try {
					exportXLS(connection, fileInputStream, qry, titulo,pathReport,pathParent);
					export=true;
				} catch (JRException e) {
					e.printStackTrace();
				}
			
			
		});
		
		return export;
	}

	private boolean exportXLS(Connection connection, FileInputStream fileInputStream, 
								String qry, String titulo,String pathReport,String pathParent) throws JRException {
		boolean export = false;
		JasperReport pdfResolucionInforme;
		try {
			// Instanciamos el objeto para crear el PDF
			pdfResolucionInforme = (JasperReport) JRLoader.loadObject(fileInputStream);
			
			// Preparamos los valores que se van a escribir en el reporte
			Map<String, Object> parametrosReporte = new HashMap<>();
			parametrosReporte.put("qry", qry);
			parametrosReporte.put("titulo", titulo);
			parametrosReporte.put("SUBREPORT_DIR", pathParent);
			
			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager.fillReport(pdfResolucionInforme, parametrosReporte, connection);
			JRXlsExporter exporterXLS = new JRXlsExporter();
			/*
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			printRequestAttributeSet.add(new Copies(1));
			PrinterJob pJ = setFormat("XLS");
			System.out.println("PrinterName:"+pJ.getPrinter().getName());
			PrinterName printerName = new PrinterName(pJ.getPrinter().getName(), null);
			PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
			printServiceAttributeSet.add(printerName);
			
			JRPrintServiceExporter exporterXLS = new JRPrintServiceExporter();
			*/
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, pathReport);
		//	exporterXLS.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
		//	exporterXLS.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
		//	exporterXLS.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
		  //  exporterXLS.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
		  
			exporterXLS.exportReport();
			
			
			export = true;
		} catch (JRException ex) {
			throw new JRException(ex.getMessage(),ex);
		}
		return export;
	}
	
	@Override
	public String getCurrentNumberFolio(String pref) {
	
		SimpleDateFormat sd = new SimpleDateFormat("yyMMdd");
		String prefijo =pref+"-"+sd.format(new Date());
		System.out.println("Prefijo:"+prefijo);
		int number = getPedidoDao().getCurrentNumberFolio(prefijo);
		if(number<0) {
			GeneralMethods.modalMsg("ERROR", "", "No fue posible generar siguiente folio");
			return "";
		}
		String secuencia=String.valueOf(number);
		/*
		if(number <=99999) {
			secuencia +="0"+String.valueOf(number); 
		}
		 
		if(number <=9999) {
			secuencia ="0"+String.valueOf(number); 
		}*/
		if(number <=999) {
			secuencia ="0"+String.valueOf(number); 
		}
		if(number <=99) {
			secuencia ="00"+String.valueOf(number); 
		}
		if(number <=9) {
			secuencia ="000"+String.valueOf(number); 
		}
		prefijo +=secuencia; 
		return prefijo;
	}

	@Override
	public boolean guardaPedido(Store_pedido pedido) {
		boolean resp=false;
		try {
			getPedidoDao().save(pedido);
			resp=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
	
	@Override
	public  void editPedido(PedidosReporteDTO in) {
		Store_pedido pedidoEntity =new Store_pedido();
		pedidoEntity.setId_pedido(in.getId_pedido());
		pedidoEntity.setFolio(in.getFolio());
		pedidoEntity.setCliente(in.getCliente());
		pedidoEntity.setTelefono(in.getTelefono());
		pedidoEntity.setDescripcion(in.getDescripcion());
		pedidoEntity.setFec_pedido(in.getFec_pedido());
		pedidoEntity.setFec_entregado(in.getFec_entregado());
		pedidoEntity.setMonto_ant(in.getMonto_ant());
		pedidoEntity.setMonto_total(in.getMonto_total());
		pedidoEntity.setId_estatus(in.getId_estatus());
		
		getPedidoDao().update(pedidoEntity);
	}

}
