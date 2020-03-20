package mx.com.bitmaking.application.remote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.abstractservice.AbstractStoreUsuarioService;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.idao.IStoreUsuarioDAO;

@Service("remoteStoreUsuarioService")
public class StoreUsuarioService extends AbstractStoreUsuarioService{
	@Autowired
	@Qualifier("remoteStoreUsuarioDAO")
	IStoreUsuarioDAO storeUsuarioDao;
	
	public List<UsuariosDTO> getUsrsByFiltergetUsrsByFilter(String login,String usuario,String estatus,
			String sucursal,String perfil) {
		return super.getUsrsByFilter(login,usuario,estatus,sucursal,perfil);
	}
	

	public void saveUser(Store_usuario objUsr) {
		super.saveUser(objUsr);
	}

	public void deleteUser(int idUsr) {
		super.deleteUser(idUsr);
	}


	@Override
	public IStoreUsuarioDAO getStoreUsuarioDAO() {
		return storeUsuarioDao;
	}
}

