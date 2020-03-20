package mx.com.bitmaking.application.abstractdao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import mx.com.bitmaking.application.dto.UsuariosDTO;
import mx.com.bitmaking.application.entity.Store_usuario;
import mx.com.bitmaking.application.idao.IStoreUsuarioDAO;

public abstract class AbstractStoreUsuarioDAO implements IStoreUsuarioDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuariosDTO> getUsr(String qry) {
		SQLQuery sttmt = getSessionFactory().getCurrentSession().createSQLQuery(qry);
		sttmt.setResultTransformer(Transformers.aliasToBean(UsuariosDTO.class));
		sttmt.addScalar("id_usr",new IntegerType());
		sttmt.addScalar("login",new StringType());
		sttmt.addScalar("passwd",new StringType());
		sttmt.addScalar("nombre",new StringType());
		sttmt.addScalar("correo",new StringType());
		sttmt.addScalar("telefono",new StringType());
		sttmt.addScalar("direccion",new StringType());
		sttmt.addScalar("intentos",new IntegerType());
		sttmt.addScalar("bloqueado",new IntegerType());
		sttmt.addScalar("activo",new IntegerType());
		sttmt.addScalar("id_perfil",new IntegerType());
		sttmt.addScalar("id_sucursal",new IntegerType());
		sttmt.addScalar("sucursal",new StringType());
		sttmt.addScalar("perfil",new StringType());
		sttmt.addScalar("estatus",new StringType());
		
		List<UsuariosDTO> resp = sttmt.list();
		return resp;
	}

	@Override
	public void saveUsr(Store_usuario objUsr) {
		getSessionFactory().getCurrentSession().saveOrUpdate(objUsr);
	}

	@Override
	public void deleteUsr(int idUsr) {
		Session session =getSessionFactory().getCurrentSession();
		Store_usuario usr =session.get(Store_usuario.class, idUsr);
		getSessionFactory().getCurrentSession().delete(usr);
	}
	
	public abstract SessionFactory getSessionFactory();

}
