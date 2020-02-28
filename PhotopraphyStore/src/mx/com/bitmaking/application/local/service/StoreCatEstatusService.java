package mx.com.bitmaking.application.local.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.idao.ICatEstatusDAO;
import mx.com.bitmaking.application.service.IStoreCatEstatusService;

@Service("StoreCatEstatusService")
public class StoreCatEstatusService implements IStoreCatEstatusService {
	
	@Autowired
	@Qualifier("CatEstatusDAO")
	ICatEstatusDAO storeCatDAO; 
	
	@Override
	@Transactional(value="transactionManager")
	public List<Store_cat_estatus> getListEstatus() {
		List<Store_cat_estatus> resp =new ArrayList<>();
		resp = storeCatDAO.findAll();
		
		return resp;
	}


}
