package mx.com.bitmaking.application.local.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.idao.IStoreFotografoDAO;
import mx.com.bitmaking.application.service.IStoreFotografoService;

@Service("StoreFotografoService")
public class StoreFotografoService implements IStoreFotografoService {

	@Autowired
	@Qualifier("StoreFotografoDAO")
	IStoreFotografoDAO fotografoRepo;
	
	/**
	 * Obtiene clientes con estatus=1 (Activos)
	 */
	@Override
	@Transactional(value="transactionManager")
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
