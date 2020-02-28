package mx.com.bitmaking.application.abstractdao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;

public abstract class AbstractStoreProdPedidoDAO implements IStoreProdPedidoDAO{
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
*/	
	public abstract SessionFactory getSessionfActory();
	
	@Override
	public void save(Store_prod_pedido producto) {
		getSessionfActory().getCurrentSession().save(producto);
	}



}
