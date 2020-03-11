package mx.com.bitmaking.application.dto;

import mx.com.bitmaking.application.entity.Store_usuario;

public class UsuariosDTO extends Store_usuario {
	private String sucursal;
	private String perfil;
	private String estatus;
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	
}
