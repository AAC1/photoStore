package mx.com.bitmaking.application.remote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_usuario {
	@Id
	@Column
	private int id_usr;
	@Column
	private String login;
	@Column
	private String passwd;
	@Column
	private String nombre;
	@Column
	private String correo;
	@Column
	private String telefono;
	@Column
	private String direccion;
	@Column
	private int bloqueado;
	@Column
	private int activo;
	@Column
	private int id_perfil;
	@Column
	private int id_sucursal;
	/**
	 * @return the id_usr
	 */
	public int getId_usr() {
		return id_usr;
	}
	/**
	 * @param id_usr the id_usr to set
	 */
	public void setId_usr(int id_usr) {
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
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
	/**
	 * @return the id_sucursal
	 */
	public int getId_sucursal() {
		return id_sucursal;
	}
	/**
	 * @param id_sucursal the id_sucursal to set
	 */
	public void setId_sucursal(int id_sucursal) {
		this.id_sucursal = id_sucursal;
	}
	
	
}
