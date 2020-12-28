package mx.com.bitmaking.application.remote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreFotografoService;
import mx.com.bitmaking.application.dto.ClienteDTO;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.idao.IStoreFotografoDAO;

@Service("remoteStoreFotografoService")
public class StoreFotografoService extends AbstractStoreFotografoService{//implements IStoreFotografoService {

	@Autowired
	@Qualifier("remoteStoreFotografoDAO")
	IStoreFotografoDAO fotografoRepo;

	/**
	 * Obtiene clientes con estatus=1 (Activos)
	 */
	@Transactional(value = "remoteTransactionManager")
	public List<Store_fotografo> getActiveClients() {
		return super.getActiveClients();
	}

	@Override
	public IStoreFotografoDAO getFotografoRepo() {
		return fotografoRepo;
	}

	@Transactional(value="remoteTransactionManager")
	@Override
	public List<ClienteDTO> getClientsByName(String name,String operator) {
		
		return super.getClientsByName(name,operator);
	}

	@Transactional(value="remoteTransactionManager")
	@Override
	public List<Store_fotografo> getClients() {
		return super.getClients();
	}
	
	@Transactional(value="remoteTransactionManager")
	@Override
	public void saveCliente(Store_fotografo cliente) {
		
		super.saveCliente(cliente);
	}

	@Transactional(value="remoteTransactionManager")
	@Override
	public void deleteCliente(int idCliente) {
		 super.deleteCliente(idCliente);
	}
}
