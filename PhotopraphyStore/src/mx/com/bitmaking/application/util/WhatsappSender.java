package mx.com.bitmaking.application.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WhatsappSender {
	
	private static final String INSTANCE_ID = "YOUR_INSTANCE_ID_HERE";
	  private static final String CLIENT_ID = "YOUR_CLIENT_ID_HERE";
	  private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET_HERE";
	  private static final String WA_GATEWAY_URL = "http://api.whatsmate.net/v3/whatsapp/single/text/message/" + INSTANCE_ID;

	public void sendMessage(String number,String message) throws IOException{
		  String jsonPayload = new StringBuilder()
			      .append("{")
			      .append("\"number\":\"")
			      .append(number)
			      .append("\",")
			      .append("\"message\":\"")
			      .append(message)
			      .append("\"")
			      .append("}")
			      .toString();

			    URL url = new URL(WA_GATEWAY_URL);
			    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			    conn.setDoOutput(true);
			    conn.setRequestMethod("POST");
			    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
			    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
			    conn.setRequestProperty("Content-Type", "application/json");

			    OutputStream os = conn.getOutputStream();
			    os.write(jsonPayload.getBytes());
			    os.flush();
			    os.close();

			    int statusCode = conn.getResponseCode();
			    System.out.println("Response from WA Gateway: \n");
			    System.out.println("Status Code: " + statusCode);
			    BufferedReader br = new BufferedReader(new InputStreamReader(
			        (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
			      ));
			    String output;
			    while ((output = br.readLine()) != null) {
			        System.out.println(output);
			    }
			    conn.disconnect();
			  }
}
