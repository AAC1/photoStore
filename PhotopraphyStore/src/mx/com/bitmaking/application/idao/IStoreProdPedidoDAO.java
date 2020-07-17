package mx.com.bitmaking.application.idao;

import java.util.List;

import mx.com.bitmaking.application.entity.Store_pedido;
import mx.com.bitmaking.application.entity.Store_prod_pedido;

public interface IStoreProdPedidoDAO {

	public void save(Store_prod_pedido producto);
	public List<Store_prod_pedido> getListProdPedidos(String pedidos);
	public void deleteByIdPedido(String folio);
	
	public void editProd(Store_prod_pedido obj);
}
