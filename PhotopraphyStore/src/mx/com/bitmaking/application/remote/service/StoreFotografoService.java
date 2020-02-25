package mx.com.bitmaking.application.remote.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.bitmaking.application.local.entity.Store_fotografo;
import mx.com.bitmaking.application.local.repository.IStoreFotografoRepo;

@Service("remoteStoreFotografoService")
public class StoreFotografoService implements IStoreFotografoService {

	@Autowired
	IStoreFotografoRepo fotografoRepo;
	
	/**
	 * Obtiene clientes con estatus=1 (Activos)
	 */
	@Override
	public List<Store_fotografo> getActiveClients() {
		List<Store_fotografo> lstResp = new ArrayList<>();
		Store_fotografo clteGral = new Store_fotografo();
		clteGral.setId_fotografo(0);
		clteGral.setFotografo("Cliente general");;
		
		lstResp.add(clteGral);
		
		lstResp.addAll(fotografoRepo.getActiveClients());
		return lstResp;
	}

}
