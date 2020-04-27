package mx.com.bitmaking.application.local.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractStoreCargoAbonoDAO;
import mx.com.bitmaking.application.entity.Store_cargo_abono;


@Repository("StoreCargoAbonoDAO")
public class StoreCargoAbonoDAO  extends AbstractStoreCargoAbonoDAO{
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Transactional(value="transactionManager")
	public void saveCargoAbono(Store_cargo_abono row) {
		super.saveCargoAbono(row);

	}

	@Transactional(value="transactionManager")
	public List<Store_cargo_abono> getCargoAbonoByDateSuc(String date, int id_sucursal) {
		return super.getCargoAbonoByDateSuc(date,id_sucursal);
	}

}