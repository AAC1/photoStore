package mx.com.bitmaking.application.remote.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreCatProdDAO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.idao.IStoreCatProdDAO;
@Repository("remoteStoreCatProdDAO")
public class StoreCatProdDAO extends AbstractStoreCatProdDAO{//implements IStoreCatProdDAO {

	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;

	@Transactional("remoteTransactionManager")
	public List<Store_cat_prod> getActiveProducts() {
		return super.getActiveProducts();
	}
	@Transactional("remoteTransactionManager")
	public List<Store_cat_prod> getAllActiveProducts() {
		return super.getAllActiveProducts();
	}
	@Transactional("remoteTransactionManager")
	public List<Store_cat_prod> findAll() {
		return super.findAll();
	}
	@Transactional("remoteTransactionManager")
	public boolean save(Store_cat_prod row) {
		return super.save(row);
	}
	@Transactional("remoteTransactionManager")
	public boolean delete(Store_cat_prod row) throws Exception {
		return super.delete(row);
	}
	@Transactional("remoteTransactionManager")
	public boolean update(Store_cat_prod row) {
		return super.update(row);
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
