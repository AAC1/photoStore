package mx.com.bitmaking.application.dto;

import com.jfoenix.controls.JFXTextField;

public class CorteCajaDTO {
	private String tipo;
	private String denominacion;
	private String importe;
	private JFXTextField cantidad;
	
	public CorteCajaDTO(String tipo,String denominacion, String importe, JFXTextField cantidad){
		this.tipo = tipo;
		this.denominacion = denominacion;
		this.importe = importe;
		this.cantidad = cantidad;
	}
	
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the denominacion
	 */
	public String getDenominacion() {
		return denominacion;
	}
	/**
	 * @param denominacion the denominacion to set
	 */
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}
	/**
	 * @return the cantidad
	 */
	public JFXTextField getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(JFXTextField cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
