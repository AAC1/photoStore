package mx.com.bitmaking.application.local.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractMenuPerfilDAO;
import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.entity.Store_menu;
import mx.com.bitmaking.application.idao.IMenuPerfilDAO;

@Repository("MenuPerfilDAO")
public class MenuPerfilDAO extends AbstractMenuPerfilDAO{// implements IMenuPerfilDAO{
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Transactional(value="transactionManager")
	public List<Store_menu> getFxIdByPerfil(int id_perfil) {
		return super.getFxIdByPerfil(id_perfil);
	}

	@Override
	public SessionFactory getSessionfActory() {
		return sessionFactory;
	}

}
