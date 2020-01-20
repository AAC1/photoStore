/**
 * 
 */
package mx.com.bitmaking.application.service;

import java.util.ArrayList;
import java.util.List;

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

	//@Override
	public List<Store_cat_prod> getCatalogoProduct(){
		List<Store_cat_prod> resp = new ArrayList<>();
		
		return catProductRepo.getActiveProducts();
	}
	
	
}
