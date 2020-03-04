package mx.com.bitmaking.application.abstractservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.entity.Store_fotografo;
import mx.com.bitmaking.application.idao.IStoreFotografoDAO;
import mx.com.bitmaking.application.service.IStoreFotografoService;
import mx.com.bitmaking.application.util.Constantes;

//@Service("StoreFotografoService")
public abstract class AbstractStoreFotografoService implements IStoreFotografoService {
/*
	@Autowired
	@Qualifier("StoreFotografoDAO")
	IStoreFotografoDAO fotografoRepo;
	*/
	public abstract IStoreFotografoDAO getFotografoRepo();
	
	/**
	 * Obtiene clientes con estatus=1 (Activos)
	 */
	@Override
	public List<Store_fotografo> getActiveClients() {
		List<Store_fotografo> lstResp = new ArrayList<>();
		Store_fotografo clteGral = new Store_fotografo();
		clteGral.setId_fotografo(0);
		clteGral.setFotografo(Constantes.CLTE_GRAL);
		
		lstResp.add(clteGral);
		
		lstResp.addAll(getFotografoRepo().getActiveClients());
		return lstResp;
	}

}
