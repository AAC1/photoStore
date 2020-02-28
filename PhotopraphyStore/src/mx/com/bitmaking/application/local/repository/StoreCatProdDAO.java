package mx.com.bitmaking.application.local.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;

import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.idao.IStoreCatProdDAO;
@Repository("StoreCatProdDAO")
public class StoreCatProdDAO implements IStoreCatProdDAO {

	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Store_cat_prod> getActiveProducts() {
		List<Store_cat_prod> resp = new ArrayList<>();
		SQLQuery qry = sessionFactory.getCurrentSession()
				.createSQLQuery("SELECT s.* Store_cat_prod s WHERE s.estatus=1");
		qry.setResultTransformer(Transformers.aliasToBean(Store_cat_prod.class));
		qry.addScalar("id_prod",new IntegerType());
		qry.addScalar("id_padre_prod",new IntegerType());
		qry.addScalar("producto",new StringType());
		qry.addScalar("estatus",new StringType());
		resp = qry.list();
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store_cat_prod> getAllActiveProducts() {
		List<Store_cat_prod> resp = new ArrayList<>();

		StringBuilder sQry = new StringBuilder();
		sQry.append("SELECT sp.id_prod as id_prod,sp.producto as producto, sp.id_padre_prod as id_padre_prod, ");
		sQry.append("case sp.estatus when '1' then 'Activo' else 'Inactivo' end as estatus FROM Store_cat_prod sp");
		;
		
		SQLQuery qry = sessionFactory.getCurrentSession().createSQLQuery(sQry.toString());
		qry.setResultTransformer(Transformers.aliasToBean(Store_cat_prod.class));
		qry.addScalar("id_prod",new IntegerType());
		qry.addScalar("id_padre_prod",new IntegerType());
		qry.addScalar("producto",new StringType());
		qry.addScalar("estatus",new StringType());
		
		resp = qry.list();
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store_cat_prod> findAll() {
		List<Store_cat_prod> resp = new ArrayList<>();

		StringBuilder sQry = new StringBuilder();
		sQry.append("SELECT sp.* ");
		sQry.append("FROM Store_cat_prod sp");

		SQLQuery qry = sessionFactory.getCurrentSession().createSQLQuery(sQry.toString());
		qry.setResultTransformer(Transformers.aliasToBean(Store_cat_prod.class));
		qry.addScalar("id_prod",new IntegerType());
		qry.addScalar("id_padre_prod",new IntegerType());
		qry.addScalar("producto",new StringType());
		qry.addScalar("estatus",new StringType());
		
		resp = qry.list();
		return resp;
	}

	@Override
	public boolean save(Store_cat_prod row) {
		Session session =sessionFactory.getCurrentSession();
		session.saveOrUpdate(row);
	//	session.flush();
		return true;
	}

	@Override
	public boolean delete(Store_cat_prod row) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		System.out.println("id_prodTO Delete:"+row.getId_prod());
		try{
			Store_cat_prod el = session.get(Store_cat_prod.class, row.getId_prod());
			session.delete(el);
		}catch(HibernateException e){
			throw new Exception("No se Pudo eliminar registro:"+e.getMessage());
		}
	//	session.flush();
		return true;
	}

	@Override
	public boolean update(Store_cat_prod row) {
		Session session =sessionFactory.getCurrentSession();
		session.update(row);
	//	session.flush();
		return true;
	}

}
