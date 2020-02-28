/**
 * 
 */
package mx.com.bitmaking.application.remote.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreCatProdService;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.idao.ICatProdDAO;
import mx.com.bitmaking.application.idao.IStoreCatProdDAO;
import mx.com.bitmaking.application.service.IStoreCatProdService;

/**
 * @author albcervantes
 *
 */

@Service("remoteStoreCatProdService")
@Scope("prototype")
public class StoreCatProdService extends AbstractStoreCatProdService{ //implements IStoreCatProdService{ 
	
	@Autowired
	@Qualifier(value = "remoteStoreCatProdDAO")
	private IStoreCatProdDAO catProductRepo;
	
	@Autowired 
	@Qualifier(value = "remoteCatProdDAO")
	private ICatProdDAO catProdDAO;

	
	@Transactional(value = "remoteTransactionManager")
	public List<Store_cat_prod> getCatalogoProduct(){
		return super.getAllCatalogoProduct();
	}
	
	@Transactional(value = "remoteTransactionManager")
	public List<Store_cat_prod> getAllCatalogoProduct(){
		return super.getAllCatalogoProduct();
	}
	
	@Transactional(value = "remoteTransactionManager")
	public boolean insertRow(Store_cat_prod row) {
		return super.insertRow(row);
	}
	
	@Transactional(value = "remoteTransactionManager")
	public boolean deleteRow(Store_cat_prod row)throws Exception {
		return super.deleteRow(row);
	}
	
	@Transactional(value = "remoteTransactionManager")
	public LinkedHashMap<Integer, Store_cat_prod> getAllCatalogoProduct2() {
		return super.getAllCatalogoProduct2();
	}
	
	
	@Transactional(value = "remoteTransactionManager")
	public LinkedHashMap<Integer, CostProductsDTO> getCostProdByClient(int cliente) {
		return super.getCostProdByClient(cliente);
	}
	
	@Transactional(value="transactionManager")
	public boolean updateRow(Store_cat_prod row) {
		return super.updateRow(row);
	}
	@Override
	public IStoreCatProdDAO getCatProductRepo() {
		return catProductRepo;
	}
	@Override
	public ICatProdDAO getCatProdDAO() {
		return catProdDAO;
	}
}
