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
@Scope("prototype")
public interface IStoreCatProdService {
	
	public List<Store_cat_prod> getCatalogoProduct();
	
}
