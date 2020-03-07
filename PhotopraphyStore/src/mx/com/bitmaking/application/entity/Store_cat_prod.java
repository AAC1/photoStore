/**
 * 
 */
package mx.com.bitmaking.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author albcervantes
 *
 */
@Entity
@Table(name="store_cat_prod")
public class Store_cat_prod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_prod")
	private int id_prod;
	
	@Column(name="id_padre_prod")
	private int id_padre_prod;
	
	@Column(name="producto")
	private String producto;

	@Column(name="estatus")
	private String estatus;

	@Column(name="barcode")
	private String barcode;
	
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getId_padre_prod() {
		return id_padre_prod;
	}
	public void setId_padre_prod(int id_padre_prod) {
		this.id_padre_prod = id_padre_prod;
	}
	/**
	 * @return the id_prod
	 */
	public int getId_prod() {
		return id_prod;
	}
	/**
	 * @param id_prod the id_prod to set
	 */
	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}
	/**
	 * @return the producto
	 */
	public String getProducto() {
		return producto;
	}
	/**
	 * @param producto the producto to set
	 */
	public void setProducto(String producto) {
		this.producto = producto;
	}
	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	
}
