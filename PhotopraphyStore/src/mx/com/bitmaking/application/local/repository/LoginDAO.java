package mx.com.bitmaking.application.local.repository;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.local.dto.UserSession;

@Repository("localLoginDAO")
public class LoginDAO implements ILoginDAO{
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	public UserSession getUsr(String usr) throws Exception {
		UserSession results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT id_usr,login,nombre,correo,telefono,intentos,bloqueado,");
		qry.append(" activo,id_perfil");
		qry.append(" FROM Store_usuario ");
		qry.append(" WHERE login=:usr ");
		
		
		try{
			if(!sessionFactory.getCurrentSession().isConnected())
			{
				throw new Exception("No hay conexión");
			}
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
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
			
			results =(UserSession) query.setMaxResults(1).uniqueResult();
		
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
		
		SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
		query.setString("usr", usr);
		query.executeUpdate();
	}

	@Override
	public UserSession getUsrByPasswd(String usr, String passwd) {
		UserSession results = null;
		StringBuilder qry = new StringBuilder();
		qry.append(" select u.id_usr,u.login,u.nombre,u.correo,u.telefono,u.intentos,u.bloqueado,");
		qry.append(" u.activo,u.id_perfil,");
		qry.append(" s.sucursal,s.prefijo ,s.direccion as dirSucursal, s.razon_social,s.telefono as telSucursal");
		qry.append(" from store_usuario u");
		qry.append(" JOIN store_sucursal s ON s.id_sucursal = u.id_sucursal");
		qry.append(" WHERE u.login=:usr AND u.passwd=:passwd");
		
		
		try{
 
			SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
			
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
			query.addScalar("sucursal",new StringType());
			query.addScalar("prefijo",new StringType());
			query.addScalar("dirSucursal",new StringType());
			query.addScalar("razon_social",new StringType());
			query.addScalar("telefono",new StringType());
			
			query.setResultTransformer(Transformers.aliasToBean(UserSession.class));
			
			results =(UserSession) query.setMaxResults(1).uniqueResult();
		
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
		
		SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(qry.toString());
		query.setString("usr", usr);
		query.setInteger("intentos", intentos);
		
		query.executeUpdate();
	}



}
