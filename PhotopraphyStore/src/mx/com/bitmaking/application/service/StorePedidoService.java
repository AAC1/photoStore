package mx.com.bitmaking.application.service;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.repository.IClteProdCostDAO;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.GeneralMethods;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class StorePedidoService implements IStorePedidoService {
	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	private IClteProdCostDAO clteProdCostoDao;
	
	private boolean export =false;
	
	
	@Transactional
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
	
	@Transactional
	@Override
	public boolean generaXLS(FileInputStream fileInputStream, String qry, String titulo,String pathReport) throws JRException{
		export=false;
		Session session = sessionFactory.getCurrentSession();
		session.doWork(connection -> {
			
				try {
					exportXLS(connection, fileInputStream, qry, titulo,pathReport);
					export=true;
				} catch (JRException e) {
					e.printStackTrace();
				}
			
			
		});
		
		return export;
	}

	private boolean exportXLS(Connection connection, FileInputStream fileInputStream, 
								String qry, String titulo,String pathReport) throws JRException {
		boolean export = false;
		JasperReport pdfResolucionInforme;
		try {
			// Instanciamos el objeto para crear el PDF
			pdfResolucionInforme = (JasperReport) JRLoader.loadObject(fileInputStream);

			// Preparamos los valores que se van a escribir en el reporte
			Map<String, Object> parametrosReporte = new HashMap<>();
			parametrosReporte.put("qry", qry);
			parametrosReporte.put("titulo", titulo);

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

}
