package mx.com.bitmaking.application.remote.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractCatProdDAO;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.entity.Store_cliente_prod_cost;
import mx.com.bitmaking.application.idao.ICatProdDAO;

@Repository("remoteCatProdDAO")
public class CatProdDAO  extends AbstractCatProdDAO {//implements ICatProdDAO {
	
	@Autowired
	@Qualifier("remoteSessionFactory")
	protected SessionFactory sessionFactory;
	
	@Transactional(value="remoteTransactionManager")
	public List<CostProductsDTO> getListCostos(int cliente) {
		return super.getListCostos(cliente);
	}
	@Transactional(value="remoteTransactionManager")
	public boolean updateCostProduct(Store_cliente_prod_cost objProd) {
		return super.updateCostProduct(objProd);
	}
	
	@Transactional("remoteTransactionManager")
	public List<Store_cat_prod> getActiveProducts() {
		return super.getActiveProducts();
	}
	@Transactional("remoteTransactionManager")
	public List<Store_cat_prod> getAllActiveProducts() {
		return super.getAllActiveProducts();
	}
	@Transactional("remoteTransactionManager")
	public List<Store_cat_prod> findAll() {
		return super.findAll();
	}
	@Transactional("remoteTransactionManager")
	public Integer save(Store_cat_prod row) {
		return super.save(row);
	}
	@Transactional("remoteTransactionManager")
	public boolean delete(Store_cat_prod row) throws Exception {
		return super.delete(row);
	}
	@Transactional("remoteTransactionManager")
	public boolean update(Store_cat_prod row) throws Exception{
		return super.update(row);
	}
	@Transactional(value="remoteTransactionManager")
	public Store_cat_prod getCatByIdProd(int idPadre) {
		return super.getCatByIdProd(idPadre);
	}
	@Transactional(value="remoteTransactionManager")
	public CostProductsDTO getCatByClteAndBarcode(int cliente,String barcode){
		return super.getCatByClteAndBarcode(cliente,barcode);
	}
	@Transactional(value="remoteTransactionManager")
	public boolean updateProductQuantity(int cantidad, int idProd){
		return super.updateProductQuantity(cantidad, idProd);
	}
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
