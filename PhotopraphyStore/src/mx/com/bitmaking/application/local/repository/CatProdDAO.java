package mx.com.bitmaking.application.local.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractCatProdDAO;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.ICatProdDAO;

@Repository("CatProdDAO")
public class CatProdDAO extends AbstractCatProdDAO {// implements ICatProdDAO {
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Transactional(value="transactionManager")
	public List<CostProductsDTO> getListCostos(int cliente) {
		return super.getListCostos(cliente);
	}
	@Transactional(value="transactionManager")
	public boolean updateCostProduct(Store_cliente_prod_cost objProd) {
		return super.updateCostProduct(objProd);
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
