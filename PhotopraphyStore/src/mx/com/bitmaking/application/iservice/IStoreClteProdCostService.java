package mx.com.bitmaking.application.iservice;

import java.util.List;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;

public interface IStoreClteProdCostService {
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente,int idProd);
	public  void insertRow(Store_cliente_prod_cost costProdObj);
	public void updateRow(Store_cliente_prod_cost costProdObj);
	public void deleteRowByIdCostProd(int costProdObj);
	public void deleteRowByCte(int idCliente);
}
