/**
 * 
 */
package mx.com.bitmaking.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
	
}
