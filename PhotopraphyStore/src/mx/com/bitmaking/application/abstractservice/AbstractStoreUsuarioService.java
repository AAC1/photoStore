package mx.com.bitmaking.application.abstractservice;

import java.util.List;

import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.idao.IStoreUsuarioDAO;
import mx.com.bitmaking.application.iservice.IStoreUsuarioService;
import mx.com.bitmaking.application.util.GeneralMethods;

public abstract class AbstractStoreUsuarioService implements IStoreUsuarioService {
	
	
	
	@Override
	public List<UsuariosDTO> getUsrsByFilter(String login,String usuario,String estatus,
			String sucursal,String perfil) {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT usr.id_usr,usr.login,usr.passwd,usr.nombre,usr.correo,usr.telefono,");
		query.append(" usr.direccion,usr.intentos,usr.bloqueado,usr.activo,usr.id_perfil,usr.id_sucursal, ");
		query.append(" s.sucursal, p.perfil,IF(usr.activo='1','ACTIVO','NO ACTIVO') as estatus ");
		query.append(" FROM Store_usuario usr");
		query.append(" INNER JOIN store_sucursal s on usr.id_sucursal = s.id_sucursal");
		query.append(GeneralMethods.validIfNull(sucursal, " AND UPPER(s.sucursal) like UPPER(\'%%%s%%\') "));
		query.append(" INNER JOIN store_perfil p on usr.id_perfil = p.id_perfil");
		query.append(GeneralMethods.validIfNull(perfil, " AND UPPER(p.perfil) like UPPER(\'%%%s%%\') "));
		query.append(" WHERE 1=1 ");
		query.append(GeneralMethods.validIfNull(login, "AND UPPER(usr.login) like UPPER(\'%%%s%%\') "));
		query.append(GeneralMethods.validIfNull(usuario, " AND UPPER(usr.nombre) like UPPER(\'%%%s%%\') "));
		query.append(GeneralMethods.validIfNull(estatus, " AND IF(usr.activo='1','ACTIVO','NO ACTIVO') like UPPER(\'%%%s%%\') "));
		
	// 	System.out.println("Qry Usr:"+ query);
		
		List<UsuariosDTO> resp = getStoreUsuarioDAO().getUsr(query.toString());
		return resp;
	}

	@Override
	public void saveUser(Store_usuario objUsr) {
		 getStoreUsuarioDAO().saveUsr(objUsr);
	}

	@Override
	public void deleteUser(int idUsr) {
		getStoreUsuarioDAO().deleteUsr(idUsr);
	}
	
	public abstract IStoreUsuarioDAO getStoreUsuarioDAO();

}
