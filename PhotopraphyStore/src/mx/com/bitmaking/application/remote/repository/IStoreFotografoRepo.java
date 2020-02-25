package mx.com.bitmaking.application.remote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.remote.entity.Store_fotografo;

@Repository("remoteStoreFotografoRepo")
public interface IStoreFotografoRepo  extends JpaRepository<Store_fotografo,Long>{
	//SELECT CONCAT('c-',f.id_fotogrado,' | ',f.fotografo) as fotografo 
	@Query("FROM Store_fotografo WHERE estatus=1")
	public List<Store_fotografo> getActiveClients();
	
}
