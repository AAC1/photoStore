package mx.com.bitmaking.application.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.iservice.IStoreClteProdCostService;
import mx.com.bitmaking.application.repository.IStoreClteProdCostRepo;

@Service
public class StoreClteProdCostService implements IStoreClteProdCostService{

	@Autowired
	IStoreClteProdCostRepo clteProdCostRepo;
	
	@Override
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		
		return clteProdCostRepo.getRowByIdProdAndClient(idCliente, idProd);
	}
	@Override
	public  void insertRow(Store_cliente_prod_cost costProdObj) {
		clteProdCostRepo.save(costProdObj);
		
	}

}
