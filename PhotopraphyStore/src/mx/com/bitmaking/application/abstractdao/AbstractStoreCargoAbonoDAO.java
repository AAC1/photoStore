/**
 * 
 */
package mx.com.bitmaking.application.abstractdao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import mx.com.bitmaking.application.entity.Store_cargo_abono;
import mx.com.bitmaking.application.entity.Store_corte_caja;
import mx.com.bitmaking.application.idao.IStoreCargoAbonoDAO;

/**
 * @author albcervantes
 *
 */
public abstract class AbstractStoreCargoAbonoDAO implements IStoreCargoAbonoDAO {
	
	public abstract SessionFactory getSessionFactory();
	
	/* (non-Javadoc)
	 * @see mx.com.bitmaking.application.idao.IStoreCargoAbonoDAO#saveCargoAbono(mx.com.bitmaking.application.entity.Store_cargo_abono)
	 */
	@Override
	public void saveCargoAbono(Store_cargo_abono row) {
		getSessionFactory().getCurrentSession().saveOrUpdate(row);

	}

	/* (non-Javadoc)
	 * @see mx.com.bitmaking.application.idao.IStoreCargoAbonoDAO#getCargoAbonoByDateSuc()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_cargo_abono> getCargoAbonoByDateSuc(String date, int id_sucursal) {
		List<Store_cargo_abono> resp = new ArrayList<>();
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT s.* ");
		qry.append(" FROM Store_cargo_abono s WHERE fecha=:date AND id_sucursal=:id_sucursal ");
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
			query.setResultTransformer(Transformers.aliasToBean(Store_cargo_abono.class));
			query.addScalar("id_cargo_abono", new IntegerType());
			query.addScalar("tipo", new StringType());
			query.addScalar("monto", new BigDecimalType());
			query.addScalar("motivo", new StringType());
			query.addScalar("fecha", new DateType());
			query.addScalar("id_sucursal", new IntegerType());
			
			query.setParameter("date", date);
			query.setParameter("id_sucursal", id_sucursal);
			
			resp =query.list();
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return resp;
	}

}
