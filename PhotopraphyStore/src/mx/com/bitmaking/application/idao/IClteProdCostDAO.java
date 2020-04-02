package mx.com.bitmaking.application.idao;

import java.util.List;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;

public interface IClteProdCostDAO {
	public List<PedidosReporteDTO> consultaPedido(String qry);
	public CostProductsDTO getCostByClteAndBarcode(int id_clte, String barcode);
}
