package mx.com.bitmaking.application.dto;

import java.util.HashMap;
import java.util.List;

import mx.com.bitmaking.application.entity.Store_menu;

public class UserSession {
	private Long id_usr;
	private String login;
	private String nombre;
	private String correo;
	private String telefono;
	private String direccion;
	private int intentos;
	private int bloqueado;
	private int activo;
	private String sucursal;
	private String prefijo;
	private int id_perfil;
	private List<Store_menu> menuAccess ;
	
	
	/**
	 * @return the intentos
	 */
	public int getIntentos() {
		return intentos;
	}
	/**
	 * @param intentos the intentos to set
	 */
	public void setIntentos(int intentos) {
		this.intentos = intentos;
	}
	/**
	 * @return the id_usr
	 */
	public Long getId_usr() {
		return id_usr;
	}
	/**
	 * @param id_usr the id_usr to set
	 */
	public void setId_usr(Long id_usr) {
		this.id_usr = id_usr;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the bloqueado
	 */
	public int getBloqueado() {
		return bloqueado;
	}
	/**
	 * @param bloqueado the bloqueado to set
	 */
	public void setBloqueado(int bloqueado) {
		this.bloqueado = bloqueado;
	}
	/**
	 * @return the activo
	 */
	public int getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(int activo) {
		this.activo = activo;
	}
	/**
	 * @return the sucursal
	 */
	public String getSucursal() {
		return sucursal;
	}
	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	/**
	 * @return the prefijo
	 */
	public String getPrefijo() {
		return prefijo;
	}
	/**
	 * @param prefijo the prefijo to set
	 */
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	/**
	 * @return the id_perfil
	 */
	public int getId_perfil() {
		return id_perfil;
	}
	/**
	 * @param id_perfil the id_perfil to set
	 */
	public void setId_perfil(int id_perfil) {
		this.id_perfil = id_perfil;
	}
	public List<Store_menu> getMenuAccess() {
		return menuAccess;
	}
	public void setMenuAccess(List<Store_menu> menuAccess) {
		this.menuAccess = menuAccess;
	}

	
}
