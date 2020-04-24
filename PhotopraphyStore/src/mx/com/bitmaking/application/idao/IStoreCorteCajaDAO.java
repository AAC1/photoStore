package mx.com.bitmaking.application.idao;

import mx.com.bitmaking.application.entity.Store_corte_caja;

public interface IStoreCorteCajaDAO {
	
	public Store_corte_caja getCorteCajaByDate(String date);
	public void saveCorteCaja( Store_corte_caja row);
}
