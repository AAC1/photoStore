/**
 * 
 */
package mx.com.bitmaking.application.local.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

//import javax.transaction.Transactional;

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
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.util.GeneralMethods;

/**
 * @author albcervantes
 *
 */

@Service("StoreCatProdService")
public class StoreCatProdService extends AbstractStoreCatProdService{ // implements IStoreCatProdService
	
	
	@Autowired 
	@Qualifier("CatProdDAO")
	private ICatProdDAO catProdDAO;
	
	@Autowired 
	@Qualifier(value = "StoreClteProdCostDAO")
	private IStoreClteProdCostDAO storeClteProdCostDAO;

	@Transactional(value="transactionManager")
	public List<Store_cat_prod> getCatalogoProduct(){
		return super.getCatalogoProduct();
	}
	
	@Transactional(value="transactionManager")
	public List<Store_cat_prod> getAllCatalogoProduct(){
		return super.getAllCatalogoProduct();
	}
	
	@Transactional(value="transactionManager")
	public Integer insertRow(Store_cat_prod row) {
		return super.insertRow(row);
	}

	@Transactional(value="transactionManager")
	public boolean updateRow(Store_cat_prod row)throws Exception {
		return super.updateRow(row);
	}

	@Transactional(value="transactionManager")
	public boolean deleteRow(Store_cat_prod row)throws Exception {
		return super.deleteRow(row);
	}
	
	@Transactional(value="transactionManager")
	public LinkedHashMap<Integer, Store_cat_prod> getAllCatalogoProduct2() {
		return super.getAllCatalogoProduct2();
	}
	
	
	@Transactional(value="transactionManager")
	public LinkedHashMap<Integer, CostProductsDTO> getCostProdByClient(int cliente) {
		return super.getCostProdByClient(cliente);
	}
	@Transactional(value="transactionManager")
	public List<Store_cat_prod> getCatByPadre(int idPadre) {
		return super.getCatByPadre(idPadre);
	}
	@Transactional(value="transactionManager")
	public Store_cat_prod getCatByPadre(String padre) {
		return super.getCatByPadre(padre);
	}

	@Transactional(value="transactionManager")
	public Store_cat_prod getCatById(int idPadre) {
		return super.getCatById(idPadre);
	}
	@Transactional(value="transactionManager")
	public boolean createBarcodePDF(FileInputStream fileInputStream, String titulo, String pathReport,String logoPath) {
		return super.createBarcodePDF(fileInputStream, titulo, pathReport,logoPath);
	}
	@Transactional(value="transactionManager")
	public CostProductsDTO getCatByClteAndBarcode(int cliente,String barcode){
		return super.getCatByClteAndBarcode( cliente, barcode);
	}
	
	@Override
	public ICatProdDAO getCatProdDAO() {
		return catProdDAO;
	}

	@Override
	public IStoreClteProdCostDAO getIStoreClteProdCostDAO() {
		return storeClteProdCostDAO;
	}
	
	
}
