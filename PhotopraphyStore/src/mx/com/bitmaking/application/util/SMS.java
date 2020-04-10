package mx.com.bitmaking.application.util;

import java.io.IOException;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

public class SMS {
	
	
	
	public static void sendMessage(String from,String to,String msg) throws IOException, NexmoClientException
    {
        AuthMethod auth = new TokenAuthMethod("api_key", "api_id");
        NexmoClient client = new NexmoClient(auth);

        TextMessage text = new TextMessage(from, to, msg);
        SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(text);
        for(SmsSubmissionResult response : responses)
        {
            System.out.println(response);
        }

    }
}
