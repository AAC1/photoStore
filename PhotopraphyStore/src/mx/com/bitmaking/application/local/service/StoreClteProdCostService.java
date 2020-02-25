package mx.com.bitmaking.application.local.service;
//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.local.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.local.repository.IStoreClteProdCostRepo;

@Service
//(value="transactionManager")
public class StoreClteProdCostService implements IStoreClteProdCostService{

	@Autowired
	//@Qualifier("localStoreClteProdCostRepo")
	IStoreClteProdCostRepo clteProdCostRepo;
	
	@Override
	@Transactional(value="transactionManager")
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		
		return clteProdCostRepo.getRowByIdProdAndClient(idCliente, idProd);
	}
	@Override
	@Transactional(value="transactionManager")
	public  void insertRow(Store_cliente_prod_cost costProdObj) {
		clteProdCostRepo.save(costProdObj);
		
	}

}
