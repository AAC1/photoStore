package mx.com.bitmaking.application.iservice;

import mx.com.bitmaking.application.dto.ResponseDTO;

public interface ILoginService {
	public ResponseDTO validUsr(String usr,String passwd)throws Exception;
}
