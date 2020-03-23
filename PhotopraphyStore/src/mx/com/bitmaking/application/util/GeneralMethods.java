package mx.com.bitmaking.application.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
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
import javax.imageio.ImageIO;

import org.aspectj.bridge.AbortException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
						
						if(number.length()> Constantes.NUMBER_LENGTH) {
							number = number.substring(0,Constantes.NUMBER_LENGTH);
						}
						
						
						String dec = newValue.substring(newValue.lastIndexOf("."), newValue.length());
						number = number.replaceAll("[^0-9]", "");
						
						dec = dec.replaceAll("[^0-9]", "");
						dec = dec.equals("")?"":dec;
						dec = (dec.length()>Constantes.DEC_LENGTH)?dec.substring(0,Constantes.DEC_LENGTH):dec;
						
						newValue = number+"."+dec;
						System.out.println("NewValue_pre: "+newValue);
						//if("".equals(dec)){
							decimalForm = String.format("%,d",Integer.parseInt(number));
							decimalForm+="."+dec;
						//}else{
						if(dec.length()>Constantes.DEC_LENGTH)	{
							decimalForm = String.format("%,."+Constantes.DEC_LENGTH+"f",Double.parseDouble(newValue));
						}
						field.setText(decimalForm);
					}
					else{
						if(newValue.length() > Constantes.NUMBER_LENGTH) {
							field.setText(oldValue);
							
						}else {
						
							newValue = newValue.replaceAll("[^0-9]", "");
							if("".equals(newValue.trim()))decimalForm="";
							else decimalForm = String.format("%,d",Integer.parseInt(newValue));
							field.setText(decimalForm);
						}
					}

				//	System.out.println("NewValue: "+decimalForm);
					
					//if(newValue.length() > Constantes.NUMBER_LENGTH) {
					//	field.setText(oldValue);
				//	}else {
						
					//	field.setText(decimalForm);
				//	}
					
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
    
    public static byte[] generateImgBarcode(String code){
    	byte[] resp = null;
//    	PdfWriter pdfWriter = null;
//    	Document document = null;
    	if(code ==null || "".equals(code))return null;
		try {
//		document =  new Document(PageSize.A0);//PageSize.A4
//        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(
//        							new File(Constantes.PATH_XLS+"barcode.pdf")));
//        document.open();
//        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
        
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(code);
        barcode128.setCodeType(Barcode128.CODE128);

//        Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
//        code128Image.setAbsolutePosition(10, 700);
//        code128Image.scalePercent(100);
//        document.add(code128Image);
//        document.close();
        
        java.awt.Image awtImage = barcode128.createAwtImage(Color.BLACK,Color.WHITE);
        BufferedImage bufferimage = new BufferedImage(awtImage.getWidth(null),awtImage.getHeight(null),
        												BufferedImage.TYPE_INT_RGB);//ImageIO.read(new File("myimage.jpg"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Graphics2D g = bufferimage.createGraphics();
        g.drawImage(awtImage, 0, 0, null);
        g.dispose();
        ImageIO.write(bufferimage, "jpg", output );
        resp = output.toByteArray();
   //     resp=Files.readAllBytes(new File(Constantes.PATH_XLS+"barcode.pdf").toPath());
        /*
        BarcodeQRCode barcodeQrcode = new BarcodeQRCode(code, 1, 1, null);
        Image qrcodeImage = barcodeQrcode.getImage();
    //    qrcodeImage.setAbsolutePosition(20, 500);
        qrcodeImage.scalePercent(100);
        document.add(qrcodeImage);
        */
       
        
		}catch(Exception e) {
			e.printStackTrace();
		}/*finally {
			if(document !=null) {
				
				if( document.isOpen() && document.getPageNumber()>0){ 
			//		document.close();
				
				}
				try {
					Files.deleteIfExists(new File(Constantes.PATH_XLS+"barcode.pdf").toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pdfWriter!=null) {
				pdfWriter.close();
				
			}
		}*/
		return resp;
	}

	public static void saveFile(Stage stage,Path path,String descExtension,String extensions) {
		//File file = new File(filename);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guarda archivo");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(descExtension,extensions);
		fileChooser.getExtensionFilters().add(extFilter);
		File dest = fileChooser.showSaveDialog(stage);
		
		if (dest != null) {
		    try {
		        Files.copy(path, dest.toPath());
		        Files.deleteIfExists(path);
		        
		    } catch (IOException ex) {
		//    	GeneralMethods.modalMsg("", "Error IO: "+ex.getMessage());
		    }catch (AbortException ex) {
		    	
		    }
		}else {
			GeneralMethods.modalMsg("", "Archivo guardado en la ruta por default", " Vaya a la ruta: "+Constantes.PATH_FILES);
		}
	}
}
