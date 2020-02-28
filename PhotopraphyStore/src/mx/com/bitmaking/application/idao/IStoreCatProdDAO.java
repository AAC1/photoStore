/**
 * 
 */
package mx.com.bitmaking.application.idao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import mx.com.bitmaking.application.entity.Store_cat_prod;

/**
 * @author ayalaja
 *
 */
public interface IStoreCatProdDAO {
	//@Query("FROM Store_cat_prod WHERE estatus=1")
	public List<Store_cat_prod> getActiveProducts();
	
	//@Query("SELECT sp.id_prod as id_prod,sp.producto as producto, case sp.estatus when '1' then 'Activo' else 'Inactivo' end as estatus FROM Store_cat_prod sp")
	public List<Store_cat_prod> getAllActiveProducts();
	
	public List<Store_cat_prod> findAll();
	
	public boolean save(Store_cat_prod row);	
	
	public boolean delete(Store_cat_prod row);
	
	public boolean update(Store_cat_prod row);	
}
