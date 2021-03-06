package mx.com.bitmaking.application.service;

import java.util.List;

import mx.com.bitmaking.application.dto.ProdPedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_prod_pedido;

public interface IStoreProdPedidoService {
	public List<Store_prod_pedido> getListProdPedidos(String pedidos);
	
	public boolean guardaProdsByPedido(String folio,Store_prod_pedido producto);
}
