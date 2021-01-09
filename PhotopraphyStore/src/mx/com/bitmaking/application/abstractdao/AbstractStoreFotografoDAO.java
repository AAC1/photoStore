package mx.com.bitmaking.application.abstractdao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.dto.ClienteDTO;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.idao.IStoreFotografoDAO;


public abstract class AbstractStoreFotografoDAO implements IStoreFotografoDAO{
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
*/
	public abstract SessionFactory getSessionFactory();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_fotografo> getActiveClients() {
		List<Store_fotografo> results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT s.* ");
		qry.append(" FROM Store_fotografo s WHERE estatus=1 ");
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
			query.setResultTransformer(Transformers.aliasToBean(Store_fotografo.class));
			
			results =query.list();
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_fotografo> getClients() {
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT s.* ");
		qry.append(" FROM Store_fotografo s  ");
		List<Store_fotografo> results = null;
		try {
			
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			query.setResultTransformer(Transformers.aliasToBean(Store_fotografo.class));
			results =query.list();
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteDTO> getClientsByName(String name,String operator) {
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT s.*,IF(s.estatus=1,'ACTIVO','NO ACTIVO') as estatusStr ");
		qry.append(" FROM Store_fotografo s  WHERE UPPER(fotografo) "+operator);
		qry.append(" UPPER(:name)");
		List<ClienteDTO> results = null;
		try {
			
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			if("like".contentEquals("like")) {
				query.setParameter("name", "%"+name+"%");
			}
			else {
				query.setParameter("name", name);
			}
			
			query.setResultTransformer(Transformers.aliasToBean(ClienteDTO.class));
			
			results =query.list();
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return results;
	}
	
	@Override
	public Integer saveCliente(Store_fotografo cliente) {
		Session session =getSessionFactory().getCurrentSession();
		Integer resp = (Integer)session.save(cliente);//saveOrUpdate(row);
		System.out.println("ID_Cte_Saved: "+resp);
		return resp;
	}
	
	@Override
	public void updateCliente(Store_fotografo cliente) {
		Session session =getSessionFactory().getCurrentSession();
		session.update(cliente);//saveOrUpdate(row);
	}

	@Override
	public void deleteCliente(int idCliente) {
		Session session =getSessionFactory().getCurrentSession();
		Store_fotografo usr =session.get(Store_fotografo.class, idCliente);
		getSessionFactory().getCurrentSession().delete(usr);
	}
}
