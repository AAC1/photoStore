package mx.com.bitmaking.application.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Store_pedido {
	
	@Id
	@Column
	private String folio;
	@Column
	private String cliente;
	@Column
	private String telefono;
	@Column
	private String email;
	@Column
	private String descripcion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Calendar fec_pedido;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Calendar fec_entregado;
	@Column
	private BigDecimal monto_ant;
	@Column
	private BigDecimal monto_total;
	@Column
	private int id_estatus;
	@Column
	private byte[] ticket;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getTicket() {
		return ticket;
	}
	public void setTicket(byte[] ticket) {
		this.ticket = ticket;
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
	public Calendar getFec_pedido() {
		return fec_pedido;
	}
	public void setFec_pedido(Calendar fec_pedido) {
		this.fec_pedido = fec_pedido;
	}
	public Calendar getFec_entregado() {
		return fec_entregado;
	}
	public void setFec_entregado(Calendar fec_entregado) {
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
