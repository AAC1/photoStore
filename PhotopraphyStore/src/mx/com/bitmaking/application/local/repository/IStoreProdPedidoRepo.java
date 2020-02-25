package mx.com.bitmaking.application.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.local.entity.Store_prod_pedido;

@Repository("localStoreProdPedidoRepo")
public interface IStoreProdPedidoRepo extends JpaRepository<Store_prod_pedido, Long>{

}
