package mx.com.bitmaking.application.dto;

public class CorteCajaResumeDTO {
	private String descripcion;
	private String importe;
	private String total;
	
	public CorteCajaResumeDTO(String descripcion,String importe,String total){
		this.descripcion=descripcion;
		this.importe=importe;
		this.total=total;
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
