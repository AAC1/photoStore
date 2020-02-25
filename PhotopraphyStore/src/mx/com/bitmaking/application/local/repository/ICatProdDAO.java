package mx.com.bitmaking.application.local.repository;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.local.entity.Store_cliente_prod_cost;

@Repository("localCatProdDAO")
public interface ICatProdDAO {

	public 	List<CostProductsDTO> getListCostos(int cliente);

	public boolean updateCostProduct(Store_cliente_prod_cost objProd);
}
