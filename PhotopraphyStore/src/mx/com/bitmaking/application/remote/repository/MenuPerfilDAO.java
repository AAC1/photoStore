package mx.com.bitmaking.application.remote.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.entity.Store_menu;

@Repository("remoteMenuPerfilDAO")
public class MenuPerfilDAO implements IMenuPerfilDAO{
	
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_menu> getFxIdByPerfil(int id_perfil) {
		List<Store_menu> resp = new ArrayList<>();
		StringBuilder qry = new StringBuilder();
		qry.append(" select m.* from store_menu m ");
		qry.append(" INNER JOIN store_menu_perfil mp ON mp.id_menu=m.id_menu AND mp.id_perfil=:idPerfil");
		
		
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			
			query.setInteger("idPerfil", id_perfil);
			
			query.addScalar("id_menu",new IntegerType());
			query.addScalar("id_padre",new IntegerType());
			query.addScalar("menu_desc",new StringType());
			query.addScalar("uri",new StringType());
			query.addScalar("fx_id",new StringType());
			query.addScalar("estatus",new IntegerType());
			
			query.setResultTransformer(Transformers.aliasToBean(Store_menu.class));
			
			resp = query.list();
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		return resp;
	}

}
