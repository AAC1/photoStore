package mx.com.bitmaking.application.remote.repository;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;

@Repository
public interface ICatProdDAO {

	public 	List<CostProductsDTO> getListCostos(int cliente);

	public boolean updateCostProduct(Store_cliente_prod_cost objProd);
}
