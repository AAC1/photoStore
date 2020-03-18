package mx.com.bitmaking.application.abstractservice;

import java.util.List;

import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.idao.IStoreSucursalDAO;
import mx.com.bitmaking.application.iservice.IStoreSucursalService;

public abstract class AbstractStoreSucursalService implements IStoreSucursalService {
	
	public abstract IStoreSucursalDAO getStoreSucursalDAO();
	
	@Override
	public List<SucursalDTO> getSuc(String sQry) {
		
		return getStoreSucursalDAO().getSuc(sQry);
	}

	@Override
	public void save(SucursalDTO obj) {
		
		getStoreSucursalDAO().save(obj);
		
	}

	@Override
	public void update(SucursalDTO obj) {
		
		getStoreSucursalDAO().update(obj);
		
	}

	@Override
	public void delete(SucursalDTO obj) {
		
		getStoreSucursalDAO().delete(obj);
		
	}

}
