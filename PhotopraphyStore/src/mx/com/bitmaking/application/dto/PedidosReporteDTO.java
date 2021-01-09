package mx.com.bitmaking.application.dto;

import java.math.BigDecimal;

import mx.com.bitmaking.application.entity.Store_pedido;

public class PedidosReporteDTO extends Store_pedido{
	private String estatus;
	private BigDecimal monto_pendiente;
	private BigDecimal total;
	
	
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getMonto_pendiente() {
		return monto_pendiente;
	}

	public void setMonto_pendiente(BigDecimal monto_pendiente) {
		this.monto_pendiente = monto_pendiente;
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
