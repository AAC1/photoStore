package mx.com.bitmaking.application.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

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

	public static ChangeListener<String> formatInteger(JFXTextField field){
	
		return new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			//	System.out.println(newValue);
				String decimalForm ="";
				if(!newValue.equals("")) {
					
					
					newValue = newValue.replaceAll("[^0-9]", "");
					decimalForm = String.format("%,d",Integer.parseInt(newValue));
					
					newValue = decimalForm;
					
					if(newValue.length() > Constantes.NUMBER_LENGTH-3) {
						field.setText(oldValue);
					}else {
						
						field.setText(newValue);
					}
					
				}
			}
			
		};
		
	}
	
	public static ChangeListener<String> onlyNumber(JFXTextField field){
		
		return new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			//	System.out.println(newValue);
				String decimalForm ="";
				if(!newValue.equals("")) {
					newValue = newValue.replaceAll("[^0-9]", "");
					
					if(newValue.length() > 10) {
						field.setText(oldValue);
					}else {
						
						field.setText(newValue);
					}
					
				}
			}
			
		};
		
	}
	public static String nSpace(int npadding) {
		StringBuilder beforeText = new StringBuilder();
		for (int i = 0; i < npadding; i++) {
			beforeText.append(" ");
		}
		return beforeText.toString();
	}
	public static String formatCurrentNumber(String val) {
		String newValue = val;
		// System.out.println(newValue);
		String decimalForm = "";
		if (!newValue.equals("")) {

			if (newValue.contains(".")) {
				String number = newValue.substring(0, newValue.lastIndexOf("."));

				String dec = newValue.substring(newValue.lastIndexOf("."), newValue.length());
				number = number.replaceAll("[^0-9]", "");

				dec = dec.replaceAll("[^0-9]+", "");
				dec = dec.equals("") ? "00" : dec;

				newValue = number + "." + dec;

			}
			decimalForm = String.format("%,.2f", Double.parseDouble(newValue));

		}
		return decimalForm;
	}
	public static String cifraSha256(String text,String salt) {
		String resp="";
		try{
			MessageDigest sha256=MessageDigest.getInstance("SHA-256");
			sha256.update((text+salt).getBytes("UTF-8"));
			byte[] digest = sha256.digest();
			StringBuffer sb=new StringBuffer();
			for(int i=0; i <digest.length;i++){
			    sb.append(String.format("%02x", digest[i]));
			}
			resp=sb.toString(); //2bb80d5...527a25b
			
		}catch(NoSuchAlgorithmException| UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return resp;
	}
	
	private static SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");
         
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
         
        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
         
        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");
 
        return secretKey;
    }
	
	public static String encriptar(String datos, String claveSecreta)  {
		String encriptado="";
		try{
		SecretKeySpec secretKey = crearClave(claveSecreta);
         
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
 
        byte[] datosEncriptar = datos.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);
		}
	    catch(UnsupportedEncodingException| NoSuchAlgorithmException| InvalidKeyException|
	    		NoSuchPaddingException| IllegalBlockSizeException| BadPaddingException  e){
			e.printStackTrace();
		}
        return encriptado;
    }
 
    
    public static String desencriptar(String datosEncriptados, String claveSecreta) {
    	String datos="";
        try{
	    	SecretKeySpec secretKey = crearClave(claveSecreta);
	        
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);
	         
	        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
	        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
	        datos = new String(datosDesencriptados);
        }
        catch(UnsupportedEncodingException| NoSuchAlgorithmException| InvalidKeyException|
        		NoSuchPaddingException| IllegalBlockSizeException| BadPaddingException  e){
			e.printStackTrace();
		}
        return datos;
    }

}
