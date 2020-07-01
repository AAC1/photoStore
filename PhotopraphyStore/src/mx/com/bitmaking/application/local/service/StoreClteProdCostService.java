package mx.com.bitmaking.application.local.service;
//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreClteProdCostService;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;
import mx.com.bitmaking.application.iservice.IStoreClteProdCostService;

@Service("StoreClteProdCostService")

public class StoreClteProdCostService extends AbstractStoreClteProdCostService{//implements IStoreClteProdCostService{

	@Autowired
	@Qualifier("StoreClteProdCostDAO")
	IStoreClteProdCostDAO clteProdCostRepo;
	
	
	@Transactional(value="transactionManager")
	public Store_cliente_prod_cost getRowByIdProdAndClient(int idCliente, int idProd) {
		return super.getRowByIdProdAndClient(idCliente, idProd);
	}
	@Transactional(value="transactionManager")
	public  void insertRow(Store_cliente_prod_cost costProdObj) {
		super.insertRow(costProdObj);
	}
	@Transactional(value="transactionManager")
	public void updateRow(Store_cliente_prod_cost costProdObj) {
		super.updateRow(costProdObj);
	}
	@Transactional(value="transactionManager")
	public void deleteRowByIdCostProd(int idProd) {
		super.deleteRowByIdCostProd(idProd);
	}
	@Override
	public IStoreClteProdCostDAO getClteProdCostRepo() {
		return clteProdCostRepo;
	}
}
