package mx.com.bitmaking.application.iservice;

import java.io.FileInputStream;
import java.util.List;

import mx.com.bitmaking.application.dto.PedidosReporteDTO;
import mx.com.bitmaking.application.entity.Store_pedido;
import net.sf.jasperreports.engine.JRException;

public interface IStorePedidoService {

	public List<PedidosReporteDTO> consultPedido(String qry);

	public boolean generaXLS(FileInputStream fileInputStream, String qry, String titulo,String pathReport)throws JRException;
}
