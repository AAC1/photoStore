package mx.com.bitmaking.application.remote.service;

import mx.com.bitmaking.application.local.entity.Store_cliente_prod_cost;

public interface IStoreClteProdCostService {
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente,int idProd);
	public  void insertRow(Store_cliente_prod_cost costProdObj);
}
