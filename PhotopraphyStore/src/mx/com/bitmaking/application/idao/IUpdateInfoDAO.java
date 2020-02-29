package mx.com.bitmaking.application.idao;

import mx.com.bitmaking.application.entity.Update_info;

public interface IUpdateInfoDAO {
	public void saveRow(Update_info obj);
	public Update_info getInfo();
}
