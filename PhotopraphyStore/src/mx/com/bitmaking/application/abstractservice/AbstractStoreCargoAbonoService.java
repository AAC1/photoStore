package mx.com.bitmaking.application.abstractservice;



import java.util.List;

import mx.com.bitmaking.application.entity.Store_cargo_abono;
import mx.com.bitmaking.application.idao.IStoreCargoAbonoDAO;
import mx.com.bitmaking.application.iservice.IStoreCargoAbonoService;

public abstract class AbstractStoreCargoAbonoService implements IStoreCargoAbonoService {
	
	public abstract IStoreCargoAbonoDAO getStoreCargoAbonoDAO();
	
	@Override
	public void saveCargoAbono(Store_cargo_abono row) {
		getStoreCargoAbonoDAO().saveCargoAbono(row);
	}

	@Override
	public List<Store_cargo_abono> getCargoAbonoByDateSuc(String date,int id_sucursal) {
		return getStoreCargoAbonoDAO().getCargoAbonoByDateSuc(date, id_sucursal);
	}

}
