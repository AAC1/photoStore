package mx.com.bitmaking.application.iservice;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;

public interface IStoreClteProdCostService {
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente,int idProd);
	public  void insertRow(Store_cliente_prod_cost costProdObj);
}
