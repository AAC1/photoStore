/**
 * 
 */
package mx.com.bitmaking.application.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;

/**
 * @author albcervantes
 *
 */

public interface IStoreCatProdService {

	public List<Store_cat_prod> getCatalogoProduct();
	public List<Store_cat_prod> getAllCatalogoProduct();

	public LinkedHashMap<Integer,Store_cat_prod> getAllCatalogoProduct2();
	public boolean insertRow(Store_cat_prod row);
	public boolean deleteRow(Store_cat_prod row);
	
	public LinkedHashMap<Integer, CostProductsDTO> getCostProdByClient(int cliente);
	
}
