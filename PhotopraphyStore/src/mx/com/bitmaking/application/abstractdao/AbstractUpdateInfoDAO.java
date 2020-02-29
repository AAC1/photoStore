package mx.com.bitmaking.application.abstractdao;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.ByteType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import mx.com.bitmaking.application.entity.Update_info;
import mx.com.bitmaking.application.idao.IUpdateInfoDAO;

public abstract class AbstractUpdateInfoDAO implements IUpdateInfoDAO {

	@Override
	public void saveRow(Update_info obj) {
		getSessionFactory().getCurrentSession().saveOrUpdate(obj);
	}

	@Override
	public Update_info getInfo() {
		StringBuilder sQry = new StringBuilder();
		sQry.append("SELECT s.* FROM Update_info s");
		SQLQuery sql = getSessionFactory().getCurrentSession().createSQLQuery(sQry.toString());
		sql.addScalar("id_update_info", new IntegerType());
		sql.addScalar("json_data", new ByteType());
		sql.addScalar("direction", new StringType());
		sql.addScalar("branch_updated", new IntegerType());
		Update_info resp = (Update_info)sql.setMaxResults(1).uniqueResult();
		return resp;
	}
	
	public abstract SessionFactory getSessionFactory();
	
}
