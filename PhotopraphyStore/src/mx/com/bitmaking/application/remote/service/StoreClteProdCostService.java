package mx.com.bitmaking.application.remote.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.local.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.local.repository.IStoreClteProdCostRepo;

@Service("remoteStoreClteProdCostService")
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
