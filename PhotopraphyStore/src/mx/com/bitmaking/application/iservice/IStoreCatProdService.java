/**
 * 
 */
package mx.com.bitmaking.application.iservice;

import java.util.List;

import org.springframework.context.annotation.Scope;

import mx.com.bitmaking.application.entity.Store_cat_prod;

/**
 * @author albcervantes
 *
 */

public interface IStoreCatProdService {

	public List<Store_cat_prod> getCatalogoProduct();
	public List<Store_cat_prod> getAllCatalogoProduct();
	
}
