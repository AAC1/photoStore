package mx.com.bitmaking.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CostProductsDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2182708187360514917L;
	private int id_prod;
	private int id_padre_prod;
	private String producto;
	private String estatus;
	private BigDecimal costo;
	private int cantidad;
	private String bar_code;
	
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the id_prod
	 */
	public int getId_prod() {
		return id_prod;
	}
	/**
	 * @param id_prod the id_prod to set
	 */
	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}
	/**
	 * @return the id_padre_prod
	 */
	public int getId_padre_prod() {
		return id_padre_prod;
	}
	/**
	 * @param id_padre_prod the id_padre_prod to set
	 */
	public void setId_padre_prod(int id_padre_prod) {
		this.id_padre_prod = id_padre_prod;
	}
	/**
	 * @return the producto
	 */
	public String getProducto() {
		return producto;
	}
	/**
	 * @param producto the producto to set
	 */
	public void setProducto(String producto) {
		this.producto = producto;
	}
	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	/**
	 * @return the costo
	 */
	public BigDecimal getCosto() {
		return costo;
	}
	/**
	 * @param costo the costo to set
	 */
	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}
	/**
	 * @return the bar_code
	 */
	public String getBar_code() {
		return bar_code;
	}
	/**
	 * @param bar_code the bar_code to set
	 */
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	
	
}
