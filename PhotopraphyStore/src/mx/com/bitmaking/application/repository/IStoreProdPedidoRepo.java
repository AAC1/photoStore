package mx.com.bitmaking.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.entity.Store_prod_pedido;

@Repository
public interface IStoreProdPedidoRepo extends JpaRepository<Store_prod_pedido, Long>{

}
