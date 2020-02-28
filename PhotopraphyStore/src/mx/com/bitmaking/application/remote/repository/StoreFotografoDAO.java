package mx.com.bitmaking.application.remote.repository;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.idao.IStoreFotografoDAO;

@Repository("remoteStoreFotografoDAO")
public class StoreFotografoDAO implements IStoreFotografoDAO{
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	@Override
	public List<Store_fotografo> getActiveClients() {
		List<Store_fotografo> results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT s.* ");
		qry.append(" FROM Store_fotografo s WHERE estatus=1 ");
		
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			
			query.setResultTransformer(Transformers.aliasToBean(Store_fotografo.class));
			
			results =query.list();
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		
		return results;
	}

}
