package mx.com.bitmaking.application.remote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.abstractservice.AbstractStoreCorteCajaService;
import mx.com.bitmaking.application.entity.Store_corte_caja;
import mx.com.bitmaking.application.idao.IStoreCorteCajaDAO;

@Service("remoteStoreCorteCajaService")
public class StoreCorteCajaService extends AbstractStoreCorteCajaService{
	
	@Autowired
	@Qualifier("remoteStoreCorteCajaDAO") 
	private IStoreCorteCajaDAO storeCorteCajaDAO;
	
	public Store_corte_caja getCorteCajaByDate(String date,int id_sucursal){
		return super.getCorteCajaByDate(date,id_sucursal);
	}
	
	public void saveCorteCaja(Store_corte_caja row){
		super.saveCorteCaja(row);
	}

	@Override
	public IStoreCorteCajaDAO storeCorteCajaDAO() {
		return storeCorteCajaDAO;
	}
}
