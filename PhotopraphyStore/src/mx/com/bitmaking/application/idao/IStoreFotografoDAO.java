package mx.com.bitmaking.application.idao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import mx.com.bitmaking.application.dto.ClienteDTO;
import mx.com.bitmaking.application.entity.Store_fotografo;

public interface IStoreFotografoDAO {
	//@Query("FROM Store_fotografo WHERE estatus=1")
	public List<Store_fotografo> getActiveClients();

	//@Query("FROM Store_fotografo ")
	public List<Store_fotografo> getClients();
	
	//@Query("FROM Store_fotografo where fotografo like '%?1%'")
	public List<ClienteDTO> getClientsByName(String name);

	public void saveCliente(Store_fotografo cliente);
	public void deleteCliente(int cliente);
}
