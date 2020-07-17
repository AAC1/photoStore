package mx.com.bitmaking.application.local.service;

import java.util.ArrayList;
import java.util.List;

//import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreProdPedidoService;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.iservice.IStoreProdPedidoService;

@Service("StoreProdPedidoService")
public class StoreProdPedidoService extends AbstractStoreProdPedidoService{//implements IStoreProdPedidoService{
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("StoreProdPedidoDAO")
	private IStoreProdPedidoDAO prodPedidoRepo;
	
	@Autowired
	@Qualifier("PedidoDAO")
	private IPedidoDAO pedidoRepo;
	
	@Transactional(value="transactionManager")
	public List<Store_prod_pedido> getListProdPedidos(String pedidos) {
		return super.getListProdPedidos(pedidos);
	}

	@Transactional(value="transactionManager")
	public boolean guardaProdsByPedido(String folio, Store_prod_pedido producto) {
		return super.guardaProdsByPedido(folio, producto);
	}
	@Transactional(value="transactionManager")
	public void deleteByFolio(String folio){
		super.deleteByFolio(folio);
	}
	@Transactional(value="transactionManager")
	public void editProd(Store_prod_pedido obj){
		super.editProd(obj);
	}
	@Override
	public IStoreProdPedidoDAO getProdPedidoRepo() {
		return prodPedidoRepo;
	}

	@Override
	public IPedidoDAO getPedidoDao() {
		return pedidoRepo;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
