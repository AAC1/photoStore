package mx.com.bitmaking.application.local.repository;

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
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.idao.IPedidoDAO;

@Repository("PedidoDAO")
public class PedidoDAO implements IPedidoDAO{
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Override
	public int getCurrentNumberFolio(String prefijo) {
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT max(folio) ");
		qry.append(" FROM store_pedido ");
		qry.append(" WHERE folio like :prefijo ");
		
		
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			
			query.setString("prefijo", prefijo+"%");
			
			List ls = query.list();
			if(ls!=null && ls.size()>0 && ls.get(0) !=null) {
				System.out.println("ls.get(0).toString():"+ls.get(0).toString());
				if(ls.get(0).toString().length()>5) {
					return Integer.parseInt(ls.get(0).toString().substring(
								ls.get(0).toString().length()-5, ls.get(0).toString().length()))+1;
				}
				return -1;
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		
		return 1;
	}

	@Override
	public int getIdByFolio(String folio) {
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT p.id_pedido  ");
		qry.append(" FROM Store_pedido p ");
		qry.append(" WHERE p.folio =:folio ");
		
		
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			
			query.setString("folio", folio);
			
			List ls = query.list();
			if(ls!=null && ls.size()>0 && ls.get(0) !=null) {
				System.out.println("id_pedido:"+ls.get(0).toString());
				return Integer.parseInt(ls.get(0).toString());
				
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		
		return -1;
	}

	@Override
	public void save(Store_pedido pedido) {
		sessionFactory.getCurrentSession().save(pedido);
		
	}

	@Override
	public void update(Store_pedido pedido) {
		sessionFactory.getCurrentSession().update(pedido);
		
	}

}
