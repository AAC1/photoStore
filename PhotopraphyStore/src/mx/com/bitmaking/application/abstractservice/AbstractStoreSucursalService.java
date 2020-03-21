package mx.com.bitmaking.application.abstractservice;

import java.util.List;

import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.idao.IStoreSucursalDAO;
import mx.com.bitmaking.application.iservice.IStoreSucursalService;
import mx.com.bitmaking.application.util.GeneralMethods;

public abstract class AbstractStoreSucursalService implements IStoreSucursalService {
	
	public abstract IStoreSucursalDAO getStoreSucursalDAO();
	
	@Override
	public List<Store_sucursal> getSuc(String sucursal,String prefijo,String razonSocial) {
		StringBuilder sQry = new StringBuilder();
		sQry.append(" SELECT s.*  FROM Store_sucursal s ");
		sQry.append(" WHERE 1=1 ");
		sQry.append(GeneralMethods.validIfNull(sucursal, " AND UPPER(s.sucursal) like UPPER(\'%%%s%%\') "));
		sQry.append(GeneralMethods.validIfNull(prefijo, " AND UPPER(s.prefijo) like UPPER(\'%%%s%%\') "));
		sQry.append(GeneralMethods.validIfNull(razonSocial, " AND UPPER(s.razon_social) like UPPER(\'%%%s%%\') "));
		
		return getStoreSucursalDAO().getSuc(sQry.toString());
	}

	@Override
	public void save(Store_sucursal obj) {
		
		getStoreSucursalDAO().save(obj);
		
	}

	@Override
	public void update(Store_sucursal obj) {
		
		getStoreSucursalDAO().update(obj);
		
	}

	@Override
	public void delete(int idSuc) {
		
		getStoreSucursalDAO().delete(idSuc);
		
	}

}
