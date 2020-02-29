/**
 * 
 */
package mx.com.bitmaking.application.remote.dao;

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

import mx.com.bitmaking.application.abstractdao.AbstractCatEstatusDAO;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.idao.ICatEstatusDAO;

/**
 * @author ayalaja
 *
 */
@Repository("remoteCatEstatusDAO")
public class CatEstatusDAO extends AbstractCatEstatusDAO {// implements ICatEstatusDAO{
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	@Transactional("remoteTransactionManager")
	public List<Store_cat_estatus> findAll() {
		return super.findAll();
	}


	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
