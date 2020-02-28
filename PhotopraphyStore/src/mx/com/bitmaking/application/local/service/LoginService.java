package mx.com.bitmaking.application.local.service;


//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractLoginService;
import mx.com.bitmaking.application.dto.ResponseDTO;
import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.dto.UserSessionDTO;
import mx.com.bitmaking.application.idao.ILoginDAO;
import mx.com.bitmaking.application.idao.IMenuPerfilDAO;
import mx.com.bitmaking.application.service.ILoginService;
import mx.com.bitmaking.application.util.Constantes;


@Service("LoginService")
public class LoginService extends AbstractLoginService{ //implements ILoginService{

	@Autowired
	@Qualifier("LoginDAO")
	ILoginDAO loginDAO;
	
	@Autowired
	@Qualifier("MenuPerfilDAO")
	IMenuPerfilDAO menuPerfilDAO;
	
	//@Override
	@Transactional(value="transactionManager")
	public ResponseDTO validUsr(String usr, String passwd) throws Exception{
		return super.validUsr(usr, passwd);

	}

	@Override
	public ILoginDAO getILoginDAO() {
		return loginDAO;
	}

	@Override
	public IMenuPerfilDAO getIMenuPerfilDAO() {
		return menuPerfilDAO;
	}

}
