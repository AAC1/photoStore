/**
 * 
 */
package mx.com.bitmaking.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.repository.IStoreCatProdRepo;

/**
 * @author albcervantes
 *
 */

@Service("StoreCatProdService")
@Scope("prototype")
public class StoreCatProdService implements IStoreCatProdService{ // implements IStoreCatProdService
	
	@Autowired
	 IStoreCatProdRepo catProductRepo;
	
	@Autowired
	@Qualifier(value = "entityManager")
	EntityManager entityManager;
	/*
	@Autowired
	private SessionFactory entityManager;
*/
	@Override
	public List<Store_cat_prod> getCatalogoProduct(){
		List<Store_cat_prod> resp = new ArrayList<>();
		
		return catProductRepo.getActiveProducts();
	}
	@Override
	public List<Store_cat_prod> getAllCatalogoProduct(){
		List<Store_cat_prod> resp = catProductRepo.findAll();
		for(int i=0; i<resp.size();i++){
			if("1".equals(resp.get(i).getEstatus())){
				resp.get(i).setEstatus("Activo");
			}else{
				resp.get(i).setEstatus("Inactivo");
			}
		}
		return resp;
	}
	@Override
	public boolean insertRow(Store_cat_prod row) {
		
		catProductRepo.save(row);
		return true;
	}
	@Override
	public boolean deleteRow(Store_cat_prod row) {
		
		catProductRepo.delete(row);
		return true;
	}
	
	@Override
	public LinkedHashMap<Integer, Store_cat_prod> getAllCatalogoProduct2() {
		LinkedHashMap<Integer, Store_cat_prod> hasResp = new LinkedHashMap<>();
		
		List<Store_cat_prod> resp = catProductRepo.findAll();
		
		for(int i=0; i<resp.size();i++){
			
			if("1".equals(resp.get(i).getEstatus())){
				resp.get(i).setEstatus("Activo");
			}else{
				resp.get(i).setEstatus("Inactivo");
			}
			hasResp.put(resp.get(i).getId_prod(), resp.get(i));
			
		}
		return hasResp;
	}
	
	//@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, CostProductsDTO> getCostProdByClient(int cliente) {
		List<CostProductsDTO> resp = null;
		LinkedHashMap<Integer, CostProductsDTO> hasResp = new LinkedHashMap<>();
		
		Session session = entityManager.unwrap(Session.class);
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT p.id_prod ,p.id_padre_prod ,");
		qry.append("p.producto as producto,(case when p.estatus > 0 then 'Activo' else 'Inactivo' end) as estatus, ");
		qry.append("a.costo as costo,a.bar_code as bar_code ");
		qry.append(" FROM Store_cat_prod p ");
		qry.append(" LEFT OUTER JOIN  Store_cliente_prod_cost a on p.id_prod =a.id_prod AND a.id_cliente = 0");
		
		Query query = session.createQuery(qry.toString());//.setParameter("idCliente",cliente);
		
		 resp =(List<CostProductsDTO>) query.getResultList() ;
	
		 if(resp !=null) {
			 System.out.println(resp.getClass().getName());
			 if(resp.size()>0) {

				 System.out.println(resp.get(0).getCosto());
				 System.out.println(resp.get(0).getClass().getName());
			 }
		 }
		for(int i=0; i<resp.size();i++){
			/*
			if("1".equals(resp.get(i).getEstatus())){
				resp.get(i).setEstatus("Activo");
			}else{
				resp.get(i).setEstatus("Inactivo");
			}*/
			hasResp.put(resp.get(i).getId_prod(), resp.get(i));
			
		}
		return hasResp;
	}
	
}
