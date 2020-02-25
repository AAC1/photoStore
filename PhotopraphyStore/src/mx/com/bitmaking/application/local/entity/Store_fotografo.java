package mx.com.bitmaking.application.local.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_fotografo {
	
	@Id
	@Column
	private int id_fotografo;
	@Column
	private String fotografo;
	@Column
	private String telefono;
	@Column
	private String email;
	@Column
	private int estatus;
	public int getId_fotografo() {
		return id_fotografo;
	}
	public void setId_fotografo(int id_fotografo) {
		this.id_fotografo = id_fotografo;
	}
	public String getFotografo() {
		return fotografo;
	}
	public void setFotografo(String fotografo) {
		this.fotografo = fotografo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	
	
	
}
