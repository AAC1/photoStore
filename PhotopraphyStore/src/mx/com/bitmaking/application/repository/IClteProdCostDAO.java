package mx.com.bitmaking.application.repository;

import java.util.List;

import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;

public interface IClteProdCostDAO {
	public List<PedidosReporteDTO> consultaPedido(String qry);
}
