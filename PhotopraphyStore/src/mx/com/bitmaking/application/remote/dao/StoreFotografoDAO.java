package mx.com.bitmaking.application.remote.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreFotografoDAO;
import mx.com.bitmaking.application.dto.ClienteDTO;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.idao.IStoreFotografoDAO;

@Repository("remoteStoreFotografoDAO")
public class StoreFotografoDAO extends AbstractStoreFotografoDAO{// implements IStoreFotografoDAO{
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	@Transactional("remoteTransactionManager")
	public List<Store_fotografo> getActiveClients() {
		return super.getActiveClients();
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional(value="remoteTransactionManager")
	@Override
	public List<Store_fotografo> getClients() {
		return super.getClients();
	}

	@Transactional(value="remoteTransactionManager")
	@Override
	public List<ClienteDTO> getClientsByName(String name) {
		return super.getClientsByName(name);
	}


	@Transactional(value="remoteTransactionManager")
	@Override
	public void saveCliente(Store_fotografo cliente) {
		super.saveCliente(cliente);
	}

	@Transactional(value="remoteTransactionManager")
	@Override
	public void deleteCliente(int idCliente) {
		super.deleteCliente(idCliente);
	}
}
