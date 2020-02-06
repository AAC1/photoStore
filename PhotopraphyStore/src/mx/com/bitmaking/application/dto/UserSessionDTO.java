package mx.com.bitmaking.application.dto;

import java.util.HashMap;


public final class UserSessionDTO {
	private static UserSessionDTO instance;
	
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
	private HashMap<String,Boolean> menuAccess ;
	
	public UserSessionDTO(){}


	public void cleanUserSession() {
		 instance =null;
	  }
	 
	
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
	 * @return the menuAccess
	 */
	public HashMap<String, Boolean> getMenuAccess() {
		return menuAccess;
	}


	/**
	 * @param menuAccess the menuAccess to set
	 */
	public void setMenuAccess(HashMap<String, Boolean> menuAccess) {
		this.menuAccess = menuAccess;
	}


	/**
	 * @return the instance
	 */
	public static UserSessionDTO getInstance(){
        return instance;
	}
	
	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(String login, String nombre, String correo, String telefono, String direccion, int bloqueado,
		int activo, String sucursal, String prefijo, int id_perfil, HashMap<String, Boolean> menuAccess) {
		if(instance == null) {
	        instance = new UserSessionDTO();
	    }
		instance.setLogin(login);
		instance.setNombre(nombre);
		instance.setCorreo(correo);
		instance.setTelefono(telefono);
		instance.setDireccion(direccion);
		instance.setBloqueado(bloqueado);
		instance.setActivo(activo);
		instance.setSucursal (sucursal);
		instance.setPrefijo(prefijo);
		instance.setId_perfil(id_perfil);
		instance.setMenuAccess(menuAccess);

	
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


	
}
