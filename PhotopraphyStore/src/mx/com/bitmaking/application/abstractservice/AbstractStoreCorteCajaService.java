package mx.com.bitmaking.application.abstractservice;

import mx.com.bitmaking.application.entity.Store_corte_caja;
import mx.com.bitmaking.application.idao.IStoreCorteCajaDAO;
import mx.com.bitmaking.application.iservice.IStoreCorteCajaService;

public abstract class AbstractStoreCorteCajaService implements IStoreCorteCajaService {
	
	public abstract IStoreCorteCajaDAO storeCorteCajaDAO() ;
	@Override
	public Store_corte_caja getCorteCajaByDate(String date) {
		
		return storeCorteCajaDAO().getCorteCajaByDate(date);
	}

	@Override
	public void saveCorteCaja(Store_corte_caja row) {
		storeCorteCajaDAO().saveCorteCaja(row);
	}

}
