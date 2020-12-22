package mx.com.bitmaking.application.dto;

import mx.com.bitmaking.application.entity.Store_fotografo;

public class ClienteDTO extends Store_fotografo{
	
	private String estatusStr;

	public String getEstatusStr() {
		return estatusStr;
	}

	public void setEstatusStr(String estatusStr) {
		this.estatusStr = estatusStr;
	}
	
	
}
