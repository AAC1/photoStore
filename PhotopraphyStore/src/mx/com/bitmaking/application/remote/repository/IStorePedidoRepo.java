package mx.com.bitmaking.application.remote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_pedido;

@Repository("remoteStorePedidoRepo")
public interface IStorePedidoRepo extends JpaRepository<Store_pedido,Long>{
	@Query("SELECT p.id_pedido FROM Store_pedido p WHERE p.folio =:folio")
	public int getIdByFolio(@Param("folio")String folio);
}
