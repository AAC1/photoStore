package mx.com.bitmaking.application.remote.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreClteProdCostService;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;

@Service("remoteStoreClteProdCostService")
public class StoreClteProdCostService extends AbstractStoreClteProdCostService{//implements IStoreClteProdCostService{

	@Autowired
	@Qualifier("remoteStoreClteProdCostDAO")
	IStoreClteProdCostDAO clteProdCostRepo;
	
	
	@Transactional(value = "remoteTransactionManager")
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		return super.getRowByIdProdAndClient(idCliente, idProd);
	}
	
	@Transactional(value = "remoteTransactionManager")
	public  void insertRow(Store_cliente_prod_cost costProdObj) {
		super.insertRow(costProdObj);
	}
	@Transactional(value = "remoteTransactionManager")
	public void updateRow(Store_cliente_prod_cost costProdObj) {
		super.updateRow(costProdObj);
	}

	@Override
	public IStoreClteProdCostDAO getClteProdCostRepo() {
		return clteProdCostRepo;
	}

}
