package mx.com.bitmaking.application.local.service;

import java.util.ArrayList;
import java.util.List;

//import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.dto.ProdPedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.local.repository.IStorePedidoRepo;
import mx.com.bitmaking.application.local.repository.IStoreProdPedidoRepo;
import mx.com.bitmaking.application.service.IStoreProdPedidoService;

@Service("StoreProdPedidoService")
public class StoreProdPedidoService implements IStoreProdPedidoService{
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private IStoreProdPedidoRepo prodPedidoRepo;
	
	@Autowired
	private IStorePedidoRepo pedidoRepo;
	
	@SuppressWarnings("unchecked")
	@Transactional(value="transactionManager")
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
	@Transactional(value="transactionManager")
	public boolean guardaProdsByPedido(String folio, Store_prod_pedido producto) {
		int id_pedido = pedidoRepo.getIdByFolio(folio);
		System.out.println("Id_pedido_saved:"+id_pedido);
		
		producto.setId_pedido(id_pedido);
		prodPedidoRepo.save(producto);
		
		return false;
	}

}
