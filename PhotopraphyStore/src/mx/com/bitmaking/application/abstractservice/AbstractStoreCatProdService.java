/**
 * 
 */
package mx.com.bitmaking.application.abstractservice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.entity.Store_cat_prod;
import mx.com.bitmaking.application.idao.ICatProdDAO;
import mx.com.bitmaking.application.idao.IStoreCatProdDAO;
import mx.com.bitmaking.application.idao.IStoreClteProdCostDAO;
import mx.com.bitmaking.application.idao.IStoreProdPedidoDAO;
import mx.com.bitmaking.application.iservice.IStoreCatProdService;
import mx.com.bitmaking.application.util.GeneralMethods;

/**
 * @author albcervantes
 *
 */

//@Service("StoreCatProdService")
public abstract class AbstractStoreCatProdService implements IStoreCatProdService { // implements IStoreCatProdService
	/*
	 * @Autowired
	 * 
	 * @Qualifier("StoreCatProdDAO") private IStoreCatProdDAO catProductRepo;
	 * 
	 * @Autowired
	 * 
	 * @Qualifier("CatProdDAO") private ICatProdDAO catProdDAO;
	 */

	public abstract ICatProdDAO getCatProdDAO();

	public abstract IStoreClteProdCostDAO getIStoreClteProdCostDAO();

	@Override
	@Transactional(value = "transactionManager")
	public List<Store_cat_prod> getCatalogoProduct() {
		List<Store_cat_prod> resp = new ArrayList<>();

		return getCatProdDAO().getActiveProducts();
	}

	@Override
	public List<Store_cat_prod> getAllCatalogoProduct() {
		List<Store_cat_prod> resp = getCatProdDAO().findAll();
		for (int i = 0; i < resp.size(); i++) {
			if ("1".equals(resp.get(i).getEstatus())) {
				resp.get(i).setEstatus("Activo");
			} else {
				resp.get(i).setEstatus("Inactivo");
			}
		}
		return resp;
	}

	@Override
	public Integer insertRow(Store_cat_prod row) {

		int id = getCatProdDAO().save(row);
		System.out.println("ID_SAVED: "+id);
		return id;
	}

	@Override
	public boolean updateRow(Store_cat_prod row) throws Exception {

		getCatProdDAO().update(row);

		return true;
	}

	@Override
	public boolean deleteRow(Store_cat_prod row) throws Exception {
		/* Borra primero los costos de los productos */
		getIStoreClteProdCostDAO().deleteRowsByIdProd(getIStoreClteProdCostDAO().getRowByIdProd(row.getId_prod()));
		getCatProdDAO().delete(row);

		return true;
	}

	@Override
	public LinkedHashMap<Integer, Store_cat_prod> getAllCatalogoProduct2() {
		LinkedHashMap<Integer, Store_cat_prod> hasResp = new LinkedHashMap<>();

		List<Store_cat_prod> resp = getCatProdDAO().findAll();

		for (int i = 0; i < resp.size(); i++) {

			if ("1".equals(resp.get(i).getEstatus())) {
				resp.get(i).setEstatus("Activo");
			} else {
				resp.get(i).setEstatus("Inactivo");
			}
			hasResp.put(resp.get(i).getId_prod(), resp.get(i));

		}
		return hasResp;
	}

	// @SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Integer, CostProductsDTO> getCostProdByClient(int cliente) {
		List<CostProductsDTO> resp = getCatProdDAO().getListCostos(cliente);
		LinkedHashMap<Integer, CostProductsDTO> hasResp = new LinkedHashMap<>();

		for (CostProductsDTO el : resp) {
			// objDto = (CostProductsDTO)el;
			hasResp.put(el.getId_prod(), el);

		}
		return hasResp;
	}

	@Override
	public List<Store_cat_prod> getCatByPadre(int idPadre) {
		List<Store_cat_prod> resp = getCatProdDAO().getCatByPadre(idPadre);
		return resp;
	}
	
	@Override
	public Store_cat_prod getCatById(int idProd) {
		Store_cat_prod resp = getCatProdDAO().getCatByIdProd(idProd);
		return resp;
	}
}
