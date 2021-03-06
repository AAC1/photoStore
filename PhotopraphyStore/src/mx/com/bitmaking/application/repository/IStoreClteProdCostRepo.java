package mx.com.bitmaking.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;

public interface IStoreClteProdCostRepo extends JpaRepository<Store_cliente_prod_cost, Long>{
	
	@Query("FROM Store_cliente_prod_cost WHERE id_cliente=:idCliente AND id_prod=:idProd")
	public Store_cliente_prod_cost getRowByIdProdAndClient(@Param("idCliente") int idCliente,@Param("idProd")int idProd);
}
