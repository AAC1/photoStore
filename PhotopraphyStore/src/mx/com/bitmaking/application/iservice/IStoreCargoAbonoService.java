package mx.com.bitmaking.application.iservice;

import mx.com.bitmaking.application.entity.Store_cargo_abono;

public interface IStoreCargoAbonoService {
	public void saveCargoAbono(Store_cargo_abono row);
	public Store_cargo_abono getCargoAbonoByDateSuc();
	
}
