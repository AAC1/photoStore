package mx.com.bitmaking.application.abstractservice;


import java.util.List;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.dto.ResponseDTO;
import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.idao.ILoginDAO;
import mx.com.bitmaking.application.idao.IMenuPerfilDAO;
import mx.com.bitmaking.application.iservice.ILoginService;
import mx.com.bitmaking.application.iservice.IStoreSucursalService;
import mx.com.bitmaking.application.util.Constantes;
import mx.com.bitmaking.application.util.Flags;


//@Service("LoginService")
public abstract class AbstractLoginService implements ILoginService{
/*
	@Autowired
	@Qualifier("LoginDAO")
	ILoginDAO loginDAO;
	
	@Autowired
	@Qualifier("MenuPerfilDAO")
	IMenuPerfilDAO menuPerfilDAO;
*/
	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("StoreSucursalService")
	IStoreSucursalService sucursalService;
	
	@Autowired
	@Qualifier("remoteStoreSucursalService")
	IStoreSucursalService remoteSucursalService;
	
	public abstract ILoginDAO getILoginDAO(); 
	public abstract IMenuPerfilDAO getIMenuPerfilDAO(); 
	
	@Override
	public ResponseDTO validUsr(String usr, String passwd) throws Exception{
		ResponseDTO resp = new ResponseDTO();
		resp.setEstado("");
		resp.setMsg("");
		resp.setValid(false);
		UserSession onlyUsr = getILoginDAO().getUsr(usr);
		if(onlyUsr ==null){
			resp.setEstado("ERROR");
			resp.setMsg("Usuario y/o contraseña no validos. Verifique que el usuario y contraseña sean correctas");
			
			return resp;
		}
		if(onlyUsr.getBloqueado()==1){
			resp.setEstado("ERROR");
			resp.setMsg("El usuario '"+usr+"' se encuentra bloqueado. Contacte al administrador.");
			return resp;
		}
		UserSession usrObj = getILoginDAO().getUsrByPasswd(usr, passwd);
		if(usrObj ==null){
			int intento = Constantes.MAX_INTENTOS - (onlyUsr.getIntentos()+1);
			if(intento<=0){
				getILoginDAO().bloqueaUsr(usr);
				resp.setEstado("ERROR");
				resp.setMsg("Usuario bloqueado ");
				return resp;
			}
			getILoginDAO().updateIntentos(usr,onlyUsr.getIntentos()+1);
			
			resp.setEstado("ERROR");
			resp.setMsg("Contraseña incorrecta. Te quedan "+(Constantes.MAX_INTENTOS - (onlyUsr.getIntentos()+1))+" intentos");
			return resp;
		}
		
		if(usrObj.getBloqueado()==1){
			resp.setEstado("ERROR");
			resp.setMsg("El usuario '"+usr+"' se encuentra bloqueado. Contacte al administrador.");
			return resp;
		}else{
			getILoginDAO().updateIntentos(usr,0);
		}
		if(usrObj.getActivo()==0){
			resp.setEstado("ERROR");
			resp.setMsg("La cuenta del usuario '"+usr+"' no se encuentra activa. Contacte al administrador.");
			return resp;
		}
		//System.out.println("getId_perfil:"+usrObj.getId_perfil());
		//OBTIENE FX_ID DE ELEMENTOS PERMITIDOS POR PERFIL
		usrObj.setMenuAccess(getIMenuPerfilDAO().getFxIdByPerfil(usrObj.getId_perfil()));
		//System.out.println("lnList:"+usrObj.getMenuAccess().size());
		String prefijo=env.getProperty("macrofoto.prefijo");
		
		List<Store_sucursal> lstSuc =  (Flags.remote_valid)?
				remoteSucursalService.getSuc(null, prefijo, null):
				sucursalService.getSuc(null, prefijo, null);
		if(lstSuc !=null && lstSuc.size()>0) {
			Store_sucursal rowSuc = lstSuc.get(0);
			usrObj.setRazon_social(rowSuc.getRazon_social());
			usrObj.setSucursal(rowSuc.getSucursal());
			usrObj.setId_sucursal(rowSuc.getId_sucursal());
			usrObj.setDirSucursal(rowSuc.getDireccion());
			usrObj.setTelSucursal(rowSuc.getTelefono());
			usrObj.setPrefijo(prefijo);
		}
		UserSessionDTO.setInstance(usrObj.getLogin(),usrObj.getNombre(),
				usrObj.getCorreo(),usrObj.getTelefono(),usrObj.getDireccion(),usrObj.getBloqueado(),
				usrObj.getActivo(),usrObj.getSucursal(),usrObj.getPrefijo(),usrObj.getId_perfil(),
				usrObj.getId_sucursal(),usrObj.getMenuAccess(),usrObj.getDirSucursal(),usrObj.getTelSucursal(),
				usrObj.getRazon_social());
		UserSessionDTO instance = UserSessionDTO.getInstance();
		
		
		System.out.println("UserSessionDTO [login=" + instance.getLogin() + ", nombre=" + instance.getNombre() + 
		", correo=" + instance.getCorreo() + ", telefono=" + instance.getTelefono()
		+ ", direccion=" + instance.getDireccion() + ", prefijo=" + instance.getPrefijo() +
		", id_sucursal=" + instance.getId_sucursal()+", razon_social="+usrObj.getRazon_social() +"]");
		
		resp.setValid(true);
		return resp;
	}

}
