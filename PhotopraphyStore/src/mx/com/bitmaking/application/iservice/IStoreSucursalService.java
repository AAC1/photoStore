package mx.com.bitmaking.application.iservice;

import java.util.List;

import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;

public interface IStoreSucursalService {
	public List<Store_sucursal> getSuc(String sucursal,String prefijo,String razonSocial);
	public void save(Store_sucursal obj);
	public void update(Store_sucursal obj);
	public void delete(int idSuc);
}
