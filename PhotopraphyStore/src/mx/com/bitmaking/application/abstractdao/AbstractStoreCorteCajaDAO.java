package mx.com.bitmaking.application.abstractdao;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;

import mx.com.bitmaking.application.entity.Store_corte_caja;
import mx.com.bitmaking.application.idao.IStoreCorteCajaDAO;

public abstract class AbstractStoreCorteCajaDAO implements IStoreCorteCajaDAO {
	
	public abstract SessionFactory getSessionFactory();
	
	@Override
	public Store_corte_caja getCorteCajaByDate(String date,int id_sucursal) {
		Store_corte_caja resp = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT s.* ");
		qry.append(" FROM Store_corte_caja s WHERE fecha=:date AND id_sucursal=:id_sucursal ");
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
			query.setResultTransformer(Transformers.aliasToBean(Store_corte_caja.class));
			query.addScalar("id_corte_caja", new IntegerType());
			query.addScalar("deno1000", new IntegerType());
			query.addScalar("deno500", new IntegerType());
			query.addScalar("deno200", new IntegerType());
			query.addScalar("deno100", new IntegerType());
			query.addScalar("deno50", new IntegerType());
			query.addScalar("deno20", new IntegerType());
			query.addScalar("deno10", new IntegerType());
			query.addScalar("deno5", new IntegerType());
			query.addScalar("deno2", new IntegerType());
			query.addScalar("deno1", new IntegerType());
			query.addScalar("deno050", new IntegerType());
			query.addScalar("importe", new BigDecimalType());
			query.addScalar("importe_ini", new BigDecimalType());
			query.addScalar("id_sucursal", new IntegerType());
			
			resp =(Store_corte_caja) query.setMaxResults(1).uniqueResult();
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return resp;
	}

	@Override
	public void saveCorteCaja(Store_corte_caja row) {
		getSessionFactory().getCurrentSession().saveOrUpdate(row);

	}

}
