package mx.com.bitmaking.application.remote.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;
import mx.com.bitmaking.application.service.IStoreClteProdCostService;

@Service("remoteStoreClteProdCostService")
public class StoreClteProdCostService implements IStoreClteProdCostService{

	@Autowired
	@Qualifier("remoteStoreClteProdCostDAO")
	IStoreClteProdCostDAO clteProdCostRepo;
	//IStoreClteProdCostRepo clteProdCostRepo;
	
	@Override
	@Transactional(value = "remoteTransactionManager")
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		
		return clteProdCostRepo.getRowByIdProdAndClient(idCliente, idProd);
	}
	@Override
	@Transactional(value = "remoteTransactionManager")
	public  void insertRow(Store_cliente_prod_cost costProdObj) {
		clteProdCostRepo.save(costProdObj);
		
	}
	@Transactional(value = "remoteTransactionManager")
	@Override
	public void updateRow(Store_cliente_prod_cost costProdObj) {
		clteProdCostRepo.update(costProdObj);
		
	}

}
