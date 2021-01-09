package mx.com.bitmaking.application.abstractdao;

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

import mx.com.bitmaking.application.dto.UserSession;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.idao.ILoginDAO;


public abstract class AbstractLoginDAO implements ILoginDAO{
	/*
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	*/
	
	public abstract SessionFactory getSessionFactory();
	
	@Override
	public UserSession getUsr(String usr) {
		UserSession results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT id_usr,login,nombre,correo,telefono,intentos,bloqueado,");
		qry.append(" activo,id_perfil");
		qry.append(" FROM Store_usuario ");
		qry.append(" WHERE login=:usr ");
		
		
		try{
			if(!getSessionFactory().getCurrentSession().isConnected())
			{
				throw new Exception("No hay conexión");
			}
			//Query query = entityManager.
			SQLQuery query =getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
					//entityManager.
				//createQuery(qry.toString()).unwrap(SQLQuery.class);
					//sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			 
			
			query.setString("usr", usr);
			query.addScalar("id_usr",new LongType());
			query.addScalar("login",new StringType());
			query.addScalar("nombre",new StringType());
			query.addScalar("correo",new StringType());
			query.addScalar("telefono",new StringType());
			query.addScalar("intentos",new IntegerType());
			query.addScalar("bloqueado",new IntegerType());
			query.addScalar("activo",new IntegerType());
			query.addScalar("id_perfil",new IntegerType());
			
			query.setResultTransformer(Transformers.aliasToBean(UserSession.class));
			
			results =(UserSession) query.setMaxResults(1).uniqueResult();//getSingleResult();//
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return results;

	}

	@Override
	public void bloqueaUsr(String usr) {
		UserSession results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" update store_usuario SET bloqueado=1, intentos=0");
		qry.append(" WHERE login=:usr ");
	//	Query query = entityManager.createQuery(qry.toString()); 
		SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
		query.setString("usr", usr);
		query.executeUpdate();
	}

	@Override
	public UserSession getUsrByPasswd(String usr, String passwd) {
		UserSession results = null;
		StringBuilder qry = new StringBuilder();
		
		qry.append(" select u.id_usr,u.login,u.nombre,u.correo,u.telefono,u.intentos,u.bloqueado,");
		qry.append(" u.activo,u.id_perfil ");
	//	qry.append(" ,s.id_sucursal, s.sucursal,s.prefijo ,s.direccion as dirSucursal, s.razon_social,s.telefono as telSucursal");
		qry.append(" from store_usuario u");
	//	qry.append(" JOIN store_sucursal s ON s.id_sucursal = u.id_sucursal");
		qry.append(" WHERE u.login=:usr AND u.passwd=:passwd");
		
		
		
		try{
 
			SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
			//Query query = entityManager.createQuery(qry.toString()); 
			
			query.setString("usr", usr);
			query.setString("passwd", passwd);
			
			query.addScalar("id_usr",new LongType());
			query.addScalar("login",new StringType());
			query.addScalar("nombre",new StringType());
			query.addScalar("correo",new StringType());
			query.addScalar("telefono",new StringType());
			query.addScalar("intentos",new IntegerType());
			query.addScalar("bloqueado",new IntegerType());
			query.addScalar("activo",new IntegerType());
			query.addScalar("id_perfil",new IntegerType());
//			query.addScalar("id_sucursal",new IntegerType());
//			query.addScalar("sucursal",new StringType());
	//		query.addScalar("prefijo",new StringType());
	//		query.addScalar("dirSucursal",new StringType());
	//		query.addScalar("telSucursal",new StringType());
	//		query.addScalar("razon_social",new StringType());
			
			query.setResultTransformer(Transformers.aliasToBean(UserSession.class));
			
			results =(UserSession) query.setMaxResults(1).uniqueResult();//getSingleResult();//
		
		}catch(Exception e) {
			e.printStackTrace();
		
		}
		
		return results;

	}

	@Override
	public void updateIntentos(String usr,int intentos) {
		StringBuilder qry = new StringBuilder();
		qry.append(" update store_usuario SET intentos=:intentos");
		qry.append(" WHERE login=:usr ");
		
		SQLQuery query= getSessionFactory().getCurrentSession().createSQLQuery(qry.toString());
		query.setString("usr", usr);
		query.setInteger("intentos", intentos);
		//Query query = entityManager.createQuery(qry.toString()); 
		query.executeUpdate();
	}



}
