package mx.com.bitmaking.application.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.repository.IClteProdCostDAO;
import mx.com.bitmaking.application.util.HibernateUtil;

@Service
public class StorePedidoService implements IStorePedidoService{
	
	@Autowired
	private IClteProdCostDAO  clteProdCostoDao;
	
	@Transactional
	@Override
	public List<Store_pedido> consultPedido(String qry) {
		List<Store_pedido> resp = new ArrayList<>();
		try {
			resp = clteProdCostoDao.consultaPedido(qry);
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		return resp;
	}

}
