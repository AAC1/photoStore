package mx.com.bitmaking.application.local.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.local.entity.Store_cat_estatus;
import mx.com.bitmaking.application.local.repository.IStoreCatEstatusRepo;

@Service
public class StoreCatEstatusService implements IStoreCatEstatusService {
	
	@Autowired
	IStoreCatEstatusRepo storeCatService; 
	@Override
	public List<Store_cat_estatus> getListEstatus() {
		List<Store_cat_estatus> resp =new ArrayList<>();
		resp = storeCatService.findAll();
		
		return resp;
	}


}
