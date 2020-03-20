package mx.com.bitmaking.application.iservice;

import mx.com.bitmaking.application.entity.Update_info;

public interface IUpdateInfoService {
	public void saveRow(Update_info obj);
	public Update_info getInfo();
	
}
