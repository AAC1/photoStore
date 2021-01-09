package mx.com.bitmaking.application.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_corte_caja {
 
	@Id
	@Column
	private int id_corte_caja;

	@Column
	private BigDecimal deno1000;
	@Column
	private BigDecimal deno500;
	@Column
	private BigDecimal deno200;
	@Column
	private BigDecimal deno100;
	@Column
	private BigDecimal deno50;
	@Column
	private BigDecimal deno20;
	@Column
	private BigDecimal deno10;
	@Column
	private BigDecimal deno5;
	@Column
	private BigDecimal deno2;
	@Column
	private BigDecimal deno1;
	@Column
	private BigDecimal deno050;
	@Column
	private BigDecimal importe;
	@Column
	private BigDecimal importe_ini;
	@Column
	private BigDecimal totalPedidos;
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
	 * @return the id_corte_caja
	 */
	public int getId_corte_caja() {
		return id_corte_caja;
	}
	/**
	 * @param id_corte_caja the id_corte_caja to set
	 */
	public void setId_corte_caja(int id_corte_caja) {
		this.id_corte_caja = id_corte_caja;
	}
	/**
	 * @return the deno1000
	 */
	public BigDecimal getDeno1000() {
		return deno1000;
	}
	/**
	 * @param deno1000 the deno1000 to set
	 */
	public void setDeno1000(BigDecimal deno1000) {
		this.deno1000 = deno1000;
	}
	/**
	 * @return the deno500
	 */
	public BigDecimal getDeno500() {
		return deno500;
	}
	/**
	 * @param deno500 the deno500 to set
	 */
	public void setDeno500(BigDecimal deno500) {
		this.deno500 = deno500;
	}
	/**
	 * @return the deno200
	 */
	public BigDecimal getDeno200() {
		return deno200;
	}
	/**
	 * @param deno200 the deno200 to set
	 */
	public void setDeno200(BigDecimal deno200) {
		this.deno200 = deno200;
	}
	/**
	 * @return the deno100
	 */
	public BigDecimal getDeno100() {
		return deno100;
	}
	/**
	 * @param deno100 the deno100 to set
	 */
	public void setDeno100(BigDecimal deno100) {
		this.deno100 = deno100;
	}
	/**
	 * @return the deno50
	 */
	public BigDecimal getDeno50() {
		return deno50;
	}
	/**
	 * @param deno50 the deno50 to set
	 */
	public void setDeno50(BigDecimal deno50) {
		this.deno50 = deno50;
	}
	/**
	 * @return the deno20
	 */
	public BigDecimal getDeno20() {
		return deno20;
	}
	/**
	 * @param deno20 the deno20 to set
	 */
	public void setDeno20(BigDecimal deno20) {
		this.deno20 = deno20;
	}
	/**
	 * @return the deno10
	 */
	public BigDecimal getDeno10() {
		return deno10;
	}
	/**
	 * @param deno10 the deno10 to set
	 */
	public void setDeno10(BigDecimal deno10) {
		this.deno10 = deno10;
	}
	/**
	 * @return the deno5
	 */
	public BigDecimal getDeno5() {
		return deno5;
	}
	/**
	 * @param deno5 the deno5 to set
	 */
	public void setDeno5(BigDecimal deno5) {
		this.deno5 = deno5;
	}
	/**
	 * @return the deno2
	 */
	public BigDecimal getDeno2() {
		return deno2;
	}
	/**
	 * @param deno2 the deno2 to set
	 */
	public void setDeno2(BigDecimal deno2) {
		this.deno2 = deno2;
	}
	/**
	 * @return the deno1
	 */
	public BigDecimal getDeno1() {
		return deno1;
	}
	/**
	 * @param deno1 the deno1 to set
	 */
	public void setDeno1(BigDecimal deno1) {
		this.deno1 = deno1;
	}
	/**
	 * @return the deno050
	 */
	public BigDecimal getDeno050() {
		return deno050;
	}
	/**
	 * @param deno050 the deno050 to set
	 */
	public void setDeno050(BigDecimal deno050) {
		this.deno050 = deno050;
	}
	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	/**
	 * @return the importe_ini
	 */
	public BigDecimal getImporte_ini() {
		return importe_ini;
	}
	/**
	 * @param importe_ini the importe_ini to set
	 */
	public void setImporte_ini(BigDecimal importe_ini) {
		this.importe_ini = importe_ini;
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
	public BigDecimal getTotalPedidos() {
		return totalPedidos;
	}
	public void setTotalPedidos(BigDecimal totalPedidos) {
		this.totalPedidos = totalPedidos;
	}
	
	
	
}
