package mx.com.bitmaking.application.dto;

import java.util.HashMap;
import java.util.List;

import mx.com.bitmaking.application.entity.Store_menu;


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
	private int id_sucursal;
	private String dirSucursal;
	private String razon_social;
	private String telSucursal;
	private List<Store_menu> menuAccess ;
	
	
	
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


	/**
	 * @return the razon_social
	 */
	public String getRazon_social() {
		return razon_social;
	}


	/**
	 * @param razon_social the razon_social to set
	 */
	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}


	/**
	 * @return the telSucursal
	 */
	public String getTelSucursal() {
		return telSucursal;
	}


	/**
	 * @param telSucursal the telSucursal to set
	 */
	public void setTelSucursal(String telSucursal) {
		this.telSucursal = telSucursal;
	}


	/**
	 * @return the dirSucursal
	 */
	public String getDirSucursal() {
		return dirSucursal;
	}


	/**
	 * @param dirSucursal the dirSucursal to set
	 */
	public void setDirSucursal(String dirSucursal) {
		this.dirSucursal = dirSucursal;
	}


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


	public List<Store_menu> getMenuAccess() {
		return menuAccess;
	}


	public void setMenuAccess(List<Store_menu> menuAccess) {
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
		int activo, String sucursal, String prefijo, int id_perfil, int id_sucursal, List<Store_menu> menuAccess,
		String dirSucursal,String telSucursal, String razon_social) {
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
		instance.setId_sucursal(id_sucursal);
		instance.setMenuAccess(menuAccess);
		instance.setDirSucursal(dirSucursal);
		instance.setTelSucursal(telSucursal);
		instance.setRazon_social(razon_social);
	
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
