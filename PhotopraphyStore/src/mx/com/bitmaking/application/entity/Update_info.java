package mx.com.bitmaking.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Update_info {
	@Id
	@Column
	private int id_update_info;
	@Column
	private byte[] json_data;
	@Column
	private String direction;
	@Column
	private int branch_updated;
	
	public int getId_update_info() {
		return id_update_info;
	}
	public void setId_update_info(int id_update_info) {
		this.id_update_info = id_update_info;
	}
	
	public byte[] getJson_data() {
		return json_data;
	}
	public void setJson_data(byte[] json_data) {
		this.json_data = json_data;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getBranch_updated() {
		return branch_updated;
	}
	public void setBranch_updated(int branch_updated) {
		this.branch_updated = branch_updated;
	}
	
	
	
}
