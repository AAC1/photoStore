package mx.com.bitmaking.application.abstractservice;
//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;
import mx.com.bitmaking.application.service.IStoreClteProdCostService;

//@Service("StoreClteProdCostService")
//(value="transactionManager")
public abstract class AbstractStoreClteProdCostService implements IStoreClteProdCostService{
/*
	@Autowired
	@Qualifier("StoreClteProdCostDAO")
	IStoreClteProdCostDAO clteProdCostRepo;
	*/
	public abstract IStoreClteProdCostDAO getClteProdCostRepo();
	
	@Override
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		
		return getClteProdCostRepo().getRowByIdProdAndClient(idCliente, idProd);
	}
	@Override
	public  void insertRow(Store_cliente_prod_cost costProdObj) {
		getClteProdCostRepo().save(costProdObj);
		
	}
	@Override
	public void updateRow(Store_cliente_prod_cost costProdObj) {
		getClteProdCostRepo().update(costProdObj);
		
	}

}
