package mx.com.bitmaking.application.abstractdao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.idao.IStoreSucursalDAO;

public abstract class AbstractStoreSucursalDAO implements IStoreSucursalDAO {
	
	public abstract SessionFactory getSessionFactory();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_sucursal> getSuc(String sQry) {
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(sQry);
		query.setResultTransformer(Transformers.aliasToBean(SucursalDTO.class));
		//query.addScalar("estatus",new StringType());
		
		return query.list();
	}

	@Override
	public void save(Store_sucursal obj) {
		getSessionFactory().getCurrentSession().saveOrUpdate(obj);
		
	}

	@Override
	public void update(Store_sucursal obj) {
		getSessionFactory().getCurrentSession().update(obj);
		
	}

	@Override
	public void delete(Store_sucursal obj) {
		Store_sucursal suc = getSessionFactory().getCurrentSession().get(Store_sucursal.class, obj.getId_sucursal());
		getSessionFactory().getCurrentSession().delete(suc);
		
	}

}
