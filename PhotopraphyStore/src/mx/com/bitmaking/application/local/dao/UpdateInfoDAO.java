package mx.com.bitmaking.application.local.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import mx.com.bitmaking.application.abstractdao.AbstractUpdateInfoDAO;
import mx.com.bitmaking.application.entity.Update_info;

@Repository("UpdateInfoDAO")
public class UpdateInfoDAO extends AbstractUpdateInfoDAO{
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	public Update_info getInfo() {
		return super.getInfo();
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
