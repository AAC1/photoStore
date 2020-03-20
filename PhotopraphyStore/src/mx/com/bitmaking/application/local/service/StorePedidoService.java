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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStorePedidoService;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.entity.Update_info;
import mx.com.bitmaking.application.idao.IClteProdCostDAO;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.idao.IUpdateInfoDAO;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;
import mx.com.bitmaking.application.util.GeneralMethods;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@Service("StorePedidoService")
public class StorePedidoService extends AbstractStorePedidoService{//implements IStorePedidoService {
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("ClteProdCostDAO")
	private IClteProdCostDAO clteProdCostoDao;
	
	@Autowired
	@Qualifier("PedidoDAO")
	private IPedidoDAO pedidoDao;
	
	@Autowired
	@Qualifier("UpdateInfoDAO")
	private IUpdateInfoDAO updateInfoDAO;
	
	private boolean export =false;
	
	
	@Transactional(value="transactionManager")
	public List<PedidosReporteDTO> consultPedido(String qry) {
		return super.consultPedido(qry);
	}
	
	@Transactional(value="transactionManager")
	public boolean generaXLS(FileInputStream fileInputStream, String qry, String titulo,
				String pathReport,String pathParent) throws JRException{
		return super.generaXLS(fileInputStream, qry, titulo, pathReport, pathParent);
	}
	
	@Transactional(value="transactionManager")
	public String getCurrentNumberFolio(String pref) {
		return super.getCurrentNumberFolio(pref);
	}

	
	@Transactional(value="transactionManager")
	public boolean guardaPedido(Store_pedido pedido) {
		/*
		if(!Flags.remote_valid) {//genera JSON para actualizar más adelante
			Update_info objInfo = updateInfoDAO.getInfo();
			JSONObject json = null;
			if(objInfo ==null ){
				json=new JSONObject();
				json.append("table", "Store_pedido");
			}else{
			 json=new JSONObject(new String(objInfo.getJson_data()));
			}
			System.out.println("json:"+json.toString());
		}
		*/
		return super.guardaPedido(pedido);
	}
	
	@Transactional(value="transactionManager")
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
