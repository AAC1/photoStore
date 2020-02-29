package mx.com.bitmaking.application.local.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.bitmaking.application.abstractdao.AbstractLoginDAO;
import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.idao.ILoginDAO;

@Repository("LoginDAO")
public class LoginDAO extends AbstractLoginDAO{ //implements ILoginDAO{
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	@Transactional(value="transactionManager")
	public UserSession getUsr(String usr) {
		return super.getUsr(usr);
	}
	@Transactional(value="transactionManager")
	public void bloqueaUsr(String usr) {
		super.bloqueaUsr(usr);
	}
	@Transactional(value="transactionManager")
	public UserSession getUsrByPasswd(String usr, String passwd) {
		return super.getUsrByPasswd(usr, passwd);

	}
	@Transactional(value="transactionManager")
	public void updateIntentos(String usr,int intentos) {
		super.updateIntentos(usr, intentos);
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}



}
