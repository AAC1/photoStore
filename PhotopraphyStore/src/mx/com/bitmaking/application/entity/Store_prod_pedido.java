package mx.com.bitmaking.application.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_prod_pedido {
	@Id
	@Column
	private int id_prod_pedido;
	@Column
	private String descripcion;
	@Column
	private int cantidad;
	@Column
	private BigDecimal costo_unitario;
	@Column
	private BigDecimal costo_total;
	@Column
	private int id_pedido;
	public int getId_prod_pedido() {
		return id_prod_pedido;
	}
	public void setId_prod_pedido(int id_prod_pedido) {
		this.id_prod_pedido = id_prod_pedido;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getCosto_unitario() {
		return costo_unitario;
	}
	public void setCosto_unitario(BigDecimal costo_unitario) {
		this.costo_unitario = costo_unitario;
	}
	public BigDecimal getCosto_total() {
		return costo_total;
	}
	public void setCosto_total(BigDecimal costo_total) {
		this.costo_total = costo_total;
	}
	public int getId_pedido() {
		return id_pedido;
	}
	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}
	
	
}
