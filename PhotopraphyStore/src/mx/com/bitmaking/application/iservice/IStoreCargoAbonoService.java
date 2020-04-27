package mx.com.bitmaking.application.iservice;

import java.util.List;

import mx.com.bitmaking.application.entity.Store_cargo_abono;

public interface IStoreCargoAbonoService {
	public void saveCargoAbono(Store_cargo_abono row);
	public List<Store_cargo_abono> getCargoAbonoByDateSuc(String date,int id_sucursal);
	
}
