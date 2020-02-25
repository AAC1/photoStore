package mx.com.bitmaking.application.local.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_pedido {
	
	@Id
	@Column
	private int id_pedido;
	@Column
	private String folio;
	@Column
	private String cliente;
	@Column
	private String telefono;
	@Column
	private String descripcion;
	@Column
	private Date fec_pedido;
	@Column
	private Date fec_entregado;
	@Column
	private BigDecimal monto_ant;
	@Column
	private BigDecimal monto_total;
	@Column
	private int id_estatus;
	
	
	public int getId_pedido() {
		return id_pedido;
	}
	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFec_pedido() {
		return fec_pedido;
	}
	public void setFec_pedido(Date fec_pedido) {
		this.fec_pedido = fec_pedido;
	}
	public Date getFec_entregado() {
		return fec_entregado;
	}
	public void setFec_entregado(Date fec_entregado) {
		this.fec_entregado = fec_entregado;
	}
	public BigDecimal getMonto_ant() {
		return monto_ant;
	}
	public void setMonto_ant(BigDecimal monto_ant) {
		this.monto_ant = monto_ant;
	}
	public BigDecimal getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(BigDecimal monto_total) {
		this.monto_total = monto_total;
	}
	public int getId_estatus() {
		return id_estatus;
	}
	public void setId_estatus(int id_estatus) {
		this.id_estatus = id_estatus;
	}
	
	
	
}
