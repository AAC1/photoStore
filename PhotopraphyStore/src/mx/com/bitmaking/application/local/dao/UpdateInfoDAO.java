package mx.com.bitmaking.application.local.dao;

import org.hibernate.SessionFactory;

import mx.com.bitmaking.application.abstractdao.AbstractUpdateInfoDAO;
import mx.com.bitmaking.application.entity.Update_info;

public class UpdateInfoDAO extends AbstractUpdateInfoDAO{
	
	public Update_info getInfoByDirection() {
		return super.getInfo();
	}
	
	@Override
	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
