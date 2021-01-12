package mx.com.bitmaking.application.dto;

public class CorteCajaResumeDTO {
	private String descripcion;
	private String importe;
	private String total;
	private boolean ganancia;
	public CorteCajaResumeDTO(String descripcion,String importe,String total,boolean ganancia){
		this.descripcion=descripcion;
		this.importe=importe;
		this.total=total;
		this.ganancia=ganancia;
	}
	
	public boolean isGanancia() {
		return ganancia;
	}

	public void setGanancia(boolean ganancia) {
		this.ganancia = ganancia;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	} 
	
	
}
