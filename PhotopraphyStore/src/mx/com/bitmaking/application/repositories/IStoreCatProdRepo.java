package mx.com.bitmaking.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.entity.Store_cat_prod;


@Repository
public interface IStoreCatProdRepo extends JpaRepository<Store_cat_prod, Long> {
	/**
	 * Obtiene productos que se encuentre habilitados
	 * @return
	 */
	@Query("FROM Store_cat_prod WHERE estatus=1")
	public List<Store_cat_prod> getActiveProducts();
}
