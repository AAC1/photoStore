package mx.com.bitmaking.application.idao;

import java.util.List;

import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;

public interface IStoreUsuarioDAO {
	
	public List<UsuariosDTO> getUsr(String qry);
	public void saveUsr(Store_usuario objUsr);
	public void deleteUsr(int idUsr);

}
