package mx.com.bitmaking.application.idao;

import mx.com.bitmaking.application.entity.Store_cargo_abono;

public interface IStoreCargoAbonoDAO {
	
	public void saveCargoAbono(Store_cargo_abono row );
	public Store_cargo_abono getCargoAbonoByDateSuc();
	
	
}
