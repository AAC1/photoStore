package mx.com.bitmaking.application.abstractdao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;


public abstract class AbstractStoreClteProdCostDAO implements IStoreClteProdCostDAO{
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
*/
	public abstract SessionFactory getSessionFactory();
	
	@Override
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		StringBuilder sQry = new StringBuilder();
		
		sQry.append("select s.* ");
		sQry.append("FROM Store_cliente_prod_cost s WHERE s.id_cliente=:idCliente AND s.id_prod=:idProd");
		SQLQuery qry = getSessionFactory().getCurrentSession()
				.createSQLQuery(sQry.toString());
		
		qry.setInteger("idCliente", idCliente);
		qry.setInteger("idProd", idProd);
		//Validar si es necesario agregar escalar
		qry.setResultTransformer(Transformers.aliasToBean(Store_cliente_prod_cost.class));
		qry.addScalar("id_clte_prod_cost", new IntegerType());
		qry.addScalar("id_cliente", new IntegerType());
		qry.addScalar("id_prod", new IntegerType());
		qry.addScalar("costo", new BigDecimalType());
		
		Store_cliente_prod_cost resp = (Store_cliente_prod_cost)qry.setMaxResults(1).uniqueResult();
		return resp;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_cliente_prod_cost> getRowByIdProd( int idProd) {
		StringBuilder sQry = new StringBuilder();
		List<Store_cliente_prod_cost> resp = new ArrayList<>();
		sQry.append("select s.* ");
		sQry.append("FROM Store_cliente_prod_cost s WHERE  s.id_prod=:idProd");
		SQLQuery qry = getSessionFactory().getCurrentSession()
				.createSQLQuery(sQry.toString());
		
		qry.setInteger("idProd", idProd);
		//Validar si es necesario agregar escalar
		qry.setResultTransformer(Transformers.aliasToBean(Store_cliente_prod_cost.class));
		qry.addScalar("id_clte_prod_cost", new IntegerType());
		qry.addScalar("id_cliente", new IntegerType());
		qry.addScalar("id_prod", new IntegerType());
		qry.addScalar("costo", new BigDecimalType());
		
		resp = qry.list();
		return resp;

	}

	public void save(Store_cliente_prod_cost costProdObj) {
		 getSessionFactory().getCurrentSession().saveOrUpdate(costProdObj);
	}

	@Override
	public void update(Store_cliente_prod_cost costProdObj) {
		
		 
		 getSessionFactory().getCurrentSession().saveOrUpdate(costProdObj);
		
	}

	@Override
	public void deleteRowsByIdProd(List<Store_cliente_prod_cost> rows) {
		 Session session = getSessionFactory().getCurrentSession();
		 Store_cliente_prod_cost row = null;
		 try {
			 for(Store_cliente_prod_cost el: rows) {
				  row=session.get(Store_cliente_prod_cost.class, el.getId_clte_prod_cost());
				  session.delete(row);
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
	}
	

}
