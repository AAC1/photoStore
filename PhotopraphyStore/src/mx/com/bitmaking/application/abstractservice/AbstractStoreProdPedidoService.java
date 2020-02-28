package mx.com.bitmaking.application.abstractservice;

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

import mx.com.bitmaking.application.entity.Store_prod_pedido;
import mx.com.bitmaking.application.idao.IPedidoDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.service.IStoreProdPedidoService;

//@Service("StoreProdPedidoService")
public abstract class AbstractStoreProdPedidoService implements IStoreProdPedidoService{
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("StoreProdPedidoDAO")
	private IStoreProdPedidoDAO prodPedidoRepo;
	//private IStoreProdPedidoRepo prodPedidoRepo;
	
	@Autowired
	@Qualifier("PedidoDAO")
	private IPedidoDAO pedidoRepo;
	*/
	
	public abstract IStoreProdPedidoDAO getProdPedidoRepo();
	public abstract IPedidoDAO getPedidoDao();
	public abstract SessionFactory getSessionFactory();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_prod_pedido> getListProdPedidos(String pedidos) {
		List<Store_prod_pedido> results =null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT p.*");
		qry.append(" FROM Store_prod_pedido p ");
		qry.append(" WHERE p.id_pedido in "+pedidos);
		
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
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
	public boolean guardaProdsByPedido(String folio, Store_prod_pedido producto) {
		int id_pedido = getPedidoDao().getIdByFolio(folio);
		System.out.println("Id_pedido_saved:"+id_pedido);
		
		producto.setId_pedido(id_pedido);
		getProdPedidoRepo().save(producto);
		
		return false;
	}

}
