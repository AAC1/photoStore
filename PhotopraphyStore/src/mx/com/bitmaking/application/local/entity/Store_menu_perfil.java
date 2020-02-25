package mx.com.bitmaking.application.local.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_menu_perfil {
	@Id
	@Column
	private int id_perfil;
	@Column
	private int id_menu;
	/**
	 * @return the id_perfil
	 */
	public int getId_perfil() {
		return id_perfil;
	}
	/**
	 * @param id_perfil the id_perfil to set
	 */
	public void setId_perfil(int id_perfil) {
		this.id_perfil = id_perfil;
	}
	/**
	 * @return the id_menu
	 */
	public int getId_menu() {
		return id_menu;
	}
	/**
	 * @param id_menu the id_menu to set
	 */
	public void setId_menu(int id_menu) {
		this.id_menu = id_menu;
	}
	
	
	
}
