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

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.dto.ProdPedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.service.IStoreProdPedidoService;

@Service("remoteStoreProdPedidoService")
public class StoreProdPedidoService implements IStoreProdPedidoService{
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("remoteStoreProdPedidoDAO")
	private IStoreProdPedidoDAO prodPedidoRepo;
	//private IStoreProdPedidoRepo prodPedidoRepo;
	
	@Autowired
	@Qualifier("remotePedidoDAO")
	private IPedidoDAO pedidoRepo;
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "remoteTransactionManager")
	@Override
	public List<Store_prod_pedido> getListProdPedidos(String pedidos) {
		List<Store_prod_pedido> results =null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT p.*");
		qry.append(" FROM Store_prod_pedido p ");
		qry.append(" WHERE p.id_pedido in "+pedidos);
		
		
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			
			query.setResultTransformer(Transformers.aliasToBean(Store_prod_pedido.class));
			
			results =query.list();
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		if(results==null) {
			results = new ArrayList<>();
		}
		return results;
	}

	@Override
	@Transactional(value = "remoteTransactionManager")
	public boolean guardaProdsByPedido(String folio, Store_prod_pedido producto) {
		int id_pedido = pedidoRepo.getIdByFolio(folio);
		System.out.println("Id_pedido_saved:"+id_pedido);
		
		producto.setId_pedido(id_pedido);
		prodPedidoRepo.save(producto);
		
		return false;
	}

}
