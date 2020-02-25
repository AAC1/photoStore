package mx.com.bitmaking.application.local.repository;

import mx.com.bitmaking.application.local.dto.UserSession;

public interface ILoginDAO {
	public UserSession getUsr(String usr);
	public UserSession getUsrByPasswd(String usr,String passwd);
	public void bloqueaUsr(String usr);
	public void updateIntentos(String usr,int intentos);
	
}
