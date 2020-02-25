package mx.com.bitmaking.application.dto;

import mx.com.bitmaking.application.local.entity.Store_pedido;

public class PedidosReporteDTO extends Store_pedido{
	private String estatus;

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
