package mx.com.bitmaking.application.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.entity.Store_cat_estatus;
@Repository
public interface IStoreCatEstatusRepo extends JpaRepository<Store_cat_estatus, Long>{

}
