package mx.com.bitmaking.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Store_menu {
	@Id
	@Column
	private int id_menu;
	@Column
	private int id_padre;
	@Column
	private String menu_desc;
	@Column
	private String uri;
	@Column
	private String fx_id;
	@Column
	private int estatus;
	
	
	public String getFx_id() {
		return fx_id;
	}
	public void setFx_id(String fx_id) {
		this.fx_id = fx_id;
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
	/**
	 * @return the id_padre
	 */
	public int getId_padre() {
		return id_padre;
	}
	/**
	 * @param id_padre the id_padre to set
	 */
	public void setId_padre(int id_padre) {
		this.id_padre = id_padre;
	}
	/**
	 * @return the menu_desc
	 */
	public String getMenu_desc() {
		return menu_desc;
	}
	/**
	 * @param menu_desc the menu_desc to set
	 */
	public void setMenu_desc(String menu_desc) {
		this.menu_desc = menu_desc;
	}
	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * @return the status
	 */
	public int getEstatus() {
		return estatus;
	}
	/**
	 * @param status the status to set
	 */
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	
	
}
