package mx.com.bitmaking.application.local.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.local.entity.Store_fotografo;

@Repository
public interface IStoreFotografoRepo  extends JpaRepository<Store_fotografo,Long>{
	//SELECT CONCAT('c-',f.id_fotogrado,' | ',f.fotografo) as fotografo 
	@Query("FROM Store_fotografo WHERE estatus=1")
	public List<Store_fotografo> getActiveClients();
	
}
