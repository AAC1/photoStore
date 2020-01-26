package mx.com.bitmaking.application.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.iservice.IStorePedidoService;
import mx.com.bitmaking.application.util.HibernateUtil;

@Service
public class StorePedidoService implements IStorePedidoService{
	
	//@Autowired
	//private EntityManagerFactory  entityManagerFactory;
	
	@Override
	public List<Store_pedido> consultPedido(String qry) {
		Session session = null;
		List<Store_pedido> resp = new ArrayList<>();
		try {
		//	session = HibernateUtil.getSessionFactory().openSession();
		//	resp=session.createQuery(qry).list();
		}
		catch(Exception e ) {
			
		}finally {
			if(session!=null) session.close();
		}
		return resp;
	}

}
