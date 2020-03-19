package mx.com.bitmaking.application.idao;

import java.util.List;

import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;

public interface IStoreSucursalDAO {
	public List<Store_sucursal> getSuc(String sQry);
	public void save(Store_sucursal obj);
	public void update(Store_sucursal obj);
	public void delete(Store_sucursal obj);
}
