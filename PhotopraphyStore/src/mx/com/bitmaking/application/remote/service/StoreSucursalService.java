package mx.com.bitmaking.application.remote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreSucursalService;
import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.idao.IStoreSucursalDAO;

@Service("remoteStoreSucursalService")
public class StoreSucursalService extends AbstractStoreSucursalService{
	
	@Autowired
	@Qualifier("remoteStoreSucursalDAO")
	IStoreSucursalDAO storeSucursalDAO;
	
	@Override
	public IStoreSucursalDAO getStoreSucursalDAO() {
		return storeSucursalDAO;
	}
	
	@Transactional(value="remoteTransactionManager")
	public List<Store_sucursal> getSuc(String sucursal,String prefijo,String razonSocial) {
		return super.getSuc( sucursal, prefijo, razonSocial);
	}
	
	@Transactional(value="remoteTransactionManager")
	public void save(SucursalDTO obj) {
		super.save(obj);
	}

	@Transactional(value="remoteTransactionManager")
	public void update(SucursalDTO obj) {
		super.update(obj);
	}
	@Transactional(value="remoteTransactionManager")
	public void delete(int idSuc) {
		super.delete(idSuc);
	}
}
