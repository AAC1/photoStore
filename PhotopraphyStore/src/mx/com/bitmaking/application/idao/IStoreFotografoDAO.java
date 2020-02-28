package mx.com.bitmaking.application.idao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import mx.com.bitmaking.application.entity.Store_fotografo;

public interface IStoreFotografoDAO {
	//@Query("FROM Store_fotografo WHERE estatus=1")
	public List<Store_fotografo> getActiveClients();
}
