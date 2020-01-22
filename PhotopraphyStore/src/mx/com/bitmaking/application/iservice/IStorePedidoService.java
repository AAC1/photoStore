package mx.com.bitmaking.application.iservice;

import java.util.List;

import mx.com.bitmaking.application.entity.Store_pedido;

public interface IStorePedidoService {

	public List<Store_pedido> consultPedido(String qry);
}
