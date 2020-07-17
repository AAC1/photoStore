package mx.com.bitmaking.application.abstractdao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
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
	
	@Override
	public List<Store_prod_pedido> getListProdPedidos(String pedidos){
		List<Store_prod_pedido> results =null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT p.*");
		qry.append(" FROM Store_prod_pedido p ");
		qry.append(" WHERE p.folio in "+pedidos);
		
		
		try{
 
			SQLQuery query= getSessionfActory().getCurrentSession().createSQLQuery(qry.toString());
			
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
	public void deleteByIdPedido(String folio){
		int results =0;
		StringBuilder qry = new StringBuilder();
		qry.append(" DELETE FROM ");
		qry.append(" Store_prod_pedido ");
		qry.append(" WHERE folio = '"+folio+"'");
		
		
		try{
 
			SQLQuery query= getSessionfActory().getCurrentSession().createSQLQuery(qry.toString());
			
			query.setResultTransformer(Transformers.aliasToBean(Store_prod_pedido.class));
			
			results =query.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		System.out.println("Se eliminan "+results +" productos de pedido:"+folio);
	}
	
	@Override
	public void editProd(Store_prod_pedido obj){
		getSessionfActory().getCurrentSession().update(obj);
	}
}
