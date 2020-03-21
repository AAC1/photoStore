package mx.com.bitmaking.application.remote.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreSucursalDAO;
import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;

@Repository("remoteStoreSucursalDAO")
public class StoreSucursalDAO extends AbstractStoreSucursalDAO{

	@Autowired
	@Qualifier("remoteSessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional(value="remoteTransactionManager")
	public List<Store_sucursal> getSuc(String sQry) {
		return super.getSuc(sQry);
	}
	
	@Transactional(value="remoteTransactionManager")
	public void save(Store_sucursal obj) {
		super.save(obj);
		
	}
	
	@Transactional(value="remoteTransactionManager")
	public void update(Store_sucursal obj) {
		super.update(obj);
		
	}

	public void delete(int idSuc) {
		super.delete(idSuc);
	}

}
