package mx.com.bitmaking.application.remote.repository;


import org.hibernate.SQLQuery;
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
@Repository("remoteStoreClteProdCostDAO")
public class StoreClteProdCostDAO implements IStoreClteProdCostDAO{
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;

	@Override
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		StringBuilder sQry = new StringBuilder();
		
		sQry.append("select s.* ");
		sQry.append("FROM Store_cliente_prod_cost s WHERE s.id_cliente=:idCliente AND s.id_prod=:idProd");
		SQLQuery qry = sessionFactory.getCurrentSession()
				.createSQLQuery(sQry.toString());
		
		qry.setInteger("idCliente", idCliente);
		qry.setInteger("idProd", idProd);
		//Validar si es necesario agregar escalar
		qry.setResultTransformer(Transformers.aliasToBean(Store_cliente_prod_cost.class));
		qry.addScalar("id_clte_prod_cost", new IntegerType());
		qry.addScalar("id_cliente", new IntegerType());
		qry.addScalar("id_prod", new IntegerType());
		qry.addScalar("bar_code", new StringType());
		qry.addScalar("costo", new BigDecimalType());
		
		Store_cliente_prod_cost resp = (Store_cliente_prod_cost)qry.setMaxResults(1).uniqueResult();
		return resp;

	}

	public void save(Store_cliente_prod_cost costProdObj) {
		 sessionFactory.getCurrentSession().saveOrUpdate(costProdObj);
	}

	@Override
	public void update(Store_cliente_prod_cost costProdObj) {
		sessionFactory.getCurrentSession().update(costProdObj);
		
	}
	

}
