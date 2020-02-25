package mx.com.bitmaking.application.remote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="store_cliente_prod_cost")
public class Store_cliente_prod_cost {
	
	@Id
	@Column
	private int id_clte_prod_cost;
	
	@Column
	private int id_cliente;
	
	@Column
	private int id_prod;
	
	@Column
	private Double costo;
	
	@Column
	private String bar_code;
	
	

	/**
	 * @return the id_clte_prod_cost
	 */
	public int getId_clte_prod_cost() {
		return id_clte_prod_cost;
	}

	/**
	 * @param id_clte_prod_cost the id_clte_prod_cost to set
	 */
	public void setId_clte_prod_cost(int id_clte_prod_cost) {
		this.id_clte_prod_cost = id_clte_prod_cost;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getId_prod() {
		return id_prod;
	}

	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	
	
	
}
