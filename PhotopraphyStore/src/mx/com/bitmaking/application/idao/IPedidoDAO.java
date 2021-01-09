package mx.com.bitmaking.application.idao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.bitmaking.application.entity.Store_pedido;

public interface IPedidoDAO {
	public int getCurrentNumberFolio(String prefijo);
	
	//@Query("SELECT p.id_pedido FROM Store_pedido p WHERE p.folio =:folio")
	public int getIdByFolio(String folio);
	
	public void save(Store_pedido pedido);
	
	public void update(Store_pedido pedido);

	public List<BigDecimal> totalPedidosByFec(String fecha, String prefijo);
	
}
