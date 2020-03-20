package mx.com.bitmaking.application.iservice;

import java.util.List;

import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;

public interface IStoreUsuarioService {
	public List<UsuariosDTO> getUsrsByFilter(String login,String usuario,String estatus,
												String sucursal,String perfil);
	
	public void saveUser(Store_usuario objUsr);
	public void deleteUser(Store_usuario objUsr);
	
}
