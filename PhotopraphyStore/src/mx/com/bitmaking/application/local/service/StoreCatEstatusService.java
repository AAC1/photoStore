package mx.com.bitmaking.application.local.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.local.repository.IStoreCatEstatusRepo;
import mx.com.bitmaking.application.service.IStoreCatEstatusService;

@Service("StoreCatEstatusService")
public class StoreCatEstatusService implements IStoreCatEstatusService {
	
	@Autowired
	IStoreCatEstatusRepo storeCatService; 
	@Override
	@Transactional(value="transactionManager")
	public List<Store_cat_estatus> getListEstatus() {
		List<Store_cat_estatus> resp =new ArrayList<>();
		resp = storeCatService.findAll();
		
		return resp;
	}


}
