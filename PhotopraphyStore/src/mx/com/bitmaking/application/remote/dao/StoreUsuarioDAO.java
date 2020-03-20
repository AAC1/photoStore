package mx.com.bitmaking.application.remote.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreUsuarioDAO;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;

@Repository("remoteStoreUsuarioDAO")
public class StoreUsuarioDAO extends AbstractStoreUsuarioDAO{
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional(value="remoteTransactionManager")
	public List<UsuariosDTO> getUsr(String qry) {
		return super.getUsr(qry);
	}

	@Transactional(value="remoteTransactionManager")
	public void saveUsr(Store_usuario objUsr) {
		super.saveUsr(objUsr);
	}

	@Transactional(value="remoteTransactionManager")
	public void deleteUsr(Store_usuario objUsr) {
		super.deleteUsr(objUsr);
	}
	
}
