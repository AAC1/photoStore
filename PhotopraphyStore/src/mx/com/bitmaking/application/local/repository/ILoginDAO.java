package mx.com.bitmaking.application.local.repository;

import mx.com.bitmaking.application.dto.UserSession;

public interface ILoginDAO {
	public UserSession getUsr(String usr)throws Exception;
	public UserSession getUsrByPasswd(String usr,String passwd);
	public void bloqueaUsr(String usr);
	public void updateIntentos(String usr,int intentos);
	
}
