package mx.com.bitmaking.application.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_cargo_abono {
	@Id
	@Column
	private int id_cargo_abono;

	@Column
	private String tipo;
	@Column
	private BigDecimal monto;
	@Column
	private String motivo;
	@Column
	private Date fecha;
	@Column
	private int id_sucursal;
	
	
	
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
	 * @return the id_cargo_abono
	 */
	public int getId_cargo_abono() {
		return id_cargo_abono;
	}
	/**
	 * @param id_cargo_abono the id_cargo_abono to set
	 */
	public void setId_cargo_abono(int id_cargo_abono) {
		this.id_cargo_abono = id_cargo_abono;
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
	 * @return the monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}
	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
}
