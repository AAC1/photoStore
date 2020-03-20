package mx.com.bitmaking.application.remote.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStorePerfilDAO;
import mx.com.bitmaking.application.entity.Store_perfil;

@Repository("remoteStorePerfilDAO")
public class StorePerfilDAO extends AbstractStorePerfilDAO {
	
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional(value="remoteTransactionManager")
	public List<Store_perfil> getAllProfiles(){
		return super.getAllProfiles();
	}
	
}
