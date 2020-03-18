package mx.com.bitmaking.application.local.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStorePedidoService;
import mx.com.bitmaking.application.abstractservice.AbstractStorePerfilService;
import mx.com.bitmaking.application.entity.Store_perfil;
import mx.com.bitmaking.application.idao.IStorePerfilDAO;

@Service("StorePerfilService")
public class StorePerfilService extends AbstractStorePerfilService{
	
	@Autowired
	@Qualifier("StorePerfilDAO")
	IStorePerfilDAO storePerfilDao;
	
	@Override
	public IStorePerfilDAO getStorePerfilDAO() {
		return storePerfilDao;
	}
	
	@Transactional(value="transactionManager")
	public List<Store_perfil> getAllProfiles() {
		return super.getAllProfiles();
	}
	

}
