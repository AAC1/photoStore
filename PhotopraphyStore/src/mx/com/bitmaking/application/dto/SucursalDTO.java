package mx.com.bitmaking.application.dto;

import mx.com.bitmaking.application.entity.Store_sucursal;

public class SucursalDTO extends Store_sucursal{
	
	private String estatus;

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	
}
