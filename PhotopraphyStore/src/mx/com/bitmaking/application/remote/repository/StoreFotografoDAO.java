package mx.com.bitmaking.application.remote.repository;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreFotografoDAO;
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
	public SessionFactory getSessionFActory() {
		return sessionFactory;
	}

}
