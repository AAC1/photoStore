package mx.com.bitmaking.application.local.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractservice.AbstractStoreSucursalService;
import mx.com.bitmaking.application.dto.SucursalDTO;
import mx.com.bitmaking.application.entity.Store_sucursal;
import mx.com.bitmaking.application.idao.IStoreSucursalDAO;

@Service("StoreSucursalService")
public class StoreSucursalService extends AbstractStoreSucursalService{
	
	@Autowired
	@Qualifier("StoreSucursalDAO")
	IStoreSucursalDAO storeSucursalDAO;
	
	@Override
	public IStoreSucursalDAO getStoreSucursalDAO() {
		return storeSucursalDAO;
	}
	
	@Transactional(value="transactionManager")
	public List<Store_sucursal> getSuc(String sucursal,String prefijo,String razonSocial) {
		return super.getSuc( sucursal, prefijo, razonSocial);
	}
	
	@Transactional(value="transactionManager")
	public void save(Store_sucursal obj) {
		super.save(obj);
	}

	@Transactional(value="transactionManager")
	public void update(Store_sucursal obj) {
		super.update(obj);
	}
	@Transactional(value="transactionManager")
	public void delete(int idSuc) {
		super.delete(idSuc);
	}
}
