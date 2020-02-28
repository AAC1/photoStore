/**
 * 
 */
package mx.com.bitmaking.application.local.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.idao.ICatProdDAO;
import mx.com.bitmaking.application.idao.IStoreCatProdDAO;
import mx.com.bitmaking.application.service.IStoreCatProdService;
import mx.com.bitmaking.application.util.GeneralMethods;

/**
 * @author albcervantes
 *
 */

@Service("StoreCatProdService")
@Scope("prototype")
public class StoreCatProdService implements IStoreCatProdService{ // implements IStoreCatProdService
	
	@Autowired
	@Qualifier("StoreCatProdDAO")
	private IStoreCatProdDAO catProductRepo;
	
	@Autowired 
	@Qualifier("CatProdDAO")
	private ICatProdDAO catProdDAO;
	
//	@Autowired
//	@Qualifier(value = "entityManager")
//	EntityManager entityManager;

	/*
	@Autowired
	private SessionFactory entityManager;
*/
	@Override
	@Transactional(value="transactionManager")
	public List<Store_cat_prod> getCatalogoProduct(){
		List<Store_cat_prod> resp = new ArrayList<>();
		
		return catProductRepo.getActiveProducts();
	}
	@Override
	@Transactional(value="transactionManager")
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
	@Transactional(value="transactionManager")
	public boolean insertRow(Store_cat_prod row) {
		
		catProductRepo.save(row);
		
		return true;
	}

	@Override
	@Transactional(value="transactionManager")
	public boolean updateRow(Store_cat_prod row) {
		
		catProductRepo.update(row);
		
		return true;
	}
	@Override
	@Transactional(value="transactionManager")
	public boolean deleteRow(Store_cat_prod row) {
		
		catProductRepo.delete(row);
		
		return true;
	}
	
	@Override
	@Transactional(value="transactionManager")
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
	@Transactional(value="transactionManager")
	public LinkedHashMap<Integer, CostProductsDTO> getCostProdByClient(int cliente) {
		List<CostProductsDTO> resp = catProdDAO.getListCostos(cliente);
		LinkedHashMap<Integer, CostProductsDTO> hasResp = new LinkedHashMap<>();
		
		for( CostProductsDTO el: resp){
			//objDto = (CostProductsDTO)el;
			hasResp.put(el.getId_prod(), el);
			
		}
		return hasResp;
	}
	
}
