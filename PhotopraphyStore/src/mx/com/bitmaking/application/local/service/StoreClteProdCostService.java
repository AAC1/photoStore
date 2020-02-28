package mx.com.bitmaking.application.local.service;
//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;
import mx.com.bitmaking.application.service.IStoreClteProdCostService;

@Service("StoreClteProdCostService")
//(value="transactionManager")
public class StoreClteProdCostService implements IStoreClteProdCostService{

	@Autowired
	@Qualifier("StoreClteProdCostDAO")
	IStoreClteProdCostDAO clteProdCostRepo;
	
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
	@Override
	public void updateRow(Store_cliente_prod_cost costProdObj) {
		clteProdCostRepo.update(costProdObj);
		
	}

}
