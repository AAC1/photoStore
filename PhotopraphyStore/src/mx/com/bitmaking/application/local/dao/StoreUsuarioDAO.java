package mx.com.bitmaking.application.local.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreUsuarioDAO;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;

@Repository("StoreUsuarioDAO")
public class StoreUsuarioDAO extends AbstractStoreUsuarioDAO{
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional(value="transactionManager")
	public List<UsuariosDTO> getUsr(String qry) {
		return super.getUsr(qry);
	}

	@Transactional(value="transactionManager")
	public void saveUsr(Store_usuario objUsr) {
		super.saveUsr(objUsr);
	}

	@Transactional(value="transactionManager")
	public void deleteUsr(int idUsr) {
		super.deleteUsr(idUsr);
	}
	
	
}
