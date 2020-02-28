package mx.com.bitmaking.application.remote.repository;


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
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreClteProdCostDAO;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;
@Repository("remoteStoreClteProdCostDAO")
public class StoreClteProdCostDAO extends AbstractStoreClteProdCostDAO{//implements IStoreClteProdCostDAO{
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;

	@Transactional("remoteTransactionManager")
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		return super.getRowByIdProdAndClient(idCliente, idProd);
	}
	@Transactional("remoteTransactionManager")
	public List<Store_cliente_prod_cost> getRowByIdProd( int idProd) {
		return super.getRowByIdProd(idProd);
	}
	@Transactional("remoteTransactionManager")
	public void save(Store_cliente_prod_cost costProdObj) {
		super.save(costProdObj);
	}
	@Transactional("remoteTransactionManager")
	public void update(Store_cliente_prod_cost costProdObj) {
		 super.update(costProdObj);
	}
	@Transactional("remoteTransactionManager")
	public void deleteRowsByIdProd(List<Store_cliente_prod_cost> rows) {
		super.deleteRowsByIdProd(rows);
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	

}
