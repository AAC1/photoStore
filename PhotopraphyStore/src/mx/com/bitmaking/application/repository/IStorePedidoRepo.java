package mx.com.bitmaking.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_pedido;

public interface IStorePedidoRepo extends JpaRepository<Store_pedido,Long>{
	
}
