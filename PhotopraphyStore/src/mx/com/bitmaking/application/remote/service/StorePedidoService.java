package mx.com.bitmaking.application.remote.service;

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

import mx.com.bitmaking.application.abstractservice.AbstractStorePedidoService;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.idao.IClteProdCostDAO;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.GeneralMethods;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@Service("remoteStorePedidoService")
public class StorePedidoService extends AbstractStorePedidoService{//implements IStorePedidoService {
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("remoteClteProdCostDAO")
	private IClteProdCostDAO clteProdCostoDao;
	
	@Autowired
	@Qualifier("remotePedidoDAO")
	private IPedidoDAO pedidoDao;
	
	
	@Transactional(value = "remoteTransactionManager")
	public List<PedidosReporteDTO> consultPedido(String qry) {
		return super.consultPedido(qry);
	}
	
	@Transactional(value = "remoteTransactionManager")
	public boolean generaXLS(FileInputStream fileInputStream,  Map<String, Object> parametrosReporte,
				String pathReport,String pathParent) throws JRException{
		return super.generaXLS(fileInputStream, parametrosReporte, pathReport, pathParent);
	}

	@Transactional(value = "remoteTransactionManager")
	public String getCurrentNumberFolio(String pref) {
		return super.getCurrentNumberFolio(pref);
	}

	
	@Transactional(value = "remoteTransactionManager")
	public boolean guardaPedido(Store_pedido pedido) {
		return super.guardaPedido(pedido);
	}
	
	@Transactional(value = "remoteTransactionManager")
	public  void editPedido(PedidosReporteDTO in) {
		super.editPedido(in);
	}

	@Override
	public IClteProdCostDAO getClteProdCostoDao() {
		return clteProdCostoDao;
	}

	@Override
	public IPedidoDAO getPedidoDao() {
		return pedidoDao;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
