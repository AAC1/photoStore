package mx.com.bitmaking.application.iservice;

import java.util.List;

import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;

public interface IStoreSucursalService {
	public List<SucursalDTO> getSuc(String sQry);
	public void save(SucursalDTO obj);
	public void update(SucursalDTO obj);
	public void delete(SucursalDTO obj);
}
