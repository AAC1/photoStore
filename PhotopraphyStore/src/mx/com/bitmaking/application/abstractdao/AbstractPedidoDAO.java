package mx.com.bitmaking.application.abstractdao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.idao.IPedidoDAO;


public abstract class AbstractPedidoDAO implements IPedidoDAO{
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	*/
	public abstract SessionFactory getSessionFactory();
	
	@SuppressWarnings("rawtypes")
	@Override
	public int getCurrentNumberFolio(String prefijo) {
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT max(folio) ");
		qry.append(" FROM store_pedido ");
		qry.append(" WHERE folio like :prefijo ");
		
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
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
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
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
	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> totalPedidosByFec(String fec, String prefijo){
		List<BigDecimal> results = new ArrayList<>();
		StringBuilder sqlQry =  new StringBuilder();
		sqlQry.append("SELECT IFNULL(SUM(monto_ant),0)  as total from store_pedido ");
		sqlQry.append(" WHERE id_estatus = 2 ");//PENDIENTE
		sqlQry.append(" AND DATE_FORMAT(fec_pedido , \"%Y%m%d\") = DATE_FORMAT(CURDATE() , \"%Y%m%d\") ");
		sqlQry.append(" AND FOLIO like :prefijo ");
		sqlQry.append(" UNION ");
		sqlQry.append("SELECT IFNULL(SUM(IF(DATE_FORMAT(fec_entregado, \"%Y%m%d\") = DATE_FORMAT(fec_pedido , \"%Y%m%d\"),monto_total ,(monto_total - monto_ant))),0) as total ");
		sqlQry.append(" FROM store_pedido " );
		sqlQry.append(" WHERE id_estatus = 1 ");//Entregado
		sqlQry.append(" AND DATE_FORMAT(fec_entregado, \"%Y%m%d\") = DATE_FORMAT(CURDATE() , \"%Y%m%d\") ");
		sqlQry.append(" AND FOLIO like :prefijo ");
		
		
		SQLQuery qry= getSessionFactory().getCurrentSession().createSQLQuery(sqlQry.toString());
		qry.setString("prefijo", prefijo+"%");
		
		qry.addScalar("total",new BigDecimalType());
		results =qry.list();
		return results;
	}
	
	@Override
	public void save(Store_pedido pedido) {
		getSessionFactory().getCurrentSession().saveOrUpdate(pedido);
		
	}

	@Override
	public void update(Store_pedido pedido) {
		getSessionFactory().getCurrentSession().update(pedido);
		
	}

}
