package mx.com.bitmaking.application.remote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_cat_estatus {
	@Id
	@Column
	private int id_estatus;
	@Column
	private String estatus;
	public int getId_estatus() {
		return id_estatus;
	}
	public void setId_estatus(int id_estatus) {
		this.id_estatus = id_estatus;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	
}
