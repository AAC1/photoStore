package mx.com.bitmaking.application.local.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreFotografoService;
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

}
