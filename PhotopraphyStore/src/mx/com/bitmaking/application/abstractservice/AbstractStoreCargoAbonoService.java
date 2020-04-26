package mx.com.bitmaking.application.abstractservice;

import org.hibernate.SessionFactory;

import mx.com.bitmaking.application.entity.Store_cargo_abono;
import mx.com.bitmaking.application.iservice.IStoreCargoAbonoService;

public abstract class AbstractStoreCargoAbonoService implements IStoreCargoAbonoService {
	
	public abstract SessionFactory getSessionFactory();
	
	@Override
	public void saveCargoAbono(Store_cargo_abono row) {
		// TODO Auto-generated method stub

	}

	@Override
	public Store_cargo_abono getCargoAbonoByDateSuc() {
		// TODO Auto-generated method stub
		return null;
	}

}
