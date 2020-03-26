package mx.com.bitmaking.application.local.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreProdPedidoDAO;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;

@Repository("StoreProdPedidoDAO")
public class StoreProdPedidoDAO extends AbstractStoreProdPedidoDAO{// implements IStoreProdPedidoDAO{
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	@Transactional("transactionManager")
	public void save(Store_prod_pedido producto) {
		super.save(producto);
	}
	@Transactional("transactionManager")
	public List<Store_prod_pedido> getListProdPedidos(String pedidos) {
		return super.getListProdPedidos(pedidos);
	}
	@Transactional("transactionManager")
	public void deleteByIdPedido(int idPedido){
		super.deleteByIdPedido(idPedido);
	}
	@Override
	public SessionFactory getSessionfActory() {
		return sessionFactory;
	}



}
