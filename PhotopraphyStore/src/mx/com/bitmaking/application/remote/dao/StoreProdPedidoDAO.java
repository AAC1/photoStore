package mx.com.bitmaking.application.remote.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreProdPedidoDAO;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;

@Repository("remoteStoreProdPedidoDAO")
public class StoreProdPedidoDAO extends AbstractStoreProdPedidoDAO{// implements IStoreProdPedidoDAO{
	
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;

	@Transactional("remoteTransactionManager")
	public void save(Store_prod_pedido producto) {
		super.save(producto);
	}
	@Transactional("remoteTransactionManager")
	public List<Store_prod_pedido> getListProdPedidos(String pedidos) {
		return super.getListProdPedidos(pedidos);
	}
	@Transactional("remoteTransactionManager")
	public void deleteByIdPedido(int idPedido){
		super.deleteByIdPedido(idPedido);
	}
	
	@Transactional("remoteTransactionManager")
	public void editProd(Store_prod_pedido obj){
		super.editProd(obj);
	}
	@Override
	public SessionFactory getSessionfActory() {
		return sessionFactory;
	}

}
