package mx.com.bitmaking.application.idao;

import java.util.List;

import mx.com.bitmaking.application.entity.Store_cat_estatus;

public interface ICatEstatusDAO {
	public List<Store_cat_estatus> findAll();
}
