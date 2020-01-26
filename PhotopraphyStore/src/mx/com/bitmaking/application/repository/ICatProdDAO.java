package mx.com.bitmaking.application.repository;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.dto.CostProductsDTO;

@Repository
public interface ICatProdDAO {

	public 	List<CostProductsDTO> getListCostos(int cliente);
}
