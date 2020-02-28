package mx.com.bitmaking.application.idao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;

public interface IStoreClteProdCostDAO {
	//@Query("FROM Store_cliente_prod_cost WHERE id_cliente=:idCliente AND id_prod=:idProd")
	public Store_cliente_prod_cost getRowByIdProdAndClient( int idCliente,int idProd);
	
	public void save(Store_cliente_prod_cost costProdObj);

	public void update(Store_cliente_prod_cost costProdObj);

	public void deleteRowsByIdProd(List<Store_cliente_prod_cost> rows);

	List<Store_cliente_prod_cost> getRowByIdProd(int idProd);
}
