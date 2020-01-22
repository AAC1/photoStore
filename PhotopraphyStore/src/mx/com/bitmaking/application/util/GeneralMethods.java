package mx.com.bitmaking.application.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class GeneralMethods {

	public static void modalMsg(String title,String msgTitle, String msgContent) {
		
		Alert alert;
		switch(title.toUpperCase()) {
			case "ERROR":
				alert=new Alert(AlertType.ERROR);
				break;
			case "WARNING":
				alert=new Alert(AlertType.WARNING);
				break;
			default:
				alert=new Alert(AlertType.INFORMATION);
		}
		alert.initStyle(StageStyle.UNDECORATED);
		
		alert.setTitle(title);
		if(msgTitle!=null && msgTitle.trim().length()>0)alert.setHeaderText(msgTitle);
		else alert.setHeaderText(null);
		//alert.setHeaderText(msgContent);
		alert.setContentText(msgContent);
	
		alert.showAndWait();
	}
	
	public static String validIfNull(String val,String format) {
		if(val!=null && val.length()>0) {
			String r =String.format(format, val);
			System.out.println("r:"+r);
			return r;
		}
		return "";
	}
}
