package mx.com.bitmaking.application.abstractservice;

import mx.com.bitmaking.application.entity.Update_info;
import mx.com.bitmaking.application.idao.IUpdateInfoDAO;
import mx.com.bitmaking.application.iservice.IUpdateInfoService;

public abstract class AbstracUpdateInfoService implements IUpdateInfoService{

	@Override
	public void saveRow(Update_info obj) {
		getUpdateInfoDAO().saveRow(obj);
	}

	@Override
	public Update_info getInfo() {
		return getUpdateInfoDAO().getInfo();
	}
	
	public abstract IUpdateInfoDAO getUpdateInfoDAO();
}
