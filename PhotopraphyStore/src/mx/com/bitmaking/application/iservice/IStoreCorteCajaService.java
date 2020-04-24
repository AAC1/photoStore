package mx.com.bitmaking.application.iservice;

import mx.com.bitmaking.application.entity.Store_corte_caja;

public interface IStoreCorteCajaService {
	
	public Store_corte_caja getCorteCajaByDate(String date);
	
	public void saveCorteCaja(Store_corte_caja row);
}
