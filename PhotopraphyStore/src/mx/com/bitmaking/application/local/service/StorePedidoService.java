package mx.com.bitmaking.application.local.service;

import java.io.FileInputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.idao.IClteProdCostDAO;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.service.IStorePedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.GeneralMethods;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@Service("StorePedidoService")
public class StorePedidoService implements IStorePedidoService {
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("ClteProdCostDAO")
	private IClteProdCostDAO clteProdCostoDao;
	
	@Autowired
	@Qualifier("PedidoDAO")
	private IPedidoDAO pedidoDao;
	
//	@Autowired
//	private IStoreProdPedidoDAO pedidoRepo;
	private boolean export =false;
	
	
	@Transactional(value="transactionManager")
	@Override
	public List<PedidosReporteDTO> consultPedido(String qry) {
		List<PedidosReporteDTO> resp = new ArrayList<>();
		try {
			resp = clteProdCostoDao.consultaPedido(qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@Transactional(value="transactionManager")
	@Override
	public boolean generaXLS(FileInputStream fileInputStream, String qry, String titulo,
				String pathReport,String pathParent) throws JRException{
		export=false;
		Session session = sessionFactory.getCurrentSession();
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
			// exporterXLS.setExporterInput(new SimpleExporterInput(reportFile));
			// exporterXLS.setExporterOutput(new
			// SimpleOutputStreamExporterOutput(Constantes.PATH_XLS));

			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, pathReport);

			exporterXLS.exportReport();
		
			export = true;
		} catch (JRException ex) {
			throw new JRException(ex.getMessage(),ex);
		}
		return export;
	}
	
	@Transactional(value="transactionManager")
	@Override
	public String getCurrentNumberFolio(String pref) {
	
		SimpleDateFormat sd = new SimpleDateFormat("yyMMdd");
		String prefijo =pref+"-"+sd.format(new Date());
		System.out.println("Prefijo:"+prefijo);
		int number = pedidoDao.getCurrentNumberFolio(prefijo);
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
	@Transactional(value="transactionManager")
	public boolean guardaPedido(Store_pedido pedido) {
		boolean resp=false;
		try {
			pedidoDao.save(pedido);
			resp=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
	
	@Transactional(value="transactionManager")
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
		
		pedidoDao.update(pedidoEntity);
	}

}
