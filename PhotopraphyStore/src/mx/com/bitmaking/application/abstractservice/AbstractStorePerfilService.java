package mx.com.bitmaking.application.abstractservice;

import java.util.List;

import mx.com.bitmaking.application.entity.Store_perfil;
import mx.com.bitmaking.application.idao.IStorePerfilDAO;
import mx.com.bitmaking.application.iservice.IStorePerfilService;

public abstract class AbstractStorePerfilService implements IStorePerfilService {

	@Override
	public List<Store_perfil> getAllProfiles() {

		return getStorePerfilDAO().getAllProfiles();
	}
	
	public abstract IStorePerfilDAO getStorePerfilDAO();

}
