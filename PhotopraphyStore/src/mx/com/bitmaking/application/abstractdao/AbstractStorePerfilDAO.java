package mx.com.bitmaking.application.abstractdao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import mx.com.bitmaking.application.entity.Store_perfil;
import mx.com.bitmaking.application.idao.IStorePerfilDAO;

public abstract class AbstractStorePerfilDAO implements IStorePerfilDAO{
	
	public abstract SessionFactory getSessionFactory();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_perfil> getAllProfiles(){
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery("SELECT p.* FROM Store_perfil p ");
		query.setResultTransformer(Transformers.aliasToBean(Store_perfil.class));
		List<Store_perfil> resp = query.list();
		return resp;
	}
}
