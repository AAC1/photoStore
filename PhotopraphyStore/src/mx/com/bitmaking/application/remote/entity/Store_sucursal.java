package mx.com.bitmaking.application.remote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_sucursal {
	@Id
	@Column
	private int id_sucursal;
	@Column
	private String sucursal;
	@Column
	private String razon_social;
	@Column
	private String direccion;
	@Column
	private String telefono;
	@Column
	private String prefijo;
	
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
