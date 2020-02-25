package mx.com.bitmaking.application.remote.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.remote.entity.Store_cliente_prod_cost;

@Repository("remoteCatPRodDAO")
public class CatProdDAO implements ICatProdDAO {
	
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
//	@Autowired
//	@Qualifier(value = "entityManager")
//	EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CostProductsDTO> getListCostos(int cliente) {
		List<CostProductsDTO> results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT p.id_prod ,p.id_padre_prod ,");
		qry.append("p.producto as producto,(case when p.estatus > 0 then 'Activo' else 'Inactivo' end) as estatus, ");
		qry.append("a.costo as costo,a.bar_code as bar_code ");
		qry.append(" FROM Store_cat_prod p ");
		qry.append(" LEFT OUTER JOIN  Store_cliente_prod_cost a on p.id_prod =a.id_prod AND a.id_cliente = :cliente");
		
		
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			
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
		qry.append(" SET p.costo=:costo, p.bar_code:barcode ");
		qry.append(" WHERE  p.id_prod =:idProd AND p.id_cliente = :cliente");
		
		
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			
			query.setDouble("costo", objProd.getCosto());
			query.setString("barcode", objProd.getBar_code());
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

}
