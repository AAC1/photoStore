package mx.com.bitmaking.application.abstractdao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.idao.IStoreSucursalDAO;

public abstract class AbstractStoreSucursalDAO implements IStoreSucursalDAO {
	
	public abstract SessionFactory getSessionFactory();
	
	@Override
	public List<SucursalDTO> getSuc(String sQry) {
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(sQry);
		
		return null;
	}

	@Override
	public void save(Store_sucursal obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Store_sucursal obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Store_sucursal obj) {
		// TODO Auto-generated method stub
		
	}

}
