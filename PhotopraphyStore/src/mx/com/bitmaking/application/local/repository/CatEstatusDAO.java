/**
 * 
 */
package mx.com.bitmaking.application.local.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.idao.ICatEstatusDAO;

/**
 * @author ayalaja
 *
 */
@Repository("CatEstatusDAO")
public class CatEstatusDAO implements ICatEstatusDAO{
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(value = "transactionManager")
	public List<Store_cat_estatus> findAll() {
		List<Store_cat_estatus> resp= new ArrayList<>();
		SQLQuery qry = sessionFactory.getCurrentSession().createSQLQuery("SELECT s.* FROM Store_cat_estatus s");
		qry.setResultTransformer(Transformers.aliasToBean(Store_cat_estatus.class));
		qry.addScalar("id_estatus",new IntegerType());
		qry.addScalar("estatus",new StringType());
		resp = qry.list();
		return resp;
	}
	
}
