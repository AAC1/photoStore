package mx.com.bitmaking.application.remote.repository;

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

import mx.com.bitmaking.application.remote.dto.UserSession;

@Repository("remotePedidoDAO")
public class PedidoDAO implements IPedidoDAO{
	@Autowired
	@Qualifier("remoteSessionFactory")
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

}
