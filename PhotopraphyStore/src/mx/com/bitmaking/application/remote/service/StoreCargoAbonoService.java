package mx.com.bitmaking.application.remote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.abstractservice.AbstractStoreCargoAbonoService;
import mx.com.bitmaking.application.entity.Store_cargo_abono;
import mx.com.bitmaking.application.idao.IStoreCargoAbonoDAO;

@Service("remoteStoreCargoAbonoService")
public class StoreCargoAbonoService extends AbstractStoreCargoAbonoService{
	
	@Autowired
	@Qualifier("remoteStoreCargoAbonoDAO") 
	private IStoreCargoAbonoDAO storeCargoAbonoDAO;
	
	@Override
	public IStoreCargoAbonoDAO getStoreCargoAbonoDAO() {
		return storeCargoAbonoDAO;
	}
	
	public void saveCargoAbono(Store_cargo_abono row) {
		super.saveCargoAbono(row);
	}

	public List<Store_cargo_abono> getCargoAbonoByDateSuc(String date,int id_sucursal) {
		return super.getCargoAbonoByDateSuc(date, id_sucursal);
	}
}
