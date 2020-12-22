package mx.com.bitmaking.application.iservice;

import java.util.List;

import mx.com.bitmaking.application.dto.ClienteDTO;
import mx.com.bitmaking.application.entity.Store_fotografo;

public interface IStoreFotografoService {

	public List<Store_fotografo> getActiveClients();
	public List<Store_fotografo> getClients();
	public List<ClienteDTO> getClientsByName(String name);
	public void saveCliente(Store_fotografo cliente);
	public void deleteCliente(int cliente);

}
