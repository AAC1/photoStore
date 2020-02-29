package mx.com.bitmaking.application.local.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractPedidoDAO;
import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.idao.IPedidoDAO;

@Repository("PedidoDAO")
public class PedidoDAO extends AbstractPedidoDAO{//implements IPedidoDAO{
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Transactional(value="transactionManager")
	public int getCurrentNumberFolio(String prefijo) {
		return super.getCurrentNumberFolio(prefijo);
	}
	@Transactional(value="transactionManager")
	public int getIdByFolio(String folio) {
		return super.getIdByFolio(folio);
	}
	@Transactional(value="transactionManager")
	public void save(Store_pedido pedido) {
		super.save(pedido);
	}
	@Transactional(value="transactionManager")
	public void update(Store_pedido pedido) {
		super.update(pedido);
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
