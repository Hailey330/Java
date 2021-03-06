package SmsProgram;

import java.util.HashMap;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class SmsSend {

	public static void main(String number, String text) {
		
		String api_key = "NCSKNGWPWMQ4JFZK";
		String api_secret = "";
		Message coolsms = new Message(api_key, api_secret);

		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("from", "01039317310"); //발신번호
		params.put("to", number); // 수신번호
		params.put("text",text); // 내용
		params.put("type", "sms"); // 타입

		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println(obj.toString());
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}
	}
}