package mx.com.bitmaking.application.abstractdao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.ICatProdDAO;


public abstract class AbstractCatProdDAO implements ICatProdDAO {
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	*/
	public abstract SessionFactory getSessionFactory();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CostProductsDTO> getListCostos(int cliente) {
		List<CostProductsDTO> results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT p.id_prod ,p.id_padre_prod ,");
		qry.append("p.producto as producto,(case when p.estatus > 0 then 'Activo' else 'Inactivo' end) as estatus, ");
		qry.append("a.costo as costo,p.barcode as bar_code ");
		qry.append(" FROM Store_cat_prod p ");
		qry.append(" LEFT OUTER JOIN  Store_cliente_prod_cost a on p.id_prod =a.id_prod AND a.id_cliente = :cliente");
		
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
			query.setInteger("cliente", cliente);
			query.setResultTransformer(Transformers.aliasToBean(CostProductsDTO.class));
			
			results =query.list();
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		
		return results;
	}

	@Override
	public boolean updateCostProduct(Store_cliente_prod_cost objProd) {
		boolean resp=false;
		StringBuilder qry = new StringBuilder();
		qry.append(" UPDATE Store_cliente_prod_cost p ");
		qry.append(" SET p.costo=:costo ");//, p.bar_code:barcode
		qry.append(" WHERE  p.id_prod =:idProd AND p.id_cliente = :cliente");
		
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
			query.setBigDecimal("costo", objProd.getCosto());
		//	query.setString("barcode", objProd.getBar_code());
			query.setInteger("idProd", objProd.getId_prod());
			query.setInteger("cliente", objProd.getId_cliente());
		//	query.setResultTransformer(Transformers.aliasToBean(CostProductsDTO.class));
			
			int exec =query.executeUpdate();
			if(exec>0){
				resp = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store_cat_prod> getActiveProducts() {
		List<Store_cat_prod> resp = new ArrayList<>();
		SQLQuery qry = getSessionFactory().getCurrentSession()
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
		
		SQLQuery qry = getSessionFactory().getCurrentSession().createSQLQuery(sQry.toString());
		qry.setResultTransformer(Transformers.aliasToBean(Store_cat_prod.class));
		qry.addScalar("id_prod",new IntegerType());
		qry.addScalar("id_padre_prod",new IntegerType());
		qry.addScalar("producto",new StringType());
		qry.addScalar("estatus",new StringType());
		qry.addScalar("barcode",new StringType());
		
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

		SQLQuery qry = getSessionFactory().getCurrentSession().createSQLQuery(sQry.toString());
		qry.setResultTransformer(Transformers.aliasToBean(Store_cat_prod.class));
		qry.addScalar("id_prod",new IntegerType());
		qry.addScalar("id_padre_prod",new IntegerType());
		qry.addScalar("producto",new StringType());
		qry.addScalar("estatus",new StringType());
		qry.addScalar("barcode",new StringType());
		
		resp = qry.list();
		return resp;
	}

	@Override
	public boolean save(Store_cat_prod row) {
		Session session =getSessionFactory().getCurrentSession();
		session.saveOrUpdate(row);
	//	session.flush();
		return true;
	}

	@Override
	public boolean delete(Store_cat_prod row) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
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
	public boolean update(Store_cat_prod row)throws Exception {
		try {
		Session session =getSessionFactory().getCurrentSession();
		session.update(row);
	//	session.flush();
		}catch (HibernateException e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store_cat_prod> getCatByPadre(int idPadre){
		List<Store_cat_prod> results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT p.* ");
		qry.append(" FROM Store_cat_prod p ");
		qry.append(" WHERE p.id_padre_prod = :idPadre ");
		qry.append(" AND id_prod in (select distinct e.id_padre_prod FROM Store_cat_prod e ) ");
		
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			
			query.setInteger("idPadre", idPadre);
			query.setResultTransformer(Transformers.aliasToBean(Store_cat_prod.class));
			query.addScalar("id_prod", new IntegerType());
			query.addScalar("id_padre_prod", new IntegerType());
			query.addScalar("producto", new StringType());
			query.addScalar("estatus", new StringType());
			query.addScalar("barcode", new StringType());
			
			results =query.list();
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		return results;
	}

}
