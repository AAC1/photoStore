package mx.com.bitmaking.application.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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

	public static boolean validDecimal(String text) {
		// validar decimales
		return true;
	}
	
	
	public static ChangeListener<String> formatNumber(JFXTextField field){
	
		return new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			//	System.out.println(newValue);
				String decimalForm ="";
				if(!newValue.equals("")) {
					
					if(newValue.contains(".")) {
						String number = newValue.substring(0, newValue.lastIndexOf("."));
						
						String dec = newValue.substring(newValue.lastIndexOf("."), newValue.length());
						number = number.replaceAll("[^0-9]", "");
						
						dec = dec.replaceAll("[^0-9]+", "");
						dec = dec.equals("")?"":dec;
						
						
						newValue = number+"."+dec;
						//if("".equals(dec)){
							decimalForm = String.format("%,d",Integer.parseInt(number));
							decimalForm+="."+dec;
						//}else{
						if(dec.length()>=2)	{
							decimalForm = String.format("%,.2f",Double.parseDouble(newValue));
						}
					}
					else{
						newValue = newValue.replaceAll("[^0-9]", "");
						decimalForm = String.format("%,d",Integer.parseInt(newValue));
					}

					System.out.println("NewValue: "+decimalForm);
					newValue = decimalForm;
					
					if(newValue.length() > Constantes.NUMBER_LENGTH) {
						field.setText(oldValue);
					}else {
						
						field.setText(newValue);
					}
					
				}
			}
			
		};
		
	}
	
}
