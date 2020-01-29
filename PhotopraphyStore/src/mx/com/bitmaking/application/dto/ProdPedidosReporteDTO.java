package mx.com.bitmaking.application.dto;

import java.math.BigDecimal;

public class ProdPedidosReporteDTO {
	private int id_prod_pedido;
	private String bar_code;
	private String descripcion;
	private int cantidad;
	private BigDecimal costo_unitario;
	private BigDecimal costo_total;
	
	
	
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
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
	
	
}
