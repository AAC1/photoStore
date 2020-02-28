package mx.com.bitmaking.application.remote.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.entity.Store_cat_estatus;
import mx.com.bitmaking.application.idao.ICatEstatusDAO;
import mx.com.bitmaking.application.service.IStoreCatEstatusService;

@Service("remoteStoreCatEstatusService")
public class StoreCatEstatusService implements IStoreCatEstatusService {
	
	@Autowired
	@Qualifier("remoteCatEstatusDAO")
	ICatEstatusDAO catEstatusDao;
	//IStoreCatEstatusRepo storeCatService; 
	@Override
	public List<Store_cat_estatus> getListEstatus() {
		List<Store_cat_estatus> resp =new ArrayList<>();
		resp = catEstatusDao.findAll();
		
		return resp;
	}


}
