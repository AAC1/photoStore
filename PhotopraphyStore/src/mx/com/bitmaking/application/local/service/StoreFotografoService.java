package mx.com.bitmaking.application.local.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreFotografoService;
import mx.com.bitmaking.application.dto.ClienteDTO;
import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.idao.IStoreFotografoDAO;

@Service("StoreFotografoService")
public class StoreFotografoService extends AbstractStoreFotografoService{//implements IStoreFotografoService {

	@Autowired
	@Qualifier("StoreFotografoDAO")
	IStoreFotografoDAO fotografoRepo;
	
	/**
	 * Obtiene clientes con estatus=1 (Activos)
	 */
	@Transactional(value="transactionManager")
	public List<Store_fotografo> getActiveClients() {
		return super.getActiveClients();
	}

	@Override
	public IStoreFotografoDAO getFotografoRepo() {
		return fotografoRepo;
	}

	@Transactional(value="transactionManager")
	@Override
	public List<ClienteDTO> getClientsByName(String name,String operator) {
		
		return super.getClientsByName(name,operator);
	}

	@Transactional(value="transactionManager")
	@Override
	public List<Store_fotografo> getClients() {
		return super.getClients();
	}
	

	@Transactional(value="transactionManager")
	@Override
	public Integer saveCliente(Store_fotografo cliente) {
		
		return super.saveCliente(cliente);
	}
	
	@Transactional(value="transactionManager")
	@Override
	public void updateCliente(Store_fotografo cliente) {
		
		super.updateCliente(cliente);
	}

	@Transactional(value="transactionManager")
	@Override
	public void deleteCliente(int idCliente) {
		 super.deleteCliente(idCliente);
	}

}
