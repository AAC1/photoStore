package mx.com.bitmaking.application.local.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.abstractservice.AbstractStoreUsuarioService;
import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;

@Service("StoreUsuarioService")
public class StoreUsuarioService extends AbstractStoreUsuarioService{
	
	public List<UsuariosDTO> getUsrsByFilter(String filter) {
		return super.getUsrsByFilter(filter);
	}

	public void saveUser(Store_usuario objUsr) {
		super.saveUser(objUsr);
	}

	public void deleteUser(Store_usuario objUsr) {
		super.deleteUser(objUsr);
	}
}
