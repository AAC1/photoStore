package mx.com.bitmaking.application.abstractservice;

import java.util.List;

import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.service.IStoreUsuarioService;

public abstract class AbstractStoreUsuarioService implements IStoreUsuarioService {
	
	
	@Override
	public List<UsuariosDTO> getUsrsByFilter(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(Store_usuario objUsr) {
		// TODO Auto-generated method stub //Prueba subiendo a otro branch

	}

	@Override
	public void deleteUser(Store_usuario objUsr) {
		// TODO Auto-generated method stub

	}

}
