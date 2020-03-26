package mx.com.bitmaking.application.remote.service;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreProdPedidoService;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.dto.ProdPedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.iservice.IStoreProdPedidoService;

@Service("remoteStoreProdPedidoService")
public class StoreProdPedidoService extends AbstractStoreProdPedidoService{//implements IStoreProdPedidoService{
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("remoteStoreProdPedidoDAO")
	private IStoreProdPedidoDAO prodPedidoRepo;
	
	@Autowired
	@Qualifier("remotePedidoDAO")
	private IPedidoDAO pedidoRepo;
	
	@Transactional(value = "remoteTransactionManager")
	public List<Store_prod_pedido> getListProdPedidos(String pedidos) {
		return super.getListProdPedidos(pedidos);
	}
	
	@Transactional(value = "remoteTransactionManager")
	public boolean guardaProdsByPedido(String folio, Store_prod_pedido producto) {
		return super.guardaProdsByPedido(folio, producto);
	}
	@Transactional(value="remoteTransactionManager")
	public void deleteByIdPedido(int idPedido){
		super.deleteByIdPedido(idPedido);
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
