/**
 * 
 */
package mx.com.bitmaking.application.iservice;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

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
	public Integer insertRow(Store_cat_prod row);
	public boolean deleteRow(Store_cat_prod row)throws Exception;
	public boolean updateRow(Store_cat_prod row) throws Exception;
	
	public LinkedHashMap<Integer, CostProductsDTO> getCostProdByClient(int cliente);
	public List<Store_cat_prod>  getCatByPadre(int idPadre);
	public Store_cat_prod  getCatByPadre(String padreName);
	public Store_cat_prod getCatById(int idProd);
	public boolean createBarcodePDF(FileInputStream fileInputStream, String titulo, String pathReport,String logoPath);
	public CostProductsDTO getCatByClteAndBarcode(int cliente,String barcode);
	public boolean updateProductQuantity(int cantidad, int idProd);
}
