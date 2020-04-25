package mx.com.bitmaking.application.remote.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreCorteCajaDAO;
import mx.com.bitmaking.application.entity.Store_corte_caja;

@Repository("remoteStoreCorteCajaDAO")
public class StoreCorteCajaDAO extends AbstractStoreCorteCajaDAO{
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	
	@Transactional(value="remoteTransactionManager")
	public Store_corte_caja getCorteCajaByDate(String date,int id_sucursal){
		return super.getCorteCajaByDate(date,id_sucursal);
	}
	
	@Transactional(value="remoteTransactionManager")
	public void saveCorteCaja(Store_corte_caja row){
		super.saveCorteCaja(row);
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
}
