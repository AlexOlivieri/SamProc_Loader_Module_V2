package ch.hevs.samproc.loadermodule.tests.json;

import org.json.JSONObject;

public class JsonObjectsCreator {

	public static JSONObject createLoginObject(String username, String password){
		JSONObject jsonAccount = new JSONObject();
		jsonAccount.put("username", username);
		jsonAccount.put("password", password);
		
		return jsonAccount;
	}

}
