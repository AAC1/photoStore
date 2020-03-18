package mx.com.bitmaking.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_perfil {
	@Id
	@Column
	private int id_perfil;
	@Column
	private String perfil;
	@Column
	private String perfil_desc;
	
	public int getId_perfil() {
		return id_perfil;
	}
	public void setId_perfil(int id_perfil) {
		this.id_perfil = id_perfil;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getPerfil_desc() {
		return perfil_desc;
	}
	public void setPerfil_desc(String perfil_desc) {
		this.perfil_desc = perfil_desc;
	}
	
	
}
