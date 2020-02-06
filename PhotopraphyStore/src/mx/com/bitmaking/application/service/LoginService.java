package mx.com.bitmaking.application.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.dto.ResponseDTO;
import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.repository.ILoginDAO;
import mx.com.bitmaking.application.util.Constantes;


@Service
public class LoginService implements ILoginService{

	@Autowired
	ILoginDAO loginDAO;
	
	@Override
	@Transactional
	public ResponseDTO validUsr(String usr, String passwd) {
		ResponseDTO resp = new ResponseDTO();
		resp.setEstado("");
		resp.setMsg("");
		resp.setValid(false);
		UserSession onlyUsr = loginDAO.getUsr(usr);
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
		UserSession usrObj = loginDAO.getUsrByPasswd(usr, passwd);
		if(usrObj ==null){
			int intento = Constantes.MAX_INTENTOS - (onlyUsr.getIntentos()+1);
			if(intento<=0){
				loginDAO.bloqueaUsr(usr);
				resp.setEstado("ERROR");
				resp.setMsg("Usuario bloqueado ");
				return resp;
			}
			loginDAO.updateIntentos(usr,onlyUsr.getIntentos()+1);
			
			resp.setEstado("ERROR");
			resp.setMsg("Contraseña incorrecta. Te quedan "+(Constantes.MAX_INTENTOS - (onlyUsr.getIntentos()+1))+" intentos");
			return resp;
		}
		
		if(usrObj.getBloqueado()==1){
			resp.setEstado("ERROR");
			resp.setMsg("El usuario '"+usr+"' se encuentra bloqueado. Contacte al administrador.");
			return resp;
		}else{
			loginDAO.updateIntentos(usr,0);
		}
		if(usrObj.getActivo()==0){
			resp.setEstado("ERROR");
			resp.setMsg("La cuenta del usuario '"+usr+"' no se encuentra activa. Contacte al administrador.");
			return resp;
		}
		
		UserSessionDTO.setInstance(usrObj.getLogin(),usrObj.getNombre(),
				usrObj.getCorreo(),usrObj.getTelefono(),usrObj.getDireccion(),usrObj.getBloqueado(),
				usrObj.getActivo(),usrObj.getSucursal(),usrObj.getPrefijo(),usrObj.getId_perfil(),
				usrObj.getMenuAccess());
		UserSessionDTO instance = UserSessionDTO.getInstance();

		System.out.println("UserSessionDTO [login=" + instance.getLogin() + ", nombre=" + instance.getNombre() + 
		", correo=" + instance.getCorreo() + ", telefono=" + instance.getTelefono()
		+ ", direccion=" + instance.getDireccion() + ", prefijo=" + instance.getPrefijo() + "]");
		
		resp.setValid(true);
		return resp;
	}

}
