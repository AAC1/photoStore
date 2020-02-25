package mx.com.bitmaking.application.remote.service;

import java.io.FileInputStream;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.local.entity.Store_pedido;
import net.sf.jasperreports.engine.JRException;
//extends JpaRepository<Store_pedido, Long>
public interface IStorePedidoService  {

	public List<PedidosReporteDTO> consultPedido(String qry);
	/**
	 * 
	 * @param fileInputStream
	 * @param qry
	 * @param titulo
	 * @param pathReport
	 * @param pathParent -> para subreporta, deben estar al mismo nivel que el reporter master
	 * @return
	 * @throws JRException
	 */
	public boolean generaXLS(FileInputStream fileInputStream, String qry, String titulo,
				String pathReport,String pathParent)throws JRException;
	
	public String getCurrentNumberFolio(String prefijo);
	
	public boolean guardaPedido(Store_pedido pedido);
	public void editPedido(PedidosReporteDTO objPedido);
}
