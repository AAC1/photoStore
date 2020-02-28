package mx.com.bitmaking.application.local.repository;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;

@Repository("StoreProdPedidoDAO")
public class StoreProdPedidoDAO implements IStoreProdPedidoDAO{
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	
	@Override
	public void save(Store_prod_pedido producto) {
		sessionFactory.getCurrentSession().save(producto);
	}



}
